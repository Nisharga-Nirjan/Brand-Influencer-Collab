package com.inn.collab.serviceImpl;

import com.google.common.base.Strings;
import com.inn.collab.JWT.InfluencerUserDetailsService;
import com.inn.collab.JWT.JwtFilter;
import com.inn.collab.JWT.JwtUtil;

import com.inn.collab.POJO.User_Signup;
import com.inn.collab.constents.CollabConstents;
import com.inn.collab.dao.*;
import com.inn.collab.service.UserService;
import com.inn.collab.utils.CollabUtils;
import com.inn.collab.utils.EmailUtils;
import com.inn.collab.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    Brand_Dao brandDao;

    @Autowired
    Influencer_Dao influencerDao;

    @Autowired
    JobDao jobDao;

    @Autowired
    Post_StatusDao postStatusDao;


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    InfluencerUserDetailsService influencerUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User_Signup user_signup = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user_signup)) {
                    Integer id_length = requestMap.get("bin_nid_number").length();
                    if (id_length == 13){  //For Brands
                        userDao.save(getBrandFromMap(requestMap));
                        return CollabUtils.getResponseEntity("Brand Successfully Registered.", HttpStatus.OK);
                    } else if (id_length == 11) {  //For Influencers
                        userDao.save(getInfluencerFromMap(requestMap));
                        return CollabUtils.getResponseEntity("Influencer Successfully Registered.", HttpStatus.OK);
                    }
                    else {
                        return CollabUtils.getResponseEntity("Identification Number is invalid", HttpStatus.BAD_REQUEST);
                    }

                } else {
                    return CollabUtils.getResponseEntity("Email already exists.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CollabUtils.getResponseEntity(CollabConstents.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private boolean validateSignUpMap(Map<String, String> requestMap) {
        return requestMap.containsKey("name") && requestMap.containsKey("password")
                && requestMap.containsKey("bin_nid_number") && requestMap.containsKey("origin_city")
                && requestMap.containsKey("location") && requestMap.containsKey("email");
    }

    private User_Signup getBrandFromMap(Map<String, String> requestMap) {
        User_Signup user_signup = new User_Signup();
        user_signup.setName(requestMap.get("name"));
        user_signup.setPassword(requestMap.get("password"));
        user_signup.setBin_nid_number(requestMap.get("bin_nid_number"));
        user_signup.setOrigin_city(requestMap.get("origin_city"));
        user_signup.setLocation(requestMap.get("location"));
        user_signup.setEmail(requestMap.get("email"));
        user_signup.setStatus("false");
        user_signup.setRole("brand");
        user_signup.setExperience_year("Not Applicable");
        user_signup.setEnglish_proficiency("Not Applicable");
        user_signup.setGender("Not Applicable");
        return user_signup;
    }

    private User_Signup getInfluencerFromMap(Map<String, String> requestMap) {
        User_Signup user_signup = new User_Signup();
        user_signup.setName(requestMap.get("name"));
        user_signup.setPassword(requestMap.get("password"));
        user_signup.setBin_nid_number(requestMap.get("bin_nid_number"));
        user_signup.setOrigin_city(requestMap.get("origin_city"));
        user_signup.setLocation(requestMap.get("location"));
        user_signup.setEmail(requestMap.get("email"));
        user_signup.setStatus("false");
        user_signup.setRole("influencer");
        user_signup.setEstablished_year("Not Applicable");
        return user_signup;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            if (auth.isAuthenticated()){
                if (influencerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+jwtUtil.generateToken(influencerUserDetailsService.getUserDetail().getId(),influencerUserDetailsService.getUserDetail().getEmail(),influencerUserDetailsService.getUserDetail().getName(), influencerUserDetailsService.getUserDetail().getRole()) + "\"}", HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval."+"\"}", HttpStatus.BAD_REQUEST);
                }
            }

        }catch (Exception ex){
            log.error("{}",ex);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials."+"\"}", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if (jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);

            }else {
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new  ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                Optional<User_Signup> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if(!optional.isEmpty()){
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmin(requestMap.get("status"),optional.get().getEmail(),userDao.getAllAdmin());
                    return CollabUtils.getResponseEntity("User Status Updated Successfully", HttpStatus.OK);
                } else {
                    return CollabUtils.getResponseEntity("User id doesn't exist", HttpStatus.OK);
                }
            } else {
                return CollabUtils.getResponseEntity(CollabConstents.UNAUTHORIZED_ACCESS, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if(status!=null && status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved", "USER:- "+user+" \n is approved by \nADMIN:-" + jwtFilter.getCurrentUser(), allAdmin);
        }else {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Disabled", "USER:- "+user+" \n is disabled by \nADMIN:-" + jwtFilter.getCurrentUser(), allAdmin);

        }
    }
    @Override
    public ResponseEntity<String> checkToken() {
        return CollabUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            User_Signup userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if(!userObj.equals(null)){
                if(userObj.getPassword().equals(requestMap.get("oldPassword"))){
                    userObj.setPassword(requestMap.get("newPassword"));
                    userDao.save(userObj);
                    return CollabUtils.getResponseEntity("Password Updated Successfully",HttpStatus.OK);
                }
                return CollabUtils.getResponseEntity("Incorrect Old Password", HttpStatus.BAD_REQUEST);
            }
            return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try{
            User_Signup user = userDao.findByEmail(requestMap.get("email"));
            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())){
                emailUtils.forgotMail(user.getEmail(), "Credentials by BIC Management System", user.getPassword());
            }
            return CollabUtils.getResponseEntity("Check your mail for Credentials.", HttpStatus.OK);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<UserWrapper>> AllProfiles(Integer id) {
        try {

            return new ResponseEntity<>(userDao.SeeAllProfiles(id),HttpStatus.OK);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<UserWrapper> ClickProfile(Integer id) {
        try {
            String role = userDao.findRoleById(id);
            if (role.equals("brand")){
                return new ResponseEntity<>(brandDao.Profile(id),HttpStatus.OK);
            }
            else if (role.equals("influencer")) {
                return new ResponseEntity<>(influencerDao.Profile(id),HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new UserWrapper(), HttpStatus.NOT_FOUND);
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new UserWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> AdminAllProfiles(Integer id) {
        try {

            return new ResponseEntity<>(userDao.SeeAllUserProfiles(id),HttpStatus.OK);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<UserWrapper> AdminClickProfile(Integer id) {
        try {
            String role = userDao.findRoleById(id);
            if (role.equals("brand")){
                return new ResponseEntity<>(brandDao.Profile(id),HttpStatus.OK);
            }
            else if (role.equals("influencer")) {
                return new ResponseEntity<>(influencerDao.Profile(id),HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new UserWrapper(), HttpStatus.NOT_FOUND);
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new UserWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> VerifyUser(Map<String, String> requestMap) {
        try{
            User_Signup user_signup = userDao.findRowById(Integer.parseInt(requestMap.get("id")));
            String status = user_signup.getStatus();
            if (status.equals("false")) {
                user_signup.setStatus("true");
                userDao.save(user_signup);
                return CollabUtils.getResponseEntity("User Verified", HttpStatus.OK);
            }
            else {
                user_signup.setStatus("false");
                userDao.save(user_signup);
                return CollabUtils.getResponseEntity("User Disabled", HttpStatus.OK);
            }



        } catch(Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> DeleteUser(Map<String, String> requestMap) {
        try{
            User_Signup user_signup = userDao.findRowById(Integer.parseInt(requestMap.get("id")));
            String status = user_signup.getRole();
            userDao.deleteRowById(Integer.parseInt(requestMap.get("id")));

            if (status.equals("brand")){
                jobDao.deleteRowsByBrandId(Integer.parseInt(requestMap.get("id")));
                postStatusDao.deleteRowsByBrandId(Integer.parseInt(requestMap.get("id")));
                return CollabUtils.getResponseEntity("Brand has been removed from server", HttpStatus.OK);
            } else { //influencer
                postStatusDao.deleteRowsByInfluencerId(Integer.parseInt(requestMap.get("id")));
                return CollabUtils.getResponseEntity("Influencer has been removed from server", HttpStatus.OK);
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}