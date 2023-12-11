package com.inn.collab.rest;

import com.inn.collab.wrapper.Post_StatusWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RequestMapping(path = "/collab")
public interface Post_StatusRest {

    @PostMapping(path = "/influencer/hire")
    public ResponseEntity<String> storeHireInfo(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/brand/seePostOffers/{post_id}")
    public ResponseEntity<List<Post_StatusWrapper>> BrandSeePostOffers(@PathVariable Integer post_id);

    @GetMapping(path = "/influencer/hireStatus/{id}")
    public ResponseEntity<List<Post_StatusWrapper>> InfluencerHireStatus(@PathVariable Integer id);

    @PostMapping("/brand/offerStatusUpdate")
    public ResponseEntity<String> updateStatus(@RequestBody(required = true) Map<String, String> requestMap);


    @GetMapping("/payment/{id}")
    public ResponseEntity<Post_StatusWrapper> GetPaymentInfo(@PathVariable Integer id); //post status id

    @PostMapping("/confirmPayment")
    public ResponseEntity<String> ConfirmPayment(@RequestBody(required = true) Map<String, String> requestMap);


}
