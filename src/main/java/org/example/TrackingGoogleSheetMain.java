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


public class TrackingGoogleSheetMain {
    private static String spreasdsfID;

    private  static String key = "_5LtoWJwHB2d6yGvPnq5oCLINBUa";

    private static String secret="9gHu_Py1MYvZWnkBfmfx7mxr_uca";
    static RestTemplate restTemplate = new RestTemplate();



    public static void main(String[] args) throws Exception {
        String url1 = "https://gw-bioid.vnpt.vn/s5/2.0/statistic/totalSubscriptionTenants";
        String url2 = "https://gw-bioid.vnpt.vn/s5/2.0/dashboard/transactions?type=day";

        String token = getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity < String > out1= restTemplate.exchange(url1, HttpMethod.GET, entity, String.class);
        System.out.println("OK1: "+ out1.getBody());

        ResponseEntity < String > out2= restTemplate.exchange(url2, HttpMethod.GET, entity, String.class);
        System.out.println("OK2: "+ out2.getBody());
//        /** SmartAds Tool **/
//        HttpHeaders smartAdsHeader = new HttpHeaders();
//        String smartAdsUrl = "https://admin.vnptsmartads.vn/api/reportPlayer";
//        HttpEntity<String> smartAdsEntity = new HttpEntity<>(smartAdsHeader);
//        ResponseEntity<String> smartOut= restTemplate.exchange(smartAdsUrl,HttpMethod.GET,smartAdsEntity,String.class);
//        System.out.println("smart: "+smartOut);
//
//        Gson smartAdsGson = new Gson();
//        Type type = new TypeToken<List<SmartAds>>() {}.getType();
//        List<SmartAds> smartAds = smartAdsGson.fromJson(smartOut.getBody(), type);
//
//
//
//        int smartLine = smartAds.size();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        Date date = new Date();
//        String[][] smartArray = new String[smartLine][5];
//        for(int i=0;i<smartLine;i++){
//            smartArray[i][0]= formatter.format(date);
//            smartArray[i][1]= smartAds.get(i).getMaTinh();
//            smartArray[i][2]= "SmartAds";
//            smartArray[i][3]= "Thiết bị";
//            smartArray[i][4]= smartAds.get(i).getSoLuong();
//        }
//
//        writeData = convert2DArrayToListOfList(smartArray);
//
//
//
//        String smartRange = "SmartAds!A:E";
//        ValueRange bodySmart = new ValueRange()
//                .setValues(writeData);
//
//        Sheets smartService = getSheetsService();
//        AppendValuesResponse smartResult =
//                smartService.spreadsheets().values().append(spreasdsfID, smartRange, bodySmart)
//                        .setValueInputOption("USER_ENTERED")
//                        .execute();


        /** Tracking Tool **/
        HttpHeaders trackingHeader = new HttpHeaders();
        trackingHeader.setBasicAuth("YWRtaW46Vm5wdEAyMDIy");
        String trackingUrl = "https://services.vnpttracking.vn/api/v1/statistic/report?agentId=0";
        HttpEntity<String> trackingEntity = new HttpEntity<>(trackingHeader);
        ResponseEntity<String> trackingOut= restTemplate.exchange(trackingUrl,HttpMethod.GET,trackingEntity,String.class);
        System.out.println("Tracking: "+trackingOut);


        Gson trackingGson = new Gson();
        TrackingModel trackingModel = trackingGson.fromJson(trackingOut.getBody(), TrackingModel.class);

        TrackingResult trackingResult = trackingModel.getResult();

        List<TrackingData> trackingData= trackingResult.getData();

        GoogleSheetApi sheetAPI = new GoogleSheetApi();
//        spreasdsfID = sheetAPI.create("New Test Sheet");

        spreasdsfID = "1iT7Bt5zJOPguJ2ZmYIfuCmbahmUr6IK46nuhAu3xYVQ";

        int line= trackingData.size();
        String[][] trackingArray = new String[line][5];
        for(int i=0;i<trackingData.size();i++){
            trackingArray[i][0]= trackingResult.getNgay();
            trackingArray[i][1]= trackingData.get(i).getMaTinh();
            trackingArray[i][2]= trackingResult.getTenDichVu();
            trackingArray[i][3]= trackingResult.getDonViTinh();
            trackingArray[i][4]= String.valueOf(trackingData.get(i).getSoLuong());
        }

        List<List<Object>> writeData;
        writeData = convert2DArrayToListOfList(trackingArray);



        String trackingRange = "Tracking!A:E";
        ValueRange body = new ValueRange()
                .setValues(writeData);

        Sheets service = getSheetsService();
        AppendValuesResponse result =
                service.spreadsheets().values().append(spreasdsfID, trackingRange, body)
                        .setValueInputOption("USER_ENTERED")
                        .execute();

//        String out2String=out2.getBody();
//        System.out.println(out2String.indexOf("postpaid"));
//        System.out.println(out2String.substring(12,out2String.indexOf("prepaid")-2));

//        Gson gson = new Gson();
//        TransactionResponse transactionResponse = gson.fromJson(out2.getBody(), TransactionResponse.class);
//
//        List<Transaction> prepaid = transactionResponse.getPrepaid();
//
//        String[][]array2 = new String[prepaid.size()][2];
//        for (int i=0; i< prepaid.size();i++){
//            array2[i][0] = prepaid.get(i).getType();
//            array2[i][1] = String.valueOf(prepaid.get(i).getCount());
//        }
//        List<Transaction> postpaid = transactionResponse.getPostpaid();
//
//        String[][]array3 = new String[postpaid.size()][2];
//        for (int i=0; i< postpaid.size();i++){
//            array3[i][0] = postpaid.get(i).getType();
//            array3[i][1] = String.valueOf(postpaid.get(i).getCount());
//        }

//        int indexI=0;
//        while(indexI<array3.length){
//            int indexJ = indexI+1;
//            while (indexJ<array3.length){
//                if (postpaid.get(indexJ).getType()==postpaid.get(indexI).getType()){
//
//                }
//            }
//        }

//        System.out.println(array2);
//
//
//
//
//        JSONArray json = new JSONArray(out1.getBody());
//        String [][] array = new String[json.length()][2];
//        for (int i=0; i< json.length();i++){
//            JSONObject obj = json.getJSONObject(i);
//            array[i][0] = obj.getString("key");
//            array[i][1] = String.valueOf(obj.getInt("value"));
//        }






//        /** Updating the values in sheet **/
//        Object[][] yash = {{"Month", "Mutual Funds", "Stocks", "Total"},
//                            {"Jan", "200", "300", "322"},
//                            {"Feb", "200", "332", "3443"}
//        };
//        System.out.println("Mang 2 chieu "+yash[0][1]);
//
//        /** write totalSubscriptionTenants **/
//
//        //String range="BioID!A1:D3";
//        String range = "BioID!A:D";
//        /** range: Sheet1 or Trang tính1**/
//        sheetAPI.updateValues(spreasdsfID, range, writeData);
//
//        /** write transaction**/
//        writeData = convert2DArrayToListOfList(array2);

//        range="BioID!A4:D"+String.valueOf(4+prepaid.size());
//        range = "BioID!A:D";
//        sheetAPI.updateValues(spreasdsfID,range, writeData);
//        ValueRange body = new ValueRange()
//                .setValues(writeData);
//
//        Sheets service = getSheetsService();
//        AppendValuesResponse result =
//                service.spreadsheets().values().append(spreasdsfID, range, body)
//                        .setValueInputOption("USER_ENTERED")
//                        .execute();


//        writeData = convert2DArrayToListOfList(array3);
//        range = "BioID!A:D";
//        //range="BioID!A"+String.valueOf(6+prepaid.size())+":D"+String.valueOf(6+prepaid.size()+postpaid.size());
//        sheetAPI.updateValues(spreasdsfID,range, writeData);


//        /**Reading from sheet **/
//        /** range: Sheet1 or Trang tính1**/
//        ValueRange range = sheetAPI.getValues(spreasdsfID, "Trang tính1!A1:D3");
//        List<List<Object>> values = sheetAPI.getValues(spreasdsfID, "Trang tính1!A1:D3").getValues();
//        if (values == null || values.isEmpty()) {
//            System.out.println("No data found.");
//        } else {
//            for (List row : values) {
//                System.out.printf("%s, %s\n", row.get(1), row.get(2));
//            }
//        }
//        System.out.println(range.getValues().get(1).get(0));
//
//        /**Formatting of cells **/
//        sheetAPI.formattingCells(spreasdsfID);
//
//        /** Making a line graph **/
//        MakeCharts makeCharts = new MakeCharts();
//        makeCharts.makeChart(spreasdsfID, "title");
    }

    public static String getToken() throws Exception {
        String url = "https://gw-bioid.vnpt.vn/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(key, secret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<TokenDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, TokenDto.class);
        return response.getBody().getAccessToken();
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