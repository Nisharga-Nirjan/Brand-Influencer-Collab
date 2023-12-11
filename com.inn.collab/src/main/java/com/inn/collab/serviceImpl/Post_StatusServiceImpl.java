package com.inn.collab.serviceImpl;

import com.inn.collab.POJO.Job_Post;
import com.inn.collab.POJO.Post_Status;
import com.inn.collab.POJO.User_Signup;
import com.inn.collab.constents.CollabConstents;
import com.inn.collab.dao.JobDao;
import com.inn.collab.dao.Post_StatusDao;
import com.inn.collab.dao.UserDao;

import com.inn.collab.service.Post_StatusService;
import com.inn.collab.utils.CollabUtils;

import com.inn.collab.wrapper.Post_StatusWrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class Post_StatusServiceImpl implements Post_StatusService {
    @Autowired
    Post_StatusDao postStatusDao;

    @Autowired
    JobDao jobDao;

    @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<String> storeHireInfo(Map<String, String> requestMap) {
        try{
            Integer count = postStatusDao.HireRecordExist(Integer.parseInt(requestMap.get("id")), Integer.parseInt(requestMap.get("post_id")));
            if (count == 0){
                postStatusDao.save(saveHireInfo(requestMap));
                return CollabUtils.getResponseEntity("Request has been submitted", HttpStatus.OK);
            }
            else {
                return CollabUtils.getResponseEntity("You already requested for this collab", HttpStatus.OK);
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private Post_Status saveHireInfo(Map<String, String> requestMap) {
        Job_Post job_post = jobDao.findRowById(Integer.parseInt(requestMap.get("post_id")));
        User_Signup user_signup = userDao.findRowById(Integer.parseInt(requestMap.get("id")));

        Post_Status post_status = new Post_Status();
        post_status.setPost_id(Integer.parseInt(requestMap.get("post_id")));
        post_status.setApplied_influencers_id(Integer.parseInt(requestMap.get("id")));
        post_status.setStatus("Pending");
        post_status.setPayment("Unpaid");

        post_status.setBrand_id(job_post.getBrand_id());
        post_status.setBrand_email(job_post.getEmail());
        post_status.setBrand_name(job_post.getName());
        post_status.setPost(job_post.getPost());
        post_status.setPost_date(job_post.getPost_date());

        post_status.setInfluencer_email(user_signup.getEmail());
        post_status.setInfluencer_name(user_signup.getName());
        post_status.setGender(user_signup.getGender());
        post_status.setInfluencer_origin_city(user_signup.getOrigin_city());
        post_status.setInfluencer_location(user_signup.getLocation());
        post_status.setInfluencer_phone(user_signup.getPhone());
        post_status.setInfluencer_website(user_signup.getWebsite());
        post_status.setInfluencer_facebook(user_signup.getFacebook());
        post_status.setInfluencer_instagram(user_signup.getInstagram());
        post_status.setInfluencer_twitter(user_signup.getTwitter());
        post_status.setInfluencer_youtube(user_signup.getYoutube());
        post_status.setInfluencer_experience_year(user_signup.getExperience_year());
        post_status.setInfluencer_english_proficiency(user_signup.getEnglish_proficiency());

        return post_status;
    }


    @Override
    public ResponseEntity<List<Post_StatusWrapper>> BrandSeePostOffers(Integer post_id) {
        try {

            return new ResponseEntity<>(postStatusDao.brandSeePostOffers(post_id),HttpStatus.OK);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Post_StatusWrapper>> InfluencerHireStatus(Integer id) {
        try {

            return new ResponseEntity<>(postStatusDao.influencerHireStatus(id),HttpStatus.OK);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try{
            Post_Status post_status = postStatusDao.findRowById(Integer.parseInt(requestMap.get("id"))); //post status id
            String status = post_status.getStatus();
            if (status.equals("Pending")) {
                post_status.setStatus(requestMap.get("status")); //Accepted or Rejected
                postStatusDao.save(post_status);
                return CollabUtils.getResponseEntity("Status Updated.", HttpStatus.OK);
            }
            else {
                return CollabUtils.getResponseEntity("Status has already been updated from Pending. You can't change now for this post", HttpStatus.OK);
            }


        } catch(Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Post_StatusWrapper> GetPaymentInfo(Integer id) {
        try {

            return new ResponseEntity<>(postStatusDao.getPaymentInfo(id),HttpStatus.OK);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new Post_StatusWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> ConfirmPayment(Map<String, String> requestMap) {
        try{
            Post_Status post_status = postStatusDao.findRowById(Integer.parseInt(requestMap.get("id")));
            String payment = post_status.getPayment();
            if (payment.equals("Unpaid")) {
                post_status.setPayment("Paid");
                postStatusDao.save(post_status);
                return CollabUtils.getResponseEntity("Payment Done.", HttpStatus.OK);
            }
            else {
                return CollabUtils.getResponseEntity("Payment has already been done before", HttpStatus.OK);
            }


        } catch(Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
