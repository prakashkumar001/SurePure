package com.sure.pure.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sure.pure.Login;
import com.sure.pure.MainActivity;
import com.sure.pure.R;
import com.sure.pure.adapter.DrawerAdapter;
import com.sure.pure.adapter.ProductListAdapter;
import com.sure.pure.common.GlobalClass;
import com.sure.pure.model.Product;
import com.sure.pure.retrofit.APIInterface;
import com.sure.pure.utils.DrawerItem;
import com.sure.pure.utils.FileUploader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Creative IT Works on 09-Jun-17.
 */

public class Home extends Fragment implements Spinner.OnItemSelectedListener,SearchView.OnQueryTextListener {

    private static final String KEY_MOVIE_TITLE = "key_title";
    public static List<Product> productList = new ArrayList<>();

    public static RecyclerView recyclerView;
    public static ProductListAdapter mAdapter;
    public static Activity a;
    static GlobalClass global;
    static int[] drawables;
    static String title;
    public static String item = "A-Z";
    EditText search;
    ImageView list_grid;
    Typeface fonts,bold;


    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment ProductList.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.home, container, false);
        title = "8";
        fonts = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Monitorica_Rg.ttf");
        bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Monitorica_Bd.ttf");
        Log.i("Title", "Title" + title);
        global = (GlobalClass) getActivity().getApplicationContext();
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        list_grid = (ImageView) v.findViewById(R.id.list_grid);
        TextView privacy = (TextView) v.findViewById(R.id.privacy);
        TextView aboutus = (TextView) v.findViewById(R.id.aboutus);
        TextView copyrights = (TextView) v.findViewById(R.id.copyrights);
        copyrights.setTypeface(bold);
        privacy.setTypeface(bold);
        aboutus.setTypeface(bold);

        a = getActivity();
        global.listmodel = "grid";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        search = (EditText) v.findViewById(R.id.search);
        search.setTypeface(bold);



        list_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.listmodel.equals("grid")) {
                    list_grid.setImageResource(R.drawable.list_icon);
                    global.listmodel = "list";

                    layoutchange();

                } else {
                    list_grid.setImageResource(R.drawable.grid_icon);
                    global.listmodel = "grid";

                    layoutchange1();


                }

            }
        });
        addTextListener();

        getAlldata();


        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();

        if (global.listmodel.equalsIgnoreCase("list")) {
            layoutchange();
        } else {
            layoutchange1();
        }


        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        layoutchange1();


    }




    public static void layoutchange() {

        try {


            String list = "list";
            global.listmodel = list;
            mAdapter = new ProductListAdapter(a.getApplicationContext(), productList, item, list);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(a);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            //recyclerView.addItemDecoration(new DividerItemDecoration(a, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);

            mAdapter.notifyDataSetChanged();


        } catch (Exception e) {

        }



    }


    public static void layoutchange1() {


        try {

            String list = "grid";
            global.listmodel = list;
            mAdapter = new ProductListAdapter(a.getApplicationContext(), productList, item, list);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(a, 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            //recyclerView.addItemDecoration(new DividerItemDecoration(a, LinearLayoutManager.VERTICAL));
            //recyclerView.addItemDecoration(new DividerItemDecoration(a, LinearLayoutManager.HORIZONTAL));

            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            mAdapter.notifyDataSetChanged();
            //setupSearchView();



        } catch (Exception e) {

        }


    }





    @Override
    public boolean onQueryTextChange(String newText) {
        //newText=search.getText().toString();
        final List<Product> filteredModelList = filter(productList, newText);
        mAdapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<Product> filter(List<Product> models, String query) {
        query = query.toLowerCase();

        final List<Product> filteredModelList = new ArrayList<>();
        for (Product model : models) {
            final String text = model.getProductname().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public void addTextListener() {

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final ArrayList<Product> filteredList = new ArrayList<>();

                for (int i = 0; i < productList.size(); i++) {

                    final String text = productList.get(i).getProductname().toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(productList.get(i));
                    }
                }

                if(global.listmodel.equalsIgnoreCase("grid"))
                {

                    String list = "grid";
                    mAdapter = new ProductListAdapter(a.getApplicationContext(), filteredList, item, list);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(a, 2);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    //recyclerView.addItemDecoration(new DividerItemDecoration(a, LinearLayoutManager.VERTICAL));
                    //recyclerView.addItemDecoration(new DividerItemDecoration(a, LinearLayoutManager.HORIZONTAL));

                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setNestedScrollingEnabled(false);
                    mAdapter.notifyDataSetChanged();
                }else
                {
                    String list = "list";
                    mAdapter = new ProductListAdapter(a.getApplicationContext(), filteredList, item, list);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(a);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    //recyclerView.addItemDecoration(new DividerItemDecoration(a, LinearLayoutManager.VERTICAL));
                    //recyclerView.addItemDecoration(new DividerItemDecoration(a, LinearLayoutManager.HORIZONTAL));

                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setNestedScrollingEnabled(false);
                    mAdapter.notifyDataSetChanged();              }
                mAdapter.notifyDataSetChanged();  // data set changed
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }


        public void getAlldata() {

        //Creating a retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        APIInterface api = retrofit.create(APIInterface.class);

        //now making the call object
        //Here we are using the api method that we created inside the api interface
        Call<List<Product>> call = api.getAllProductList();
        call.enqueue(new Callback<List<Product>>() {


            @Override
            public void onResponse(Call<List<Product>> call, retrofit2.Response<List<Product>> response) {
               productList = response.body();





                layoutchange1();


            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }





}