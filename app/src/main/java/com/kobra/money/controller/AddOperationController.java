package com.kobra.money.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.toolbox.Volley;
import com.kobra.money.model.CategoryModel;
import com.kobra.money.model.OperationModel;
import com.kobra.money.request.CustomRequest;
import com.kobra.money.view.form.add.AddOperationForm;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class AddOperationController {
    private Context context;
    private CustomRequest request;
    private Queue<OperationController.ModelRequest> modelRequestQueue;

    private CategoryModel model;
    private AddOperationForm view;

    public AddOperationController(Context context) {
        this.context = context;
        request = new CustomRequest(Volley.newRequestQueue(context));
        modelRequestQueue = new ArrayDeque<>();
        createModel();
    }

    public void setCategories(HashMap<String, String> args, CategoryModel.Event event) {
        if(model.isRunning()) {
            modelRequestQueue.add(new OperationController.ModelRequest() {
                @Override
                public void request() {
                    model.setList(args, request, event);
                }
            });
        } else {
            model.setList(args, request, event);
        }
    }

    public void setView(View formView) {
        view = (AddOperationForm) new AddOperationForm.Builder(context)
                .setFormView(formView)
                .getForm();

        if (model.isRunning()) {
            modelRequestQueue.add(new OperationController.ModelRequest() {
                @Override
                public void request() {
                    view.setCategories(model.getList());
                }
            });
        } else {
            view.setCategories(model.getList());
        }
    }

    private void createModel() {
        Event event = new Event() {
            @Override
            public void onReady() {
                while (!model.isRunning() && !modelRequestQueue.isEmpty()) {
                    OperationController.ModelRequest modelRequest = modelRequestQueue.poll();
                    if(modelRequest != null) {
                        modelRequest.request();
                    }
                }
            }
        };
        model = new CategoryModel(event);
    }

    public interface Event {
        void onReady();
    }

    public interface ModelRequest {
        void request();
    }
}
