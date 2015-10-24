package com.ote.chain.sample;

import com.ote.chain.util.Handler;

/**
 * Created by Olivier on 24/10/2015.
 */
public class MyHandler2 extends Handler<Query, Handler.Context<Query>> {

    @Override
    protected Handler.Context<Query> createContext() {
        return new Handler.Context<Query>();
    }

    @Override
    protected boolean isAccepted(Handler.Context<Query> context) {
        return true;
    }

    @Override
    protected void handle(Handler.Context<Query> context) {
        try {
            Thread.sleep(1500);
            System.out.println("Handler2 " + context.getQuery().getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
