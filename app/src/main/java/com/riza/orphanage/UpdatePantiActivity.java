package com.riza.orphanage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePantiActivity extends AppCompatActivity {
    private ActivityUpdatePantiBinding binding;
    private PantiItem input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatePantiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot);

        input = getIntent().getParcelableExtra("EXTRA_DATA");
        String id = input.getId();

        binding.etNamaPanti.setText(input.getNamaPanti());
        binding.etTentangPanti.setText(input.getTentangPanti());
        binding.etAlamatPanti.setText(input.getAlamatPanti());
        binding.etFotoPanti.setText(input.getFotoPanti());
        binding.etTelephonePanti.setText(input.getTelephonePanti());

        binding.btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama_panti = binding.etNamaPanti.getText().toString();
                String tentang_panti = binding.etTentangPanti.getText().toString();
                String alamat_panti = binding.etAlamatPanti.getText().toString();
                String foto_panti = binding.etFotoPanti.getText().toString();
                String telephone_panti = binding.etTelephonePanti.getText().toString();

                boolean bolehUpdateNamaPanti = Util.inputError(nama_panti, binding.etNamaPanti, "nama panti");
                boolean bolehUpdateTentangPanti = Util.inputError(tentang_panti, binding.etTentangPanti, "tentang panti");
                boolean bolehUpdateAlamatPanti = Util.inputError(alamat_panti, binding.etFotoPanti, "alamat panti");
                boolean bolehUpdateFotoPanti = Util.inputError(foto_panti, binding.etFotoPanti, "foto panti");
                boolean bolehUpdateTelephonePanti = Util.inputError(telephone_panti, binding.etTelephonePanti, "telephone panti");

                if (bolehUpdateNamaPanti && bolehUpdateTentangPanti && bolehUpdateAlamatPanti && bolehUpdateFotoPanti && bolehUpdateTelephonePanti) {
                    savePantiToAPI(id, nama_panti, tentang_panti, alamat_panti, foto_panti, telephone_panti);
                }
            }
        });
    }

    private void savePantiToAPI(String id, String nama_panti, String tentang_panti, String alamat_panti, String foto_panti, String telephone_panti) {
        binding.progressbar.setVisibility(View.VISIBLE);
        APIService api = Util.getRetrofit().create(APIService.class);
        Call<PantiItem> call = api.updatePanti(idPanti, namapanti, tentangpanti, alamatpanti, fotopanti, telephonepanti);

        call.enqueue(new Callback<PantiItem>() {
            @Override
            public void onResponse(Call<PantiItem> call, Response<PantiItem> response) {
                binding.progressbar.setVisibility(View.VISIBLE);
                Log.e("UpdatePantiActivity", "Response Code: " + response.message());
                if (response.code()==200){
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    if (success == 1){
                        Toast.makeText(UpdatePantiActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UpdatePantiActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UpdatePantiActivity.this, "Response" + response.code(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<PantiItem> call, Throwable t) {
                binding.progressbar.setVisibility(View.VISIBLE);
                System.out.println("Retrofit Error : " + t.getMessage());
                Toast.makeText(UpdatePantiActivity.this, "Retrofit Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}