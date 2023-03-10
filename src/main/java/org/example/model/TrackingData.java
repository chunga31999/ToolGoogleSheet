package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TrackingData {
    @SerializedName("TenDaiLy")
    String tenDaiLy;
    @SerializedName("TenTinh")
    String tenTinh;
    @SerializedName("MaTinh")
    String maTinh;
    @SerializedName("VNT918")
    int vnt918;
    @SerializedName("VNT102")
    int vnt102;
    @SerializedName("VNT198S")
    int vnt918S;
    @SerializedName("TG102V")
    int tg102V;
    @SerializedName("TG102SE")
    int tg102SE;
    @SerializedName("BK88")
    int bk88;
    @SerializedName("TG102LE")
    int tg102LE;
    @SerializedName("VNPT01M")
    int vnpt01M;
    @SerializedName("SoLuong")
    int soLuong;
}
