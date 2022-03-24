package com.kobra.money.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

    public void setView(OperationView view) {
        this.view = view;
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

    public void print() {
        if(view != null) {
            if (model.isRunning()) {
                modelRequestQueue.add(new ModelRequest() {
                    @Override
                    public void request() {
                        view.setOperations(model.getList());
                        view.print();
                    }
                });
            } else {
                view.setOperations(model.getList());
                view.print();
            }
        }
    }

    public void update() {
        OperationModel.Event event = new OperationModel.Event() {
            @Override
            public void onSuccess() {
                if(view != null) {
                    view.update();
                }
            }

            @Override
            public void onError() {

            }
        };

        if (model.isRunning()) {
            modelRequestQueue.add(new ModelRequest() {
                @Override
                public void request() {
                    model.update(request, event);
                }
            });
        } else {
            model.update(request, event);
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

    public interface Event {
        void onReady();
    }

    public interface ModelRequest {
        void request();
    }
}
