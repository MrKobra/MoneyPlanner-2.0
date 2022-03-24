package com.kobra.money.view;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kobra.money.model.OperationModel;
import com.kobra.money.view.adapter.ListOperationAdapter;
import com.kobra.money.view.adapter.OperationAdapter;

import java.util.List;

public class OperationView extends ViewModel {
    private Context context;
    private RecyclerView recyclerView;
    private OperationAdapter adapter;
    private List<OperationModel.Operation> operations;

    public OperationView(Context context, RecyclerView recyclerView, OperationAdapter adapter) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        init();
    }

    public void setOperations(List<OperationModel.Operation> operations) {
        this.operations = operations;
    }

    @Override
    public void print() {
        if(operations != null && operations.size() > 0) {
            adapter.setItems(operations);
            if(adapter instanceof RecyclerView.Adapter) {
                recyclerView.setAdapter((RecyclerView.Adapter) adapter);
            }
        }
    }

    @Override
    public void update() {
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
    }
}
