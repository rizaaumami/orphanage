package com.riza.orphanage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailPantiActivity extends AppCompatActivity {
    private DetailPantiActivity binding;
    private PantiItem pantiItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}