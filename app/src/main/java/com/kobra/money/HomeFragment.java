package com.kobra.money;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kobra.money.controller.AuthController;
import com.kobra.money.controller.Controller;
import com.kobra.money.controller.OperationController;
import com.kobra.money.view.Loader;
import com.kobra.money.view.OperationView;
import com.kobra.money.view.adapter.ListOperationAdapter;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private Context context;
    private View fragment;

    private Loader loader;

    private SwipeRefreshLayout swipeRefreshLayout;

    private OperationController operationController;

    public HomeFragment() {
        loader = new Loader(1);
        loader.setEvent(new Loader.Event() {
            @Override
            public void onLoad() {
                loader.hide();
            }
        });
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_home, container, false);

        loader.setLoaderView(fragment.findViewById(R.id.loader));
        if(!loader.isLoad()) {
            loader.show();
        }

        swipeRefreshLayout = fragment.findViewById(R.id.swipeRefresh);

        OperationView operationView = new OperationView(context,
                fragment.findViewById(R.id.operationsList), new ListOperationAdapter(context));
        operationView.setEmptyNotify(fragment.findViewById(R.id.emptyOperation));

        operationController = new OperationController.Builder(context)
                .setViewOption(operationView)
                .setAddOption(fragment.findViewById(R.id.formAddOperation))
                .getController();

        HashMap<String, String> operationArgs = new HashMap<String, String>() {{
            put("user_id", Long.toString(AuthController.authUser.getId()));
            put("limit", "3");
            put("include_category", "1");
            put("include_type", "1");
        }};
        HashMap<String, String> categoryArgs = new HashMap<String, String>() {{
            put("limit", "16");
        }};

        operationController.setOperations(operationArgs, new Controller.Event() {
            @Override
            public void onSuccess() {
                loader.add(1);
            }

            @Override
            public void onError(String error) {

            }
        });

        operationController.setCategories(categoryArgs, null);

        operationController.print();

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
            }
        });
    }

    public void update() {
        Loader updateLoader = new Loader(1);
        updateLoader.setEvent(new Loader.Event() {
            @Override
            public void onLoad() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        operationController.update(new Controller.Event() {
            @Override
            public void onSuccess() {
                updateLoader.add(1);
            }

            @Override
            public void onError(String error) {
                updateLoader.add(1);
            }
        });
    }
}