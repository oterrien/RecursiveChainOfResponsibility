package com.ote.chain.util;

/**
 * Created by Olivier on 24/10/2015.
 */
public abstract class Handler<TQ, TP extends Handler.Context<TQ>> implements IHandler<TQ> {

    private TP context;

    public TP getContext() {
        return context;
    }

    public void setContext(TP context) {
        this.context = context;
    }

    @Override
    public void setUp(TQ query) {
        context = createContext();
        context.setQuery(query);
    }

    protected abstract TP createContext();

    @Override
    public boolean isAccepted() {
        return accept(context);
    }

    protected abstract boolean accept(TP context);

    @Override
    public void handle() {

        handle(context);
    }

    protected abstract void handle(TP context);

    /**
     * Handler's context to wrap the query and use additional data without enriching the query
     *
     * @param <TQ>
     */
    public static class Context<TQ> {

        private TQ query;

        public Context(){

        }

        public TQ getQuery() {
            return query;
        }

        public void setQuery(TQ query) {
            this.query = query;
        }
    }
}
