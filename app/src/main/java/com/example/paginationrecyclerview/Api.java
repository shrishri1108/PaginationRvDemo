package com.example.paginationrecyclerview;

import com.example.paginationrecyclerview.Model.DataResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("v2/list")
    Call<String> getApiData(
            @Query("page") int page,
            @Query("limit") int limit
    );
}
