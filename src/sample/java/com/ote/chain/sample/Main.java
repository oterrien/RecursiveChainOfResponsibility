package com.ote.chain.sample;

import com.ote.chain.util.AsynchronousProcessor;
import com.ote.chain.util.IProcessor;
import com.ote.chain.util.Processor;

/**
 * Created by Olivier on 24/10/2015.
 */
public class Main {

    public static void main(String[] args) {
        testSynchronous();
        testAsynchronous();
    }

    private static void testSynchronous() {

        long time = System.currentTimeMillis();
        System.out.println("Test Synchronous");

        Query q = new Query();
        q.setMessage("toto");
        IProcessor<Query> processor = new Processor().
                add(new MyHandler2()).
                add(new MyHandler1());
        processor.process(q);
        System.out.println("End " + (System.currentTimeMillis() - time) + " ms");
    }

    private static void testAsynchronous() {

        long time = System.currentTimeMillis();
        System.out.println("Test Asynchronous");

        Query q = new Query();
        q.setMessage("toto");
        IProcessor<Query> processor = new AsynchronousProcessor<>(new Processor().
                add(new MyHandler2()).
                add(new MyHandler1()));
        processor.process(q);

        System.out.println("End " + (System.currentTimeMillis() - time) + " ms");
    }
}
