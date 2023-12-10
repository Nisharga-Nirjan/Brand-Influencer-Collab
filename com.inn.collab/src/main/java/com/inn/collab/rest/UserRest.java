package com.inn.collab.rest;

import com.inn.collab.wrapper.JobWrapper;
import com.inn.collab.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path="/user")
public interface UserRest {

    @PostMapping(path="/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String,String> requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllUser();

    @PostMapping(path = "/update")
    public ResponseEntity<String> update(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path="/checkToken")
    public ResponseEntity<String> checkToken();

    @PostMapping(path="/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap);

    @PostMapping(path="/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/allProfiles/{id}")
    public ResponseEntity<List<UserWrapper>> AllProfiles(@PathVariable Integer id);

    @GetMapping(path = "/clickProfile/{id}")
    public ResponseEntity<UserWrapper> ClickProfile(@PathVariable Integer id);

    @GetMapping(path = "/admin/allProfiles/{id}")  //Can't see other admins
    public ResponseEntity<List<UserWrapper>> AdminAllProfiles(@PathVariable Integer id);

    @GetMapping(path = "/admin/clickProfile/{id}")
    public ResponseEntity<UserWrapper> AdminClickProfile(@PathVariable Integer id);

    @PostMapping(path = "/admin/verifyUser")
    public ResponseEntity<String> VerifyUser(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/admin/deleteUser")
    public ResponseEntity<String> DeleteUser(@RequestBody(required = true) Map<String, String> requestMap);

}
