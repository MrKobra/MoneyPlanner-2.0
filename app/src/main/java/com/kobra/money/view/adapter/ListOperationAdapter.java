package com.kobra.money.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kobra.money.R;
import com.kobra.money.include.FormatStr;
import com.kobra.money.model.CategoryModel;
import com.kobra.money.model.OperationModel;
import com.kobra.money.model.TypeModel;

import java.util.ArrayList;
import java.util.List;

public class ListOperationAdapter extends RecyclerView.Adapter<ListOperationAdapter.OperationViewHolder> implements OperationAdapter {
    private Context context;
    private List<OperationModel.Operation> operations;

    public ListOperationAdapter(Context context, List<OperationModel.Operation> operations) {
        this.operations = operations;
        this.context = context;
    }

    public ListOperationAdapter(Context context) {
        this.context = context;
        operations = new ArrayList<>();
    }

    @Override
    public void setItems(List<OperationModel.Operation> items) {
        operations = items;
    }

    @NonNull
    @Override
    public OperationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View operationItem = LayoutInflater.from(context).inflate(R.layout.item_operation, parent, false);
        return new OperationViewHolder(operationItem);
    }

    @Override
    public void onBindViewHolder(@NonNull OperationViewHolder holder, int position) {
        OperationModel.Operation operation = operations.get(position);
        CategoryModel.Category category = operation.getCategory();
        TypeModel.Type type = operation.getType();

        holder.amount.setText(FormatStr.getAmount(operation.getAmount()));
        holder.date.setText(FormatStr.getDate(operation.getDate()));

        if(category != null) {
            holder.icon.setImageResource(category.getIconID(context));
        }
        if(type != null) {
            holder.type.setText(type.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return (operations == null) ? 0 : operations.size();
    }

    public static class OperationViewHolder extends RecyclerView.ViewHolder {

        private TextView amount;
        private TextView date;
        private TextView type;
        private ImageView icon;

        public OperationViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.typeTitle);
            amount = itemView.findViewById(R.id.operationAmount);
            date = itemView.findViewById(R.id.operationDate);
            icon = itemView.findViewById(R.id.categoryIcon);
        }
    }
}
