package org.example;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.*;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.example.GoogleSheetApi.getSheetsService;
import java.util.ArrayList;
import java.util.List;

public class SmartAdsGoogleSheetMain {

    private static String spreasdsfID;

    static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) throws Exception {
        /** SmartAds Tool **/

        spreasdsfID = "1iT7Bt5zJOPguJ2ZmYIfuCmbahmUr6IK46nuhAu3xYVQ";
        HttpHeaders smartAdsHeader = new HttpHeaders();
        String smartAdsUrl = "https://admin.vnptsmartads.vn/api/reportPlayer";
        HttpEntity<String> smartAdsEntity = new HttpEntity<>(smartAdsHeader);
        ResponseEntity<String> smartOut= restTemplate.exchange(smartAdsUrl,HttpMethod.GET,smartAdsEntity,String.class);
        System.out.println("smart: "+smartOut);

        Gson smartAdsGson = new Gson();
        Type type = new TypeToken<List<SmartAds>>() {}.getType();
        List<SmartAds> smartAds = smartAdsGson.fromJson(smartOut.getBody(), type);



        int smartLine = smartAds.size();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String[][] smartArray = new String[smartLine][5];
        for(int i=0;i<smartLine;i++){
            smartArray[i][0]= formatter.format(date);
            smartArray[i][1]= smartAds.get(i).getMaTinh();
            smartArray[i][2]= "SmartAds";
            smartArray[i][3]= "Thiết bị";
            smartArray[i][4]= smartAds.get(i).getSoLuong();
        }
        List<List<Object>> writeData;

        writeData = convert2DArrayToListOfList(smartArray);



        String smartRange = "Smart Ads!A:E";
        ValueRange bodySmart = new ValueRange()
                .setValues(writeData);

        Sheets smartService = getSheetsService();
        AppendValuesResponse smartResult =
                smartService.spreadsheets().values().append(spreasdsfID, smartRange, bodySmart)
                        .setValueInputOption("USER_ENTERED")
                        .execute();
    }
    public static List<List<Object>> convert2DArrayToListOfList(Object[][] array) {
        List<List<Object>> writeData = new ArrayList<List<Object>>();
        for (Object[] someData : array) {
            List<Object> dataRow = new ArrayList<Object>();
            for (int i = 0; i < array[0].length; i++) {
                dataRow.add(someData[i]);
            }
            writeData.add(dataRow);
        }
        return writeData;
    }
}
