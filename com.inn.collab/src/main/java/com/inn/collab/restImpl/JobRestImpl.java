package com.inn.collab.restImpl;

import com.inn.collab.constents.CollabConstents;
import com.inn.collab.rest.JobRest;
import com.inn.collab.service.JobService;
import com.inn.collab.service.Post_StatusService;
import com.inn.collab.utils.CollabUtils;
import com.inn.collab.wrapper.JobWrapper;
import com.inn.collab.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RestController
public class JobRestImpl implements JobRest {

    @Autowired
    JobService jobService;

    @Override
    public ResponseEntity<String> post(Map<String, String> requestMap) {
        try{
            return jobService.post(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<JobWrapper>> BrandSeePost(Integer id) {
        try {
            return jobService.BrandSeePost(id);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<JobWrapper>> InfluencerSeePosts() {
        try {
            return jobService.InfluencerSeePosts();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
