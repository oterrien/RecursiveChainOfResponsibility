package com.ote.chain.sample;

import com.ote.chain.util.AsynchronousProcessor;
import com.ote.chain.util.IHandler;
import com.ote.chain.util.IProcessor;
import com.ote.chain.util.Processor;

import java.util.Collection;
import java.util.List;

/**
 * Created by Olivier on 24/10/2015.
 */
public class MyProcessor<Query> implements IProcessor<Query> {

    IProcessor<Query> processor = new AsynchronousProcessor<Query>(
            new Processor().
                    add(new MyHandler2()).
                    add(new MyHandler1()));

    @Override
    public void process(Query query) {
        processor.process(query);
    }
}
