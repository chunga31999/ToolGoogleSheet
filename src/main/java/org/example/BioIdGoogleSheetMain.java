package org.example;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class BioIdGoogleSheetMain {
    private static String spreasdsfID;
    private  static String key = "_5LtoWJwHB2d6yGvPnq5oCLINBUa";
    private static String secret="9gHu_Py1MYvZWnkBfmfx7mxr_uca";
    static RestTemplate restTemplate = new RestTemplate();
    public static void main(String[] args) {


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
}
