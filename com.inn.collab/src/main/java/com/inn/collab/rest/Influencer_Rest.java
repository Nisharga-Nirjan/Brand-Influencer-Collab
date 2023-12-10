package com.inn.collab.rest;
import com.inn.collab.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RequestMapping(path="/influencer")
public interface Influencer_Rest {
    @GetMapping(path = "/profile/{id}")
    public ResponseEntity<UserWrapper> InfluencerProfile(@PathVariable Integer id);

    @PostMapping(path = "/edit")
    public ResponseEntity<String> editProfile(@RequestBody(required = true) Map<String, String> requestMap);



}
