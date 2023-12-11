package com.inn.collab.service;

import com.inn.collab.wrapper.Post_StatusWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
public interface Post_StatusService {

    ResponseEntity<String> storeHireInfo(Map<String, String> requestMap);

    ResponseEntity<List<Post_StatusWrapper>> BrandSeePostOffers(Integer post_id);

    ResponseEntity<List<Post_StatusWrapper>> InfluencerHireStatus(Integer id);

    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    ResponseEntity<Post_StatusWrapper> GetPaymentInfo(Integer id);

    ResponseEntity<String> ConfirmPayment(Map<String, String> requestMap);

}
