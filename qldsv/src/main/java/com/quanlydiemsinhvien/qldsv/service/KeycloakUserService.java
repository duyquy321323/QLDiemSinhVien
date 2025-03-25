package com.quanlydiemsinhvien.qldsv.service;

import java.util.List;

import com.quanlydiemsinhvien.qldsv.dto.UserDTO;

public interface KeycloakUserService {
    public void assignRoleToUser(String userId, String roleName) throws Exception;
    public String createUserInKeycloak(UserDTO user);
    public boolean updateUser(String userId, String newEmail, String fullName) ;
    public boolean deleteUser(String userId);
    public String getKeycloakUserId(String username);
    public boolean updateUserPassword(String userId, String newPassword);
    public boolean checkOldPassword(String username, String oldPassword);
    public String getUsernameByUserId(String userId) ;
    public List<String> getUserRoles(String userId);
    public String getUserIdByUsername(String username);
}
