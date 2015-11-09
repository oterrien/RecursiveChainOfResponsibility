package com.ote.chain.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * Created by Olivier on 24/10/2015.
 * <p>
 * Decorate a Processor
 */
public class AsynchronousProcessor<TQ> extends Processor<TQ> implements IProcessor<TQ> {

    private Processor<TQ> processor;

    public AsynchronousProcessor(Processor<TQ> processor) {
        this.processor = processor;
    }

    @Override
    public void process(TQ query) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            Collection<Future<Boolean>> taskResults = new ArrayList<Future<Boolean>>(getHandlers().size());

            getHandlers().
                    stream().
                    map(p -> createTask(p, query)).
                    forEach(t -> execute(executorService, taskResults, t));

            taskResults.
                    stream().
                    allMatch(f -> wait(f));

        } finally {
            executorService.shutdown();
        }
    }

    public void process1(TQ query) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            Collection<Future<Boolean>> taskResults = new ArrayList<Future<Boolean>>(getHandlers().size());

            getHandlers().
                    stream().
                    map((p) -> createTask(p, query)).
                    forEach((t) -> execute(executorService, taskResults, t));

            taskResults.
                    stream().
                    allMatch((f) -> wait(f));

        } finally {
            executorService.shutdown();
        }
    }


    /**
     * Launch task and add the future to taskResults
     *
     * @param executorService
     * @param taskResults
     * @param task
     */
    private void execute(ExecutorService executorService, Collection<Future<Boolean>> taskResults, Callable<Boolean> task) {
        try {
            taskResults.add(executorService.submit(task));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * wait for a future to be returned
     *
     * @param future
     * @return
     */
    private boolean wait(Future<Boolean> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encapsulate the processor.handle method into a Callable which returns true once terminated
     *
     * @param handler
     * @param query
     * @return
     */
    private Callable<Boolean> createTask(IHandler<TQ> handler, TQ query) {

        return () -> {
            handle(handler, query);
            return true;
        };
    }

    @Override
    protected void handle(IHandler<TQ> handler, TQ query) {
        processor.handle(handler, query);
    }

    @Override
    protected List<IHandler<TQ>> getHandlers() {
        return processor.getHandlers();
    }
}
