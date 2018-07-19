package com.sure.pure;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.sure.pure.adapter.PaginationAdapter;
import com.sure.pure.common.GlobalClass;
import com.sure.pure.model.Product;
import com.sure.pure.retrofit.APIInterface;
import com.sure.pure.utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bairavi on 7/19/2018.
 */

public class HomeActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener, SearchView.OnQueryTextListener {
    public List<Product> productList;

    public RecyclerView recyclerView;
    public static Activity a;
    static GlobalClass global;
    static String title;
    public static String item = "A-Z";
    EditText search;
    ImageView list_grid;
    Typeface fonts, bold;
    PaginationAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;


    private int currentPage = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        final String categoryValue = this.getIntent().getStringExtra("category");


        // Inflate the layout for this fragment
        title = "8";
        fonts = Typeface.createFromAsset(HomeActivity.this.getAssets(), "fonts/Monitorica_Rg.ttf");
        bold = Typeface.createFromAsset(HomeActivity.this.getAssets(), "fonts/Monitorica_Bd.ttf");
        Log.i("Title", "Title" + title);
        global = (GlobalClass) HomeActivity.this.getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        list_grid = (ImageView) findViewById(R.id.list_grid);
        TextView privacy = (TextView) findViewById(R.id.privacy);
        TextView aboutus = (TextView) findViewById(R.id.aboutus);
        progressBar = (ProgressBar) findViewById(R.id.item_progress_bar);

        TextView copyrights = (TextView) findViewById(R.id.copyrights);
        copyrights.setTypeface(bold);
        privacy.setTypeface(bold);
        aboutus.setTypeface(bold);

        a = HomeActivity.this;
        global.listmodel = "list";




        search = (EditText) findViewById(R.id.search);
        search.setTypeface(bold);


        list_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (global.listmodel.equals("grid")) {
                    list_grid.setImageResource(R.drawable.list_icon);
                    global.listmodel = "list";
                    if (categoryValue != null) {
                        linearLayout();

                    } else {
                        linearLayout();
                    }


                } else {
                    list_grid.setImageResource(R.drawable.grid_icon);
                    global.listmodel = "grid";
                    if (categoryValue != null) {
                        gridLayout();

                    } else {
                        gridLayout();
                    }


                }

            }
        });
        addTextListener();

        productList = new ArrayList<>();
        adapter=new PaginationAdapter(HomeActivity.this,productList);
        linearLayout();
        if (categoryValue != null) {
            getSelectCategory(categoryValue);

        } else {
            addDataToList();
        }
        recyclerView.setAdapter(adapter);
       // recyclerView.setNestedScrollingEnabled(false);


        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (categoryValue != null) {
                    getSelectCategory(categoryValue);

                } else {
                    addDataToList();
                }
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();

        if (global.listmodel.equalsIgnoreCase("list")) {
            linearLayout();
        } else {
            gridLayout();
        }


        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void linearLayout() {

        try {


            String list = "list";
            global.listmodel = list;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
            recyclerView.setLayoutManager(linearLayoutManager);
           // adapter=new PaginationAdapter(HomeActivity.this,productList);
            //recyclerView.setAdapter(adapter);


        } catch (Exception e) {

        }


    }


    public void gridLayout() {


        try {

            String list = "grid";
            global.listmodel = list;
            GridLayoutManager linearLayoutManager = new GridLayoutManager(HomeActivity.this, 2);
            //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
           // adapter=new PaginationAdapter(HomeActivity.this,productList);
           // recyclerView.setAdapter(adapter);


        } catch (Exception e) {

        }


    }


    @Override
    public boolean onQueryTextChange(String newText) {
        //newText=search.getText().toString();
        final List<Product> filteredModelList = filter(productList, newText);
        adapter.setFilter(filteredModelList);
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

                if (global.listmodel.equalsIgnoreCase("grid")) {

                    String list = "grid";

                    productList.addAll(filteredList);
                    adapter.notifyDataSetChanged();
                } else {
                    String list = "list";

                    productList.addAll(filteredList);
                    adapter.notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    public void getSelectCategory(String categoryname) {


        //Creating a retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        APIInterface api = retrofit.create(APIInterface.class);

        //now making the call object
        //Here we are using the api method that we created inside the api interface
        Call<List<Product>> call = api.getSelectedCategoryList(categoryname);
        call.enqueue(new Callback<List<Product>>() {


            @Override
            public void onResponse(Call<List<Product>> call, retrofit2.Response<List<Product>> response) {

                productList = new ArrayList<Product>();
                productList.addAll(response.body());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });

    }


    private void load() {
        //Creating a retrofit object
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        //creating the api interface
        APIInterface api = retrofit.create(APIInterface.class);

        //now making the call object
        //Here we are using the api method that we created inside the api interface
        final String page = APIInterface.BASE_URL + "/rest/index.php/htc?p_id=" + currentPage;
        Call<List<Product>> call = api.getAllProductList(page);
        call.enqueue(new Callback<List<Product>>() {


            @Override
            public void onResponse(Call<List<Product>> call, retrofit2.Response<List<Product>> response) {


                if (response.isSuccessful()) {

                    productList.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    currentPage = currentPage + 1;


                } else {
                    Log.e(" Response Error ", " Response Error " + String.valueOf(response.code()));
                }

                //should call the custom method adapter.notifyDataChanged here to get the correct loading status

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Response Error ", " Response Error " + t.getMessage());
            }
        });
    }

    private void addDataToList() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*for (int i = 0; i <= 30; i++) {
                    data.add("SampleText : " + mLoadedItems);
                    mLoadedItems++;
                }*/
                load();
                progressBar.setVisibility(View.GONE);
            }
        }, 1500);

    }
}
