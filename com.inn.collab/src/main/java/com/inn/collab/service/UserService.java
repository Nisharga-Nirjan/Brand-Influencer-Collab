package com.inn.collab.service;

import com.inn.collab.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<String> signUp(Map<String, String> requestMap);

    ResponseEntity<String> login(Map<String, String> requestMap);

    ResponseEntity<List<UserWrapper>> getAllUser();

    ResponseEntity<String> update(Map<String, String> requestMap);

    ResponseEntity<String> checkToken();

    ResponseEntity<String> changePassword(Map<String, String> requestMap);

    ResponseEntity<String> forgotPassword(Map<String, String> requestMap);

    ResponseEntity<List<UserWrapper>> AllProfiles(Integer id);

    ResponseEntity<UserWrapper> ClickProfile(Integer id);

    ResponseEntity<List<UserWrapper>> AdminAllProfiles(Integer id);

    ResponseEntity<UserWrapper> AdminClickProfile(Integer id);

    ResponseEntity<String> VerifyUser(Map<String, String> requestMap);

    ResponseEntity<String> DeleteUser(Map<String, String> requestMap);
}
