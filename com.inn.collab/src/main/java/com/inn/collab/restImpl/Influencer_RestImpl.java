package com.inn.collab.restImpl;

import com.inn.collab.rest.Influencer_Rest;
import com.inn.collab.constents.CollabConstents;
import com.inn.collab.service.Influencer_Service;
import com.inn.collab.utils.CollabUtils;
import com.inn.collab.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class Influencer_RestImpl implements Influencer_Rest {

    @Autowired
    Influencer_Service influencerService;

    @Override
    public ResponseEntity<UserWrapper> InfluencerProfile(Integer id) {
        try {
            return influencerService.InfluencerProfile(id);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new UserWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> editProfile(Map<String, String> requestMap) {
        try{
            return influencerService.editProfile(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
