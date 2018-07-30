package com.sure.pure.retrofit;

import com.google.gson.JsonElement;
import com.sure.pure.model.Product;
import com.sure.pure.pojo.CheckoutResponse;
import com.sure.pure.pojo.ProductData;
import com.sure.pure.pojo.ProductList;
import com.sure.pure.utils.DrawerItem;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface APIInterface {
    String BASE_URL = "http://www.htcfurniturestore.com";

    @GET("/rest/index.php/htc/product_category")
    Call<List<DrawerItem>> getCategoryList();

    @GET
    Call<List<Product>> getAllProductList(@Url String url);

    @GET("/rest/index.php/htc/product_list/{input}")
    Call<List<Product>> getSelectedCategoryList(@Path("input") String input);
     @POST("/rest/index.php/htc/checkout_cart")
    Call<CheckoutResponse> checkout(@Body ProductList task);

    @FormUrlEncoded
    @POST("/rest/index.php/htc/product_search")
    Call<List<Product>> getSearchFilter(@Field("qry_string") String qry_string);


}