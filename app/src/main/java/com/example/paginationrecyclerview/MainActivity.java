package com.example.paginationrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.paginationrecyclerview.Adapter.DataAdapter;
import com.example.paginationrecyclerview.Model.DataResponseModel;
import com.example.paginationrecyclerview.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private ArrayList<DataResponseModel> mylists = new ArrayList<>();
    private int page = 1, limit = 19;
    private DataAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // getting Data from Api
        getData(page, limit);
        mainBinding.scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {

                    // Increase Page Size
                    page++;

                    mainBinding.progressBar.setVisibility(View.VISIBLE);
                    getData(page, limit);
                }
            }
        });
    }

    private void getData(int page, int limit) {

        //  Innitialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        // Create Api Interface
        Api api = retrofit.create(Api.class);
        Call<String> call = api.getApiData(page, limit);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(MainActivity.this, " Response is Successful ! .  ", Toast.LENGTH_SHORT).show();
                    mainBinding.progressBar.setVisibility(View.GONE);
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        parseResult(jsonArray);
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }

                    adapter = new DataAdapter(mylists, MainActivity.this);
                    mainBinding.recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mainBinding.progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, " " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void parseResult(JSONArray jsonArray) {

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //   Initialize DataResponseModel
                DataResponseModel dataResponseModel = new DataResponseModel(jsonObject.getString("download_url"), jsonObject.getString("author"));
                mylists.add(dataResponseModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}