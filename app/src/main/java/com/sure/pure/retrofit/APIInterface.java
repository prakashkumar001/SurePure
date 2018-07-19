package com.sure.pure.retrofit;

import com.google.gson.JsonElement;
import com.sure.pure.model.Product;
import com.sure.pure.pojo.CheckoutResponse;
import com.sure.pure.pojo.ProductList;
import com.sure.pure.utils.DrawerItem;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface APIInterface {
    String BASE_URL = "http://www.boolfox.com";
    @Headers("Content-Type: application/json")

    @GET("/rest/index.php/htc/product_category")
    Call<List<DrawerItem>> getCategoryList();

    @GET
    Call<List<Product>> getAllProductList(@Url String url);

    @GET("/rest/index.php/htc/product_list/{input}")
    Call<List<Product>> getSelectedCategoryList(@Path("input") String input);
     @POST("/rest/index.php/htc/checkout_cart")
    Call<String> checkout(@Body ProductList task);

    /*@GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}