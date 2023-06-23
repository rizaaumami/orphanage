package com.riza.orphanage;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    @GET("get")
    Call<Panti> getPanti();

    @FormUrlEncoded
    @POST("add")
    Call<DataPanti>
    addPanti(
            @Field("nama_panti") String namaPanti,
            @Field("tentang_panti") String tentangPanti,
            @Field("alamat_panti") String alamatPanti,
            @Field("foto_panti") Integer fotoPanti,
            @Field("telephone_panti") String telephonePanti
    );

    @FormUrlEncoded
    @PUT("update/{id}")
    Call<DataPanti>
    updatePanti(
            @Path("id") String id,
            @Field("nama_panti") String namaPanti,
            @Field("tentang_panti") String tentangPanti,
            @Field("alamat_panti") String alamatPanti,
            @Field("foto_panti") Integer fotoPanti,
            @Field("telephone_panti") String telephonePanti
    );

    @DELETE("delete/{id}")
    Call<DataPanti>
    deletePanti(
            @Path("id") String id
    );
}
