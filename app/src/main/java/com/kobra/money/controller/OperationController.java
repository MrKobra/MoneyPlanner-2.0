package com.kobra.money.controller;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.Volley;
import com.kobra.money.model.OperationModel;
import com.kobra.money.request.CustomRequest;
import com.kobra.money.view.OperationView;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class OperationController {
    private Context context;
    private CustomRequest request;
    private Queue<ModelRequest> modelRequestQueue;

    private OperationModel model;
    private OperationView view;

    public OperationController(Context context) {
        this.context = context;
        request = new CustomRequest(Volley.newRequestQueue(context));
        modelRequestQueue = new ArrayDeque<>();
        createModel();
    }

    public void setItems(HashMap<String, String> args) {
        if(model.isRunning()) {
            modelRequestQueue.add(new ModelRequest() {
                @Override
                public void request() {
                    model.setList(args, request, null);
                }
            });
        } else {
            model.setList(args, request, null);
        }
    }

    private void createModel() {
        Event event = new Event() {
            @Override
            public void onReady() {
                while (!model.isRunning() && !modelRequestQueue.isEmpty()) {
                    ModelRequest modelRequest = modelRequestQueue.poll();
                    if(modelRequest != null) {
                        modelRequest.request();
                    }
                }
            }
        };
        model = new OperationModel(event);
    }

    private void setView(OperationView view) {
        this.view = view;
    }

    public interface Event {
        void onReady();
    }

    public interface ModelRequest {
        void request();
    }
}
