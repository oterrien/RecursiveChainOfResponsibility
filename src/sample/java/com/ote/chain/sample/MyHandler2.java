package com.ote.chain.sample;

import com.ote.chain.util.Handler;

/**
 * Created by Olivier on 24/10/2015.
 */
public class MyHandler2 extends Handler<Query, MyHandler2.Context> {

    @Override
    protected Context createContext() {
        return new Context();
    }

    @Override
    protected boolean accept(Context context) {
        return true;
    }

    @Override
    protected void handle(Context context) {
        try {
            Thread.sleep(1500);
            System.out.println("Handler2 " + context.getQuery().getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class Context extends Handler.Context<Query> {


    }
}
