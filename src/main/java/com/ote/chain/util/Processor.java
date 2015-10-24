package com.ote.chain.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Olivier on 24/10/2015.
 */
public class Processor<TQ> implements IProcessor<TQ> {

    private List<IHandler<TQ>> handlers = new ArrayList<IHandler<TQ>>(10);

    public Processor(IHandler<TQ>... handlers) {
        this.handlers.addAll(Arrays.asList(handlers));
    }

    public Processor<TQ> add(IHandler<TQ> handler){
        handlers.add(handler);
        return this;
    }

    protected List<IHandler<TQ>> getHandlers(){
        return handlers;
    }

    @Override
    public void process(TQ query) {
        for (IHandler<TQ> handler : handlers) {
            handle(handler, query);
        }
    }

    protected void handle(IHandler<TQ> handler, TQ query){
        handler.setUp(query);
        if (handler.isAccepted()) {
            handler.handle();
        }
    }
}
