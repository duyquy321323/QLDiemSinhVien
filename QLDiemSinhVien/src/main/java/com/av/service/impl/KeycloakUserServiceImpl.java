package com.av.service.impl;

import com.av.service.KeycloakUserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakUserServiceImpl implements KeycloakUserService {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm-admin}")
    private String realmAdmin;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id-number}")
    private String clientIdNumber;

    @Value("${keycloak.client-id-admin}")
    private String clientIdAdmin;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    public void assignRoleToUser(String userId, String roleName) throws IOException {
        Map<String, Object> accessTokenCp = getAccessToken();
        String accessToken = accessTokenCp.get("access_token").toString();

        // Lấy thông tin role
        Map<String, Object> roleInfo = findRoleByName(roleName, accessToken);
        if (roleInfo == null) {
            System.out.println("Không tìm thấy role: " + roleName);
            return;
        }

        // Gán role cho user
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(serverUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/clients" + clientIdNumber);
            httpPost.setHeader(new BasicHeader("Authorization", "Bearer " + accessToken));
            httpPost.setHeader(new BasicHeader("Content-Type", "application/json"));

            // JSON body chứa role cần gán
            String jsonBody = "[" + new ObjectMapper().writeValueAsString(roleInfo) + "]";
            httpPost.setEntity(new StringEntity(jsonBody));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                System.out.println("Gán role " + roleName + " cho user " + userId + " - Status: " + response.getStatusLine().getStatusCode());
            }
        }
    }

    private List<Map<String, Object>> getRoleInfo(String roleName, String accessToken) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(serverUrl + "/admin/realms/" + realm + "/clients/" + clientIdNumber + "/roles");
            httpGet.setHeader(new BasicHeader("Authorization", "Bearer " + accessToken));
            httpGet.setHeader(new BasicHeader("Content-Type", "application/json"));

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    return null;
                }
                String responseBody = new String(response.getEntity().getContent().readAllBytes());
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(responseBody, new TypeReference<List<Map<String, Object>>>() {});
            }
        }
    }

    public Map<String, Object> findRoleByName(String roleName, String accessToken) throws IOException {
        List<Map<String, Object>> roles = getRoleInfo(roleName, accessToken);
        if (roles == null) return null;

        for (Map<String, Object> role : roles) {
            if (roleName.equals(role.get("name"))) {
                return role;
            }
        }
        return null; // Không tìm thấy role
    }


    public Map<String, Object> getAccessToken() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(serverUrl + "/realms/" + realmAdmin + "/protocol/openid-connect/token");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

            List<BasicNameValuePair> formParams = Arrays.asList(
                    new BasicNameValuePair("client_id", clientIdAdmin),
                    new BasicNameValuePair("grant_type", "password"),
                    new BasicNameValuePair("username", adminUsername),
                    new BasicNameValuePair("password", adminPassword)
            );

            httpPost.setEntity(new UrlEncodedFormEntity(formParams));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseBody = new String(response.getEntity().getContent().readAllBytes());
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(responseBody, Map.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
