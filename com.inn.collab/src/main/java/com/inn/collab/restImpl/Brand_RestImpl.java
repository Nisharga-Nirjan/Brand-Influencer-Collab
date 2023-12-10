package com.inn.collab.restImpl;

import com.inn.collab.rest.Brand_Rest;
import com.inn.collab.constents.CollabConstents;
import com.inn.collab.service.Brand_Service;
import com.inn.collab.utils.CollabUtils;
import com.inn.collab.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Brand_RestImpl implements Brand_Rest {

    @Autowired
    Brand_Service brandService;

    @Override
    public ResponseEntity<UserWrapper> BrandProfile(Integer id) {
        try {
            return brandService.BrandProfile(id);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new UserWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> editProfile(Map<String, String> requestMap) {
        try{
            return brandService.editProfile(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
