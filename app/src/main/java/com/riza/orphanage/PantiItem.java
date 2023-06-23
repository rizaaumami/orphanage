package com.riza.orphanage;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class PantiItem implements Parcelable {
	@SerializedName("success")
	private int success;

	@SerializedName("message")
	private String message;

	@SerializedName("nama_panti")
	private String namaPanti;

	@SerializedName("tentang_panti")
	private String tentangPanti;

	@SerializedName("alamat_panti")
	private String alamatPanti;

	@SerializedName("foto_panti")
	private String fotoPanti;

	@SerializedName("telephone_panti")
	private String telephonePanti;

	@SerializedName("_id")
	private String id;

	protected PantiItem(Parcel in) {
		namaPanti = in.readString();
		tentangPanti = in.readString();
		alamatPanti = in.readString();
		fotoPanti = in.readString();
		telephonePanti = in.readString();
		id = in.readString();
	}

	public static final Creator<PantiItem> CREATOR = new Creator<PantiItem>() {
		@Override
		public PantiItem createFromParcel(Parcel in) {
			return new PantiItem(in);
		}

		@Override
		public PantiItem[] newArray(int size) {
			return new PantiItem[size];
		}
	};

	public int getSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public String getNamaPanti() {
		return namaPanti;
	}

	public String getTentangPanti() {
		return tentangPanti;
	}

	public String getAlamatPanti() {
		return alamatPanti;
	}

	public String getFotoPanti() {
		return fotoPanti;
	}

	public String getTelephonePanti() {
		return telephonePanti;
	}

	public String getId() {
		return id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel dest, int flags) {
		dest.writeString(namaPanti);
		dest.writeString(tentangPanti);
		dest.writeString(alamatPanti);
		dest.writeString(fotoPanti);
		dest.writeString(telephonePanti);
		dest.writeString(id);
	}
}
