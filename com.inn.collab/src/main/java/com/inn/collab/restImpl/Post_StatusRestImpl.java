package com.inn.collab.restImpl;

import com.inn.collab.constents.CollabConstents;
import com.inn.collab.rest.Post_StatusRest;
import com.inn.collab.service.Post_StatusService;
import com.inn.collab.utils.CollabUtils;
import com.inn.collab.wrapper.Post_StatusWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RestController
public class Post_StatusRestImpl implements Post_StatusRest {
    @Autowired
    Post_StatusService postStatusService;

    @Override
    public ResponseEntity<String> storeHireInfo(Map<String, String> requestMap) {
        try{
            return postStatusService.storeHireInfo(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Post_StatusWrapper>> BrandSeePostOffers(Integer post_id) {
        try {
            return postStatusService.BrandSeePostOffers(post_id);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Post_StatusWrapper>> InfluencerHireStatus(Integer id) {
        try {
            return postStatusService.InfluencerHireStatus(id);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            return postStatusService.updateStatus(requestMap);

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Post_StatusWrapper> GetPaymentInfo(Integer id) {
        try {
            return postStatusService.GetPaymentInfo(id);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new Post_StatusWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> ConfirmPayment(Map<String, String> requestMap) {
        try {
            return postStatusService.ConfirmPayment(requestMap);

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
