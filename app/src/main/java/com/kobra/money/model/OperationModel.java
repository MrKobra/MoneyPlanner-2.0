package com.kobra.money.model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kobra.money.R;
import com.kobra.money.entity.Operation;
import com.kobra.money.request.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OperationModel extends Model {
    private HashMap<String, String> args;

    public OperationModel(Context context) {
        super(context);
    }

    public void getItemsFromHTTP(HashMap<String, String> args, GetEvent<Operation> event) {
        setRun(true);
        this.args = args;

        HttpRequest.getInstance(context).sendRequest(args, HttpRequest.Entity.OPERATION,
                HttpRequest.Action.GET, new HttpRequest.Event() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            if (response.getInt("error") == 0) {
                                JSONArray items = response.getJSONArray("items");
                                if (event != null) event.onSuccess(getOperationList(items));
                            } else {
                                if (event != null) event.onError(context.getString(R.string.get_error));
                            }
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                            if (event != null) event.onError(context.getString(R.string.get_error));
                        }

                        setRun(false);
                    }

                    @Override
                    public void onError(Exception exception) {
                        exception.printStackTrace();
                        if (event != null) event.onError(context.getString(R.string.network_error));
                        setRun(false);
                    }
                });
    }

    public void updateItemsFromHTTP(GetEvent<Operation> event) {
        setRun(true);

        HttpRequest.getInstance(context).sendRequest(args, HttpRequest.Entity.OPERATION,
                HttpRequest.Action.GET, new HttpRequest.Event() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            if(response.getInt("error") == 0) {
                                JSONArray items = response.getJSONArray("items");
                                if (event != null) event.onSuccess(getOperationList(items));
                            } else {
                                event.onError(context.getString(R.string.get_error));
                            }
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                            if (event != null) event.onError(context.getString(R.string.get_error));
                        }

                        setRun(false);
                    }

                    @Override
                    public void onError(Exception exception) {
                        exception.printStackTrace();
                        if(event != null) event.onError(context.getString(R.string.network_error));
                        setRun(false);
                    }
                });
    }

    public void addOperation(HashMap<String, String> args, Event event) {
        setRun(true);

        HttpRequest.getInstance(context).sendRequest(args, HttpRequest.Entity.OPERATION,
                HttpRequest.Action.ADD, new HttpRequest.Event() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            if(response.getInt("error") == 0) {
                                if (event != null) event.onSuccess();
                            } else {
                                if (event != null) event.onError(context.getString(R.string.add_error));
                            }
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                            if(event != null) event.onError(context.getString(R.string.get_error));
                        }

                        setRun(false);
                    }

                    @Override
                    public void onError(Exception exception) {
                        exception.printStackTrace();
                        if(event != null) event.onError(context.getString(R.string.network_error));
                        setRun(false);
                    }
                });
    }

    private List<Operation> getOperationList(JSONArray items) {
        List<Operation> operations = new ArrayList<Operation>();
        for (int i = 0; i < items.length(); i++) {
            try {
                JSONObject item = items.getJSONObject(i);
                Operation operation = new Operation(item);
                operations.add(operation);
            }
            catch (JSONException exception) {
                exception.printStackTrace();
            }
        }
        return operations;
    }

    /*private List<Operation> list;
    private boolean running;
    private OperationController.Event event;
    private HashMap<String, String> queryArgs;

    public OperationModel(OperationController.Event event) {
        this.event = event;
        list = new ArrayList<>();
        running = false;
    }

    public void setList(JSONArray items) {
        list.clear();
        add(items);
    }

    public void update(HttpRequest request, Event event) {
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

        request.request(responseListener, errorListener, HttpRequest.Entity.OPERATION,
                "get", queryArgs);
    }

    public boolean isRunning() {
        return running;
    }

    public List<Operation> getList() {
        return list;
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
    } */
}
