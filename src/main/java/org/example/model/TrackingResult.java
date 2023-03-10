package org.example.model;

import lombok.Data;

import java.util.List;

@Data
public class TrackingResult {
    int total;
    String ngay;
    List<TrackingData> data;
    int tongSoThietBi;
    String tenDichVu;
    String donViTinh;
}
