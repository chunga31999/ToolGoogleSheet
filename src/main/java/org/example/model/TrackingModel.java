package org.example.model;


import lombok.Data;

import java.util.List;
@Data
public class TrackingModel {
    String code;
    Boolean status;

    String message;

    TrackingResult result;

}
