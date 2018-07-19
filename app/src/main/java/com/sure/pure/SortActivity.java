package com.sure.pure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.sure.pure.adapter.SortAdapter;
import com.sure.pure.common.GlobalClass;
import com.sure.pure.fragments.Home;
import com.sure.pure.model.HolderClass;

import java.util.ArrayList;

/**
 * Created by v-62 on 11/19/2016.
 */

public class SortActivity extends AppCompatActivity {

    ListView list;
    public static ArrayList<String> data;
    Button Ok,Cancel;
    GlobalClass global;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort);
        data=new ArrayList<String>();
        global=(GlobalClass)getApplicationContext();
        Ok=(Button)findViewById(R.id.Ok);
        Cancel=(Button)findViewById(R.id.cancel);
/* list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String values=parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        data.add("Quantity High-Low");
        data.add("Quantity Low-High");
        data.add("Price Low-High");
        data.add("Price High-Low");
        list=(ListView)findViewById(R.id.list);

       // ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.list_content,data);

        SortAdapter adapter=new SortAdapter(getApplicationContext(),data);
        list.setAdapter(adapter);


        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HolderClass.optionSelected>-1)
                {

                    Home home=new Home();

                    home.item= SortActivity.data.get(HolderClass.optionSelected);
                        if(global.listmodel.equalsIgnoreCase("list"))
                        {
                            home.linearLayout();
                        }else
                        {
                            home.gridLayout();
                        }
                    finish();


                }else {
                    Toast.makeText(getApplicationContext(),"Please Select Option", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HolderClass.optionSelected=-1;
                finish();
            }
        });

    }
}
