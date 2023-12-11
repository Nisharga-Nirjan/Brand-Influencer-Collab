package com.inn.collab.serviceImpl;

import com.inn.collab.POJO.Job_Post;

import com.inn.collab.constents.CollabConstents;
import com.inn.collab.dao.JobDao;

import com.inn.collab.dao.UserDao;
import com.inn.collab.service.JobService;
import com.inn.collab.utils.CollabUtils;

import com.inn.collab.wrapper.JobWrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class JobServiceImpl implements JobService{
    @Autowired
    JobDao jobDao;

    @Autowired
    UserDao userDao;


    @Override
    public ResponseEntity<String> post(Map<String, String> requestMap) {
        try{
            jobDao.save(savePost(requestMap));
            return CollabUtils.getResponseEntity("Posted.", HttpStatus.OK);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private Job_Post savePost(Map<String, String> requestMap) {
        Job_Post job_post = new Job_Post();
        job_post.setPost(requestMap.get("post"));
        job_post.setName(userDao.findByEmailId(requestMap.get("email")).getName());
        job_post.setEmail(requestMap.get("email"));
        job_post.setBrand_id(userDao.findByEmailId(requestMap.get("email")).getId());
        return  job_post;
    }

    @Override
    public ResponseEntity<List<JobWrapper>> BrandSeePost(Integer id) {
        try {

            return new ResponseEntity<>(jobDao.brandSeePosts(id),HttpStatus.OK);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<JobWrapper>> InfluencerSeePosts() {
        try {

            return new ResponseEntity<>(jobDao.InfluencerSeePosts(),HttpStatus.OK);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
