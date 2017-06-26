package com.sure.pure.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sure.pure.Order_History;
import com.sure.pure.R;
import com.sure.pure.adapter.PendingAdapter;
import com.sure.pure.common.GlobalClass;


/**
 * Created by Creative IT Works on 19-Jun-17.
 */

public class Pending  extends Fragment {

    RecyclerView list;
    GlobalClass globalClass;
    public Pending() {
        // Required empty public constructor
        globalClass=new GlobalClass();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.history, container, false);
        list=(RecyclerView)v.findViewById(R.id.orderhistory);
        PendingAdapter adapter=new PendingAdapter(getActivity(), globalClass.pendingdata);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(mLayoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setNestedScrollingEnabled(false);
        list.setAdapter(adapter);
        list.setNestedScrollingEnabled(true);
        // Inflate the layout for this fragment
        return v;
    }

}