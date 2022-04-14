package com.kobra.money.model;

import android.content.Context;

import java.util.List;

public abstract class Model {
    protected Context context;
    protected boolean run;
    protected ReadyEvent ready;

    public Model(Context context) {
        this.context = context;
    }

    public boolean isRun() {
        return run;
    }

    public void setReady(ReadyEvent ready) {
        this.ready = ready;
    }

    protected void setRun(boolean run) {
        this.run = run;
        if(!isRun()) {
            if (ready != null) ready.onReady();
        }
    }


    public interface GetEvent<T> {
        void onSuccess(List<T> items);
        void onError(String error);
    }

    public interface AddEvent {
        void onSuccess();
        void onError(String error);
    }

    public interface DeleteEvent {
        void onSuccess();
        void onError(String error);
    }

    public interface EditEvent<T> {
        void onSuccess(T item);
        void onError(String error);
    }

    public interface ReadyEvent {
        void onReady();
    }
}
