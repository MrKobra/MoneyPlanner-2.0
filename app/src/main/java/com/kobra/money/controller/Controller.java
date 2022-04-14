package com.kobra.money.controller;

import android.content.Context;

import com.kobra.money.model.Model;
import com.kobra.money.request.CustomRequest;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public abstract class Controller {
    protected Context context;
    protected CustomRequest request;

    public Controller(Context context) {
        this.context = context;
        request = new CustomRequest(context);
    }

    public static class ModelRequestQueue {
        private Deque<ModelRequest> modelRequestQueue;
        private Model model;

        public ModelRequestQueue(Model model) {
            this.model = model;
            modelRequestQueue = new ArrayDeque<>();
        }

        public void add(ModelRequest modelRequest) {
            if(model.isRun()) {
                modelRequestQueue.add(modelRequest);
            } else {
                modelRequest.request();
            }
        }

        public ModelRequest poll() {
            return modelRequestQueue.poll();
        }

        public boolean isEmpty() {
            return modelRequestQueue.isEmpty();
        }
    }

    public interface ModelRequest {
        void request();
    }

    public interface Event {
        void onSuccess();
        void onError(String error);
    }
}
