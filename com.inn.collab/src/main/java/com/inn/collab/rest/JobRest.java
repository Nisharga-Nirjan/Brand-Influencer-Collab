package com.inn.collab.rest;

import com.inn.collab.wrapper.JobWrapper;
import com.inn.collab.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path="/job")
public interface JobRest {
    @PostMapping(path = "/post")
    public ResponseEntity<String> post(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/brand/seePost/{id}")
    public ResponseEntity<List<JobWrapper>> BrandSeePost(@PathVariable Integer id);

    @GetMapping(path = "/influencer/seePost")
    public ResponseEntity<List<JobWrapper>> InfluencerSeePosts();

}
