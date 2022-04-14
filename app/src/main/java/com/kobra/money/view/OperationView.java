package com.kobra.money.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kobra.money.entity.Operation;
import com.kobra.money.model.OperationModel;
import com.kobra.money.view.adapter.ListOperationAdapter;
import com.kobra.money.view.adapter.OperationAdapter;

import java.util.List;

public class OperationView extends ViewModel {
    private Context context;

    private RecyclerView recyclerView;
    private OperationAdapter adapter;
    private List<Operation> operations;

    private CardView emptyNotify;

    public OperationView(Context context, RecyclerView recyclerView, OperationAdapter adapter) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        init();
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public void setEmptyNotify(CardView emptyNotify) {
        this.emptyNotify = emptyNotify;
    }

    @Override
    public void print() {
        if(operations != null && operations.size() > 0) {
            adapter.setItems(operations);
            if(adapter instanceof RecyclerView.Adapter) {
                recyclerView.setAdapter((RecyclerView.Adapter<?>) adapter);
                emptyNotify.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        } else {
            if(emptyNotify != null) {
                emptyNotify.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void update() {
        if(operations != null && operations.size() > 0) {
            if (adapter != null && adapter instanceof RecyclerView.Adapter) {
                ((RecyclerView.Adapter<?>) adapter).notifyDataSetChanged();
                emptyNotify.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        } else {
            emptyNotify.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
    }
}
