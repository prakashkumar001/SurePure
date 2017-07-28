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
import com.sure.pure.adapter.ProductListAdapter;
import com.sure.pure.common.GlobalClass;
import com.sure.pure.model.Product;
import com.sure.pure.utils.FileUploader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Creative IT Works on 09-Jun-17.
 */

public class Home extends Fragment implements Spinner.OnItemSelectedListener,SearchView.OnQueryTextListener {

    private static final String KEY_MOVIE_TITLE = "key_title";
    public static ArrayList<Product> productList = new ArrayList<>();

    public static RecyclerView recyclerView;
    public static ProductListAdapter mAdapter;
    public static Activity a;
    static GlobalClass global;
    static int[] drawables;
    static String title;
    public static String item = "A-Z";
    EditText search;
    ImageView list_grid;


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

        Log.i("Title", "Title" + title);
        global = (GlobalClass) getActivity().getApplicationContext();
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        list_grid = (ImageView) v.findViewById(R.id.list_grid);

        a = getActivity();
        global.listmodel = "grid";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        search = (EditText) v.findViewById(R.id.search);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Comfortaa_Regular.ttf");
        search.setTypeface(typeface);


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

    void productDetails() {
        String tag_json_arry = "json_array_req";

        String url = "http://sridharchits.com/market/index.php/mobile/getProductById";

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest req = new StringRequest(Request.Method.GET, url + "?cat_id=" + title,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());
                        pDialog.hide();
                        productList.clear();
                        JSONArray arr=null;
                        try {
                            arr = new JSONArray(response);
                        }catch (Exception e)
                        {

                        }

                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject object = arr.getJSONObject(i);
                                String productname = object.getString("product_name");
                                String productid = object.getString("p_id");
                                String productdescription = object.getString("prd_description");
                                String productretailprice = object.getString("retails_price");
                                String productsellerprice = object.getString("selling_price");
                                //String productimage = object.getString("prd_image");

                                if(productdescription.equals("null"))
                                {
                                    productdescription= "No description";
                                }

                                Product p = new Product(productid, productname, null, productsellerprice, productretailprice, productdescription,productretailprice);

                                productList.add(p);

                                layoutchange1();

                            } catch (Exception e) {

                            }


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

// Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(req);
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

    public void getAlldata()
    {
        class uploadTOserver extends AsyncTask<String, Void, String> {
            ProgressDialog dialog;
            String response=null;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog=new ProgressDialog(getActivity());
                dialog.setMessage("Loading..");
                dialog.show();
            }
            @Override
            protected String doInBackground(String[] params) {
                try {
                    String charset = "UTF-8";
                    String requestURL = "http://sridharchits.com/surepure/index.php/mobile/get_products";
                    FileUploader multipart = new FileUploader(requestURL, charset,getActivity());

                    String response = multipart.finish();

                    System.out.println("SERVER REPLIED:"+response);

                    productList.clear();

                    JSONArray arr=new JSONArray(response);
                    for(int i=0;i<arr.length();i++) {

                        try {
                            JSONObject object = arr.getJSONObject(i);
                            String productdescription = null;

                            productdescription = object.getString("description");

                            String productname = object.getString("prd_name");
                            String productid = object.getString("id");
                            //String productretailprice = object.getString("prd_price");
                            String productsellerprice = object.getString("prd_price");
                            String productimage = object.getString("img_url");

                            if (productdescription.equals("null")) {
                                productdescription = "No description";
                            }

                            Product p = new Product(productid, productname, productimage, productsellerprice, "", productdescription,productsellerprice);

                            productList.add(p);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }



                } catch (IOException ex) {
                    System.err.println(ex);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return response;
            }


            @Override
            protected void onPostExecute(String o) {

                dialog.dismiss();
                layoutchange1();
            }
        } new uploadTOserver().execute();


    }

}