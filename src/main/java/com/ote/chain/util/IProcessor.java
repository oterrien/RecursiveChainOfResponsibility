package com.ote.chain.util;

import com.ote.chain.util.IHandler;

import java.util.Collection;
import java.util.List;

/**
 * Created by Olivier on 24/10/2015.
 *
 */
public interface IProcessor<TQ> {

    void process(TQ query);

    List<IHandler<TQ>> getHandlers();
}
