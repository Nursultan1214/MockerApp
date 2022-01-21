package com.example.lesson25;

import android.app.Application;

import com.example.lesson25.data.remote.PostApi;
import com.example.lesson25.data.remote.RetrofitClient;

public class App extends Application {

    private RetrofitClient client;
    public static PostApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        client = new RetrofitClient();
        api = client.provideApi();
    }
}
