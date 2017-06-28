package com.sure.pure.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sure.pure.Order_History;
import com.sure.pure.R;
import com.sure.pure.adapter.DeliverAdapter;
import com.sure.pure.adapter.PendingAdapter;
import com.sure.pure.common.GlobalClass;


/**
 * Created by Creative IT Works on 19-Jun-17.
 */

public class Pending  extends Fragment {

    RecyclerView list;
    GlobalClass globalClass;
    TextView title;
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
        title=(TextView)v.findViewById(R.id.title);

        // Inflate the layout for this fragment
        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i("GGGGGGGGGGGG","GGGGGGGG"+globalClass.deliverdata);


        if(globalClass.pendingdata.size()>0)
        {
            title.setVisibility(View.GONE);
            PendingAdapter adapter=new PendingAdapter(getActivity(), globalClass.pendingdata);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            list.setLayoutManager(mLayoutManager);
            list.setItemAnimator(new DefaultItemAnimator());
            list.setNestedScrollingEnabled(false);
            list.setAdapter(adapter);
            list.setNestedScrollingEnabled(true);
        }else
        {
            title.setVisibility(View.VISIBLE);
            title.setText("No Data Available");

        }




        // Inflate the layout for this fragment

    }

}