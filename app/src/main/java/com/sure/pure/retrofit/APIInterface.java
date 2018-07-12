package com.sure.pure.retrofit;

import com.sure.pure.model.Product;
import com.sure.pure.utils.DrawerItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    String BASE_URL = "http://www.boolfox.com";

    @GET("/rest/index.php/htc/product_category")
    Call<List<DrawerItem>> getCategoryList();

    @GET("/rest/index.php/htc/")
    Call<List<Product>> getAllProductList();

    @GET("/rest/index.php/htc/product_list/{input}")
    Call<List<Product>> getSelectedCategoryList(@Path("input") String input);
    /* @POST("/api/users")
    Call<User> createUser(@Body User user);

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}