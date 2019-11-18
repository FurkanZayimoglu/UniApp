package com.furkanzayimoglu.btchallange.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.furkanzayimoglu.btchallange.activities.LoginActivity;
import com.furkanzayimoglu.btchallange.network.OnItemClickListener;
import com.furkanzayimoglu.btchallange.R;
import com.furkanzayimoglu.btchallange.model.TokenManager;
import com.furkanzayimoglu.btchallange.adapters.UniAdapter;
import com.furkanzayimoglu.btchallange.activities.UniDetailActivity;
import com.furkanzayimoglu.btchallange.model.UniModel;

import java.util.ArrayList;


public class ListFragment extends Fragment implements OnItemClickListener {

    private RecyclerView recyclerView;
    private UniAdapter adapter;

    static TokenManager tokenManager;

    ArrayList<UniModel> uniModels = new ArrayList<>();

    public static ListFragment newInstance(ArrayList<UniModel> uniModels) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("uniModels", uniModels);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uniModels = getArguments().getParcelableArrayList("uniModels");
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new UniAdapter(getContext(), uniModels);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        tokenManager = TokenManager.getInstance(getActivity().getSharedPreferences("logintoken", Context.MODE_PRIVATE));
        System.out.println("null mu geldi ? " + tokenManager.getToken().getAccessToken());
    }

    @Override
    public void onItemClick(View view, int positon) {
        if (tokenManager.getToken().getAccessToken() == null) {
            Intent i = new Intent(getActivity(), LoginActivity.class);
            i.putExtra("loginId", uniModels.get(positon).getId());
            startActivity(i);
        } else {
            Intent i = new Intent(getActivity(), UniDetailActivity.class);
            i.putExtra("loginId", uniModels.get(positon).getId());
            startActivity(i);
        }


    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
