package com.kobra.money.model;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kobra.money.controller.OperationController;
import com.kobra.money.request.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OperationModel {
    private List<Operation> list;
    private boolean running;
    private OperationController.Event event;

    public OperationModel(OperationController.Event event) {
        this.event = event;
        list = new ArrayList<>();
        running = false;
    }

    public void setList(JSONArray items) {
        list.clear();
        add(items);
    }

    public void setList(HashMap<String, String> args, CustomRequest request, Event event) {
        setRunning(true);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    if(result.getInt("error") == 0) {
                        JSONArray items = result.getJSONArray("items");
                        setList(items);
                        if(event != null) event.onSuccess();
                    } else {
                        if(event != null) event.onError();
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                    if(event != null) event.onError();
                }

                setRunning(false);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(event != null) event.onError();
                setRunning(false);
            }
        };

        request.request(responseListener, errorListener, CustomRequest.Entity.OPERATION,
                "get", args);
    }

    public boolean isRunning() {
        return running;
    }

    private void add(JSONArray items) {
        for (int i = 0; i < items.length(); i++) {
            try {
                JSONObject item = items.getJSONObject(i);
                Operation operation = new Operation(item);
                list.add(operation);
            }
            catch (JSONException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void setRunning(boolean flag) {
        running = flag;
        if(!isRunning()) {
            event.onReady();
        }
    }

    public interface Event {
        void onSuccess();
        void onError();
    }

    public static class Operation {
        private long id;
        private int amount;
        private long typeId;
        private long categoryId;
        private long bankCardId;
        private LocalDate date;

        public Operation(JSONObject item) throws JSONException {
            id = item.getLong("id");
            amount = item.getInt("amount");
            typeId = item.getLong("type_id");
            categoryId = item.getLong("term_id");
            bankCardId = item.getLong("bank_card_id");
            date = LocalDate.parse(item.getString("date"));
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public long getTypeId() {
            return typeId;
        }

        public void setTypeId(long typeId) {
            this.typeId = typeId;
        }

        public long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(long categoryId) {
            this.categoryId = categoryId;
        }

        public long getBankCardId() {
            return bankCardId;
        }

        public void setBankCardId(long bankCardId) {
            this.bankCardId = bankCardId;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }
}
