package com.riza.orphanage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.riza.orphanage.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama_panti = binding.etNamaPanti.getText().toString();
                String tentang_panti = binding.etTentangPanti.getText().toString();
                String alamat_panti = binding.etAlamatPanti.getText().toString();
                String foto_panti = binding.etFotoPanti.getText().toString();
                String telephone_panti = binding.etTelephonePanti.getText().toString();

                boolean bolehInputNamaPanti = Util.inputError(nama_panti,binding.etNamaPanti, "nama panti");
                boolean bolehInputTentangPanti = Util.inputError(tentang_panti,binding.etTentangPanti, "tentang panti");
                boolean bolehInputAlamatPanti = Util.inputError(alamat_panti,binding.etAlamatPanti, "Alamat Panti");
                boolean bolehInputFotoPanti = Util.inputError(foto_panti,binding.etFotoPanti, "foto panti");
                boolean bolehInputTelephonePanti = Util.inputError(telephone_panti,binding.etTelephonePanti, "telephone panti");
                
                if (bolehInputNamaPanti && bolehInputTentangPanti && bolehInputAlamatPanti
                && bolehInputFotoPanti && bolehInputTelephonePanti) {
                    savePantiToAPI(nama_panti, tentang_panti, alamat_panti, foto_panti, telephone_panti);
                }
            }
        });

    }

    private void savePantiToAPI(String nama_panti, String tentang_panti, String alamat_panti, String foto_panti, String telephone_panti) {
        binding.progressbar.setVisibility(View.VISIBLE);
        APIService api = Util.getRetrofit().create(APIService.class);
        Call<DataPanti> call = api.addPanti(namaPanti, tentangPanti, alamatPanti, fotoPanti, telephonePanti);
        
        call.enqueue(new Callback<DataPanti>() {
            @Override
            public void onResponse(Call<DataPanti> call, Response<DataPanti> response) {
                binding.progressbar.setVisibility(View.VISIBLE);
                if (response.code()==200){
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    if (success == 1){
                        Toast.makeText(InputActivity.this,message, Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(InputActivity.this,message, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    Toast.makeText(InputActivity.this, "Response" + response.code(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DataPanti> call, Throwable t) {
                binding.progressbar.setVisibility(View.VISIBLE);
                System.out.println("Retrofit Error : " + t.getMessage());
                Toast.makeText(InputActivity.this, "Retrofit Error : " + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}