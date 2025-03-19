package com.av.service;

public interface KeycloakUserService {
    public void assignRoleToUser(String userId, String roleName) throws Exception;
}
