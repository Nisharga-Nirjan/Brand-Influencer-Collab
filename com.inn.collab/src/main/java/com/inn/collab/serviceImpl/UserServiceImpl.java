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
import com.inn.collab.utils.RSAUtils;
import com.inn.collab.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.KeyFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
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

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    private KeyPair generateUserKeyPair() throws Exception {
        return RSAUtils.generateKeyPair();
    }

    // Method to encrypt user data
    private String encryptData(String data, PublicKey publicKey) {
        try {
            return RSAUtils.encrypt(data, publicKey);
        } catch (Exception ex) {
            log.error("Error encrypting data: {}", ex.getMessage());
            return null;
        }
    }




    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User_Signup existingUser = userDao.findByEmailId(requestMap.get("email"));
                if (existingUser == null) {
                    String password = requestMap.get("password");
                    String salt = BCrypt.gensalt(); // Generate random salt
                    String saltedPassword = password + salt;
                    String hashedPassword = passwordEncoder.encode(saltedPassword);

                    User_Signup newUser;
                    Integer idLength = requestMap.get("bin_nid_number").length();
                    if (idLength == 13) {
                        newUser = getBrandFromMap(requestMap);
                    } else if (idLength == 11) {
                        newUser = getInfluencerFromMap(requestMap);
                    } else {
                        return CollabUtils.getResponseEntity("Identification Number is invalid", HttpStatus.BAD_REQUEST);
                    }

                    // Generate RSA key pair for the user
                    KeyPair userKeyPair = generateUserKeyPair();

                    // Set the generated private key to the privateKey field
                    newUser.setPrivateKey(Base64.getEncoder().encodeToString(userKeyPair.getPrivate().getEncoded()));

                    // Encrypt sensitive data using the user's public key
                    newUser.setBin_nid_number(encryptData(requestMap.get("bin_nid_number"), userKeyPair.getPublic()));
                    newUser.setOrigin_city(encryptData(requestMap.get("origin_city"), userKeyPair.getPublic()));
                    newUser.setLocation(encryptData(requestMap.get("location"), userKeyPair.getPublic()));

                    // Hashing+salting
                    newUser.setPassword(hashedPassword);
                    newUser.setSalt(salt);

                    // Set the user's public key to the publicKey field
                    newUser.setPublicKey(Base64.getEncoder().encodeToString(userKeyPair.getPublic().getEncoded()));

                    userDao.save(newUser);

                    return CollabUtils.getResponseEntity("User Successfully Registered.", HttpStatus.OK);
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
            String email = requestMap.get("email");
            String password = requestMap.get("password");

            User_Signup user = userDao.findByEmail(email);
            if (user != null) {
                String storedPassword = user.getPassword();
                String storedSalt = user.getSalt();

                // Combine provided password with stored salt
                String saltedPassword = password + storedSalt;

                // Use BCrypt to verify the password
                if (passwordEncoder.matches(saltedPassword, storedPassword)) {
                    if ("true".equalsIgnoreCase(user.getStatus())) {
                        // Generate and return JWT token upon successful login
                        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getName(), user.getRole(), user.getPublicKey(), user.getPrivateKey());
                        return ResponseEntity.ok("{\"token\":\"" + token + "\"}");
                    } else {
                        return ResponseEntity.badRequest().body("{\"message\":\"Wait for admin approval.\"}");
                    }
                } else {
                    return ResponseEntity.badRequest().body("{\"message\":\"Bad Credentials.\"}");
                }
            } else {
                return ResponseEntity.badRequest().body("{\"message\":\"Bad Credentials.\"}");
            }
        } catch (Exception ex) {
            log.error("Error during login: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Something went wrong.\"}");
        }
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
