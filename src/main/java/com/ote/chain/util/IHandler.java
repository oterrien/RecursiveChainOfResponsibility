package com.ote.chain.util;

/**
 * Created by Olivier on 24/10/2015.
 */
public interface IHandler<TQ> {

    void setUp(TQ query);

    boolean isAccepted();

    void handle();
}
