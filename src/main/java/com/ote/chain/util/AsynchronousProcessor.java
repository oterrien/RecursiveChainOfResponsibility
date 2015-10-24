package com.ote.chain.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by Olivier on 24/10/2015.
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

            List<Callable<Boolean>> tasks = processor.handlers.
                    stream().
                    map((p) -> createTask(p, query)).
                    collect(Collectors.toList());

            List<Future<Boolean>> taskResults = new ArrayList<>(processor.handlers.size());
            tasks.forEach((p) -> {
                try {
                    taskResults.add(executorService.submit(p));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            taskResults.forEach((f) -> {
                try {
                    f.get();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        } finally {
            executorService.shutdown();
        }
    }

    /**
     * Encapsulate the processor.handle method into a Callable which returns true once terminated
     * @param handler
     * @param query
     * @return
     */
    private Callable<Boolean> createTask(IHandler<TQ> handler, TQ query) {

        return () -> {
            processor.handle(handler, query);
            return true;
        };
    }
}
