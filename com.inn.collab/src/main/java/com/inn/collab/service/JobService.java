package com.inn.collab.service;

import com.inn.collab.wrapper.JobWrapper;
import com.inn.collab.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.*;
import java.util.List;
import java.util.Map;
public interface JobService {
    ResponseEntity<String> post(Map<String, String> requestMap);

    ResponseEntity<List<JobWrapper>> BrandSeePost(Integer id);

    ResponseEntity<List<JobWrapper>> InfluencerSeePosts();
}
