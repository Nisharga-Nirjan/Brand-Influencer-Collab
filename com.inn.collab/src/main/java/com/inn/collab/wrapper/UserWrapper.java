package com.inn.collab.wrapper;

import com.inn.collab.utils.RSAUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.security.PrivateKey;


@Data
@NoArgsConstructor
public class UserWrapper {


    private Integer id;

    private String email;

    private String name;

    private String password;

    private String gender;

    private String bin_nid_number;

    private String origin_city;

    private String location;

    private String status;

    private String role;

    private String phone;

    private String established_year;

    private String website;

    private String facebook;

    private String instagram;

    private String twitter;

    private String youtube;

    private String experience_year;

    private String english_proficiency;

    private String privateKey;




    public UserWrapper(Integer id, String email, String name, String password, String gender, String bin_nid_number, String origin_city, String location, String status,String phone,String established_year, String website,String facebook,String instagram, String twitter,String youtube, String experience_year,String english_proficiency, String privateKey ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.bin_nid_number = bin_nid_number;
        this.origin_city = origin_city;
        this.location = location;
        this.status = status;
        this.phone = phone;
        this.established_year = established_year;
        this.website = website;
        this.facebook = facebook;
        this.instagram = instagram;
        this.twitter = twitter;
        this.youtube = youtube;
        this.experience_year = experience_year;
        this.english_proficiency = english_proficiency;
        this.privateKey = privateKey;
    }

    public UserWrapper(Integer id, String email, String name, String gender, String origin_city, String location, String status, String role, String phone,String established_year, String website,String facebook,String instagram, String twitter,String youtube, String experience_year,String english_proficiency, String privateKey ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.origin_city = origin_city;
        this.location = location;
        this.status = status;
        this.role = role;
        this.phone = phone;
        this.established_year = established_year;
        this.website = website;
        this.facebook = facebook;
        this.instagram = instagram;
        this.twitter = twitter;
        this.youtube = youtube;
        this.experience_year = experience_year;
        this.english_proficiency = english_proficiency;
        this.privateKey = privateKey;
    }
}


