package com.inn.collab.service;
import com.inn.collab.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.Map;
public interface Influencer_Service {

    ResponseEntity<UserWrapper> InfluencerProfile(Integer id);

    ResponseEntity<String> editProfile(Map<String, String> requestMap);
}
