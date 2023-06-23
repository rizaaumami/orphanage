package com.riza.orphanage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.riza.orphanage.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private PantiViewAdapter pantiViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getAllInput();

        pantiViewAdapter = new PantiViewAdapter();
        binding.rvPanti.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPanti.setAdapter(pantiViewAdapter);

        pantiViewAdapter.setOnItemLongClickListener(this::dataClick);
        pantiViewAdapter.setOnItemLongClickListener(new PantiViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, DataPanti item, int position) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                popupMenu.inflate(R.menu.menu_popup);
                popupMenu.setGravity(Gravity.RIGHT);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem input) {
                        int idMenu = Integer.parseInt(item.getId());
                        if (idMenu == R.id.action_edit) {
                            Intent intent = new Intent(MainActivity.this, UpdatePantiActivity.class);
                            intent.putExtra("EXTRA DATA", input);
                            startActivity(intent);
                            return true;
                        } else if (idMenu == R.id.action_delete) {
                            if (input != null) {
                                String id = input.getId();
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Konfirmasi");
                                builder.setMessage("Yakin Ingin Menghapus Konser" + input.getNamaCafe() + "?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteInput(id);
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();

                                alertDialog.show();
                            } else {
                                // Tampilkan pesan atau lakukan tindakan jika input null
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
        binding.fabInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);

            }
        });
    }
    private void dataClick(View view, DataPanti item, int i) {
        Intent intent = new Intent(MainActivity.this, DetailPantiActivity.class);
        intent.putExtra("EXTRA_DATA", item);
        startActivity(intent);
    }
    private void deleteInput(String id) {
        APIService api = Util.getRetrofit().create(APIService.class);
        Call<DataPanti> call = api.deletePanti(id);
        call.enqueue(new Callback<DataPanti>() {
            @Override
            public void onResponse(Call<DataPanti> call, Response<DataPanti> response) {
                Log.e("MainActivity", "Get Delete Response Code: " + response.code());
                if (response.code() == 200) {
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    if (success == 1) {
                        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();.
                        makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        getAllInput();
                    } else {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Response " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DataPanti> call, Throwable t) {
                Log.e("MainActivity", "Retrofit Error : " + t.getMessage());
                Toast.makeText(MainActivity.this, "Retrofit Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        getAllInput();
    }

    private void getAllInput() {
        Log.e("MainActivity", "Main GetAllInput()");
        binding.progressbar.setVisibility(View.VISIBLE);
        APIService api = Util.getRetrofit().create(APIService.class);
        Call<Panti> call = api.getPanti();
        call.enqueue(new Callback<Panti>() {
            @Override
            public void onResponse(Call<Panti> call, Response<Panti> response) {
                binding.progressbar.setVisibility(View.GONE);
                Log.e("MainActivity", "Response:" + response.code());
                if (response.code() == 200) {
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    if (success == 1) {
                        List<DataPanti> dataPanti = response.body().getData();
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        PantiViewAdapter.setData(dataPanti);
                    }
                }
            }

            @Override
            public void onFailure(Call<Panti> call, Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
                System.out.println("Retrofit Error : " + t.getMessage());
                Toast.makeText(MainActivity.this, "Retrofit Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
