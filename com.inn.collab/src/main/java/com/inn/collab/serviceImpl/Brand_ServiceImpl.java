package com.inn.collab.serviceImpl;
import com.inn.collab.POJO.Job_Post;
import com.inn.collab.POJO.User_Signup;
import com.inn.collab.constents.CollabConstents;
import com.inn.collab.dao.Brand_Dao;
import com.inn.collab.dao.JobDao;
import com.inn.collab.dao.Post_StatusDao;
import com.inn.collab.dao.UserDao;
import com.inn.collab.service.Brand_Service;
import com.inn.collab.utils.CollabUtils;
import com.inn.collab.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class Brand_ServiceImpl implements Brand_Service {
    @Autowired
    Brand_Dao brandDao;



    @Override
    public ResponseEntity<UserWrapper> BrandProfile(Integer id) {
        try {

            return new ResponseEntity<>(brandDao.Profile(id),HttpStatus.OK);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new UserWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> editProfile(Map<String, String> requestMap) {
        try{
            brandDao.save(saveBrandEdit(requestMap));
            return CollabUtils.getResponseEntity("Profile Updated.", HttpStatus.OK);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private User_Signup saveBrandEdit(Map<String, String> requestMap) {

        Integer id = Integer.parseInt(requestMap.get("id"));
        User_Signup existingProfile = brandDao.findById(id).orElse(null);

        if (requestMap.containsKey("name") && requestMap.get("name") != null && !requestMap.get("name").isEmpty()){
            existingProfile.setName(requestMap.get("name"));
        }
        if (requestMap.containsKey("established_year") && requestMap.get("established_year") != null && !requestMap.get("established_year").isEmpty()){
            existingProfile.setEstablished_year(requestMap.get("established_year"));
        }
        if (requestMap.containsKey("location") && requestMap.get("location") != null && !requestMap.get("location").isEmpty()){
            existingProfile.setLocation(requestMap.get("location"));
        }
        if (requestMap.containsKey("origin_city") && requestMap.get("origin_city") != null && !requestMap.get("origin_city").isEmpty()){
            existingProfile.setOrigin_city(requestMap.get("origin_city"));
        }
        if (requestMap.containsKey("phone") && requestMap.get("phone") != null && !requestMap.get("phone").isEmpty()){
            existingProfile.setPhone(requestMap.get("phone"));
        }
        if (requestMap.containsKey("facebook") && requestMap.get("facebook") != null && !requestMap.get("facebook").isEmpty()){
            existingProfile.setFacebook(requestMap.get("facebook"));
        }
        if (requestMap.containsKey("instagram") && requestMap.get("instagram") != null && !requestMap.get("instagram").isEmpty()){
            existingProfile.setInstagram(requestMap.get("instagram"));
        }
        if (requestMap.containsKey("twitter") && requestMap.get("twitter") != null && !requestMap.get("twitter").isEmpty()){
            existingProfile.setTwitter(requestMap.get("twitter"));
        }
        if (requestMap.containsKey("youtube") && requestMap.get("youtube") != null && !requestMap.get("youtube").isEmpty()){
            existingProfile.setYoutube(requestMap.get("youtube"));
        }
        if (requestMap.containsKey("website") && requestMap.get("website") != null && !requestMap.get("website").isEmpty()){
            existingProfile.setWebsite(requestMap.get("website"));
        }

        return  existingProfile;
    }


}
