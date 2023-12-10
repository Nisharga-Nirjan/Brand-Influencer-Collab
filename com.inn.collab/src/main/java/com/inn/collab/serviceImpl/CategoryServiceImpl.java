package com.inn.collab.serviceImpl;

import com.google.common.base.Strings;
import com.inn.collab.JWT.JwtFilter;
import com.inn.collab.POJO.Category;
import com.inn.collab.constents.CollabConstents;
import com.inn.collab.dao.CategoryDao;
import com.inn.collab.service.CategoryService;
import com.inn.collab.utils.CollabUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                if (validateCategoryMap(requestMap, false)){
                    categoryDao.save(getCategoryFromMap(requestMap, false));
                    return CollabUtils.getResponseEntity("Category Added Successfully", HttpStatus.OK);
                }
            }else {
                return CollabUtils.getResponseEntity(CollabConstents.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")){
            if (requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId) {
                return true;
            }
        }
        return false;

    }

    private Category getCategoryFromMap(Map<String, String> requestMap,Boolean isAdd){
        Category category = new Category();
        if (isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info("Inside if");
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                if(validateCategoryMap(requestMap, true)){
                    Optional optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        categoryDao.save(getCategoryFromMap(requestMap,true));
                        return CollabUtils.getResponseEntity("Category Updated Successfully", HttpStatus.OK);
                    }else {
                        return CollabUtils.getResponseEntity("Category id does not exist", HttpStatus.OK);
                    }
                }
                return CollabUtils.getResponseEntity(CollabConstents.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }else {
                return CollabUtils.getResponseEntity(CollabConstents.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CollabUtils.getResponseEntity(CollabConstents.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
