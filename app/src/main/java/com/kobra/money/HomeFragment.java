package com.kobra.money;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kobra.money.controller.AuthController;
import com.kobra.money.controller.OperationController;
import com.kobra.money.view.OperationView;
import com.kobra.money.view.adapter.ListOperationAdapter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private Context context;
    private View fragment;

    private OperationController operationController;

    public HomeFragment() {
        // Required empty public constructor
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
        operationController = new OperationController(context);
        operationController.setItems(new HashMap<String, String>(){{
            put("user_id", Long.toString(AuthController.authUser.getId()));
            put("limit", "3");
            put("include_category", "1");
            put("include_type", "1");
        }});
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_home, container, false);

        OperationView operationView = new OperationView(context, fragment.findViewById(R.id.operationsList),
                new ListOperationAdapter(context));
        operationView.setEmptyNotify(fragment.findViewById(R.id.emptyOperation));
        operationController.setView(operationView);
        operationController.print();

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}