package com.quanlydiemsinhvien.qldsv.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanlydiemsinhvien.qldsv.dto.GiangVienDTO;
import com.quanlydiemsinhvien.qldsv.dto.SinhVienDTO;
import com.quanlydiemsinhvien.qldsv.dto.UserDTO;
import com.quanlydiemsinhvien.qldsv.service.KeycloakUserService;

@Service
@Transactional
public class KeycloakUserServiceImpl implements KeycloakUserService {

    @Value("${keycloak.realm-admin}")
    private String realmAdmin;

    @Value("${keycloak.client-id-number}")
    private String clientIdNumber;

    @Value("${keycloak.client-id-admin}")
    private String clientIdAdmin;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id-admin}")
    private String adminClient;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

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
            HttpPost httpPost = new HttpPost(serverUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/clients/" + clientIdNumber);
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

    @Override
    public boolean checkOldPassword(String username, String oldPassword) {
    try {
        String url = String.format("%s/realms/%s/protocol/openid-connect/token", serverUrl, realm);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", clientId);
        requestBody.add("grant_type", "password");
        requestBody.add("username", username);
        requestBody.add("password", oldPassword);
        requestBody.add("client_secret", clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.POST, request, String.class);

        return response.getStatusCode() == HttpStatus.OK;
    } catch (HttpClientErrorException e) {
        System.err.println("Mật khẩu cũ không đúng: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        return false;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    @Override
    public boolean updateUserPassword(String userId, String newPassword) {
        try {
            String url = String.format("%s/admin/realms/%s/users/%s/reset-password", serverUrl, realm, userId);

            // Tạo request body
            Map<String, Object> passwordMap = new HashMap<>();
            passwordMap.put("type", "password");
            passwordMap.put("value", newPassword);
            passwordMap.put("temporary", false); // false để mật khẩu không bị reset sau lần đăng nhập đầu tiên

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(getAdminAccessToken());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(passwordMap, headers);
            ResponseEntity<Void> response = new RestTemplate().exchange(url, HttpMethod.PUT, request, Void.class);

            return response.getStatusCode() == HttpStatus.NO_CONTENT; // Trả về true nếu cập nhật thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
                return objectMapper.readValue(responseBody, new TypeReference<>() {});
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


    public Map<String, Object> getAccessToken() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = getHttpPost();

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

    @NotNull
    private HttpPost getHttpPost() throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(serverUrl + "/realms/" + realmAdmin + "/protocol/openid-connect/token");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

        List<BasicNameValuePair> formParams = Arrays.asList(
                new BasicNameValuePair("client_id", clientIdAdmin),
                new BasicNameValuePair("grant_type", "password"),
                new BasicNameValuePair("username", adminUsername),
                new BasicNameValuePair("password", adminPassword)
        );

        httpPost.setEntity(new UrlEncodedFormEntity(formParams));
        return httpPost;
    }

    @Override
    public String createUserInKeycloak(UserDTO user) {
        try {
            String url = String.format("%s/admin/realms/%s/users", serverUrl, realm);

            // Tạo request body
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("username", user.getEmail());
            userMap.put("email", user.getEmail()); // Thêm email vào tài khoản
            userMap.put("firstName", user.getHoTen().split(" ", 2)[0]); // Lấy họ
            userMap.put("lastName", user.getHoTen().split(" ", 2)[1]); // Lấy tên
            userMap.put("enabled", true);
            userMap.put("emailVerified", true);

            Map<String, String> credentials = new HashMap<>();
            credentials.put("type", "password");
            credentials.put("value", "defaultPassword"); // Đặt mật khẩu mặc định
            credentials.put("temporary", "false");

            userMap.put("credentials", Collections.singletonList(credentials));

            // Gửi request HTTP POST để tạo tài khoản
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(getAdminAccessToken());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(userMap, headers);
            ResponseEntity<Void> response = new RestTemplate().exchange(url, HttpMethod.POST, request, Void.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                // Lấy ID của user mới tạo
                String userId = getKeycloakUserId(user.getEmail());

                if (userId != null) {
                    if(user instanceof SinhVienDTO){
                        this.assignRoleToUser(userId, "SV");
                    } else if (user instanceof GiangVienDTO){
                        this.assignRoleToUser(userId, "GV");
                    }
                }

                return userId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
public boolean deleteUser(String userId) {
    try {
        String url = String.format("%s/admin/realms/%s/users/%s", serverUrl, realm, userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAdminAccessToken());

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Void> response = new RestTemplate().exchange(url, HttpMethod.DELETE, request, Void.class);

        return response.getStatusCode() == HttpStatus.NO_CONTENT; // Xóa thành công
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    @Override
    public boolean updateUser(String userId, String newEmail, String fullName) {
        try {
            String url = String.format("%s/admin/realms/%s/users/%s", serverUrl, realm, userId);
    
            // Tách họ và tên từ fullName
            String[] parts = fullName.split(" ", 2);
            String firstName = parts.length > 1 ? parts[0] : fullName;
            String lastName = parts.length > 1 ? parts[1] : "";
    
            // Tạo body request
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("email", newEmail);
            userMap.put("firstName", firstName);
            userMap.put("lastName", lastName);
            userMap.put("emailVerified", true); // Cập nhật trạng thái xác minh email
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(getAdminAccessToken());
    
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(userMap, headers);
            ResponseEntity<Void> response = new RestTemplate().exchange(url, HttpMethod.PUT, request, Void.class);
    
            return response.getStatusCode() == HttpStatus.NO_CONTENT; // Trả về true nếu cập nhật thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getKeycloakUserId(String username) {
        String url = String.format("%s/admin/realms/%s/users?username=%s", serverUrl, realm, username);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAdminAccessToken());

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<List> response = new RestTemplate().exchange(url, HttpMethod.GET, request, List.class);

        if (response.getBody() != null && !response.getBody().isEmpty()) {
            Map<String, Object> user = (Map<String, Object>) response.getBody().get(0);
            return (String) user.get("id");
        }

        return null;
    }

    /**
     * Phương thức lấy access token của admin Keycloak
     */
    private String getAdminAccessToken() {
        String tokenUrl = String.format("%s/realms/master/protocol/openid-connect/token", serverUrl);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", adminClient);
        body.add("username", adminUsername);
        body.add("password", adminPassword);
        body.add("grant_type", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = new RestTemplate().exchange(tokenUrl, HttpMethod.POST, request, Map.class);

        return response.getBody().get("access_token").toString();
    }

    @Override
    public String getUsernameByUserId(String userId) {
        try {
            String url = String.format("%s/admin/realms/%s/users/%s", serverUrl, realm, userId);
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(getAdminAccessToken());
    
            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
    
            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> userMap = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
    
                return userMap.get("username").toString(); // Trả về username
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy hoặc có lỗi xảy ra
    }

   @Override
public List<String> getUserRoles(String userId) {
    try {
        String url = String.format("%s/admin/realms/%s/users/%s/role-mappings", serverUrl, realm, userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getAdminAccessToken());

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());

            List<String> roleNames = new ArrayList<>();

            // Lấy danh sách realm roles
            if (root.has("realmMappings")) {
                for (JsonNode role : root.get("realmMappings")) {
                    roleNames.add(role.get("name").asText());
                }
            }

            // Lấy danh sách client roles
            if (root.has("clientMappings")) {
                JsonNode clientMappings = root.get("clientMappings");
                for (JsonNode client : clientMappings) {
                    if (client.has("mappings")) {
                        for (JsonNode role : client.get("mappings")) {
                            roleNames.add(role.get("name").asText());
                        }
                    }
                }
            }

            return roleNames;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return Collections.emptyList();
}


@Override
public String getUserIdByUsername(String username) {
    try {
        String url = String.format("%s/admin/realms/%s/users?username=%s", serverUrl, realm, username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getAdminAccessToken()); // Lấy token admin

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> users = objectMapper.readValue(response.getBody(), new TypeReference<>() {});

            if (!users.isEmpty()) {
                return users.get(0).get("id").toString(); // Lấy userId của user đầu tiên
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null; // Trả về null nếu không tìm thấy
}
}
