package com.example.manuel.testapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.manuel.testapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSets)
    public void startSetsActivity(View view){
        if (isNetworkAvailable()) {
            Intent intent = new Intent(this, SetsActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(MainActivity.this, "Doesn't work the network", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.btnEpisodes)
    public void startEpisodesActivity(View view){

        if (isNetworkAvailable()) {
            Intent intent = new Intent(this, EpisodeActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(MainActivity.this, "Doesn't work the network", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;

    }


}
