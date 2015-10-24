package com.ote.chain.util;

/**
 * Created by Olivier on 24/10/2015.
 */
public interface IProcessor<TQ> {

    void process(TQ query);
}
