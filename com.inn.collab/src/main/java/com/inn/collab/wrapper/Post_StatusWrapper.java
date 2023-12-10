package com.inn.collab.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Post_StatusWrapper {

    private Integer id;

    private Integer post_id;

    private Integer applied_influencers_id;

    private String status;

    private String payment;

    private Integer brand_id;

    private String brand_email;

    private String brand_name;

    private String post;

    private String post_date;

    private String influencer_email;

    private String influencer_name;

    private String gender;

    private String influencer_origin_city;

    private String influencer_location;

    private String influencer_phone;

    private String influencer_website;

    private String influencer_facebook;

    private String influencer_instagram;

    private String influencer_twitter;

    private String influencer_youtube;

    private String influencer_experience_year;

    private String influencer_english_proficiency;



    public Post_StatusWrapper(Integer id, Integer post_id, Integer applied_influencers_id, String status, String payment, Integer brand_id,String brand_email,String brand_name, String post, String post_date,String influencer_email,String influencer_name,String gender, String influencer_origin_city,String influencer_location,String influencer_phone,String influencer_website,String influencer_facebook,String influencer_instagram,String influencer_twitter,String influencer_youtube,String influencer_experience_year,String influencer_english_proficiency) {
        this.id = id;
        this.post_id = post_id;
        this.applied_influencers_id = applied_influencers_id;
        this.status = status;
        this.payment = payment;
        this.brand_id = brand_id;
        this.brand_email = brand_email;
        this.brand_name = brand_name;
        this.post = post;
        this.post_date = post_date;
        this.influencer_email = influencer_email;
        this.influencer_name = influencer_name;
        this.gender = gender;
        this.influencer_origin_city = influencer_origin_city;
        this.influencer_location = influencer_location;
        this.influencer_phone = influencer_phone;
        this.influencer_website = influencer_website;
        this.influencer_facebook = influencer_facebook;
        this.influencer_instagram = influencer_instagram;
        this.influencer_twitter = influencer_twitter;
        this.influencer_youtube = influencer_youtube;
        this.influencer_experience_year = influencer_experience_year;
        this.influencer_english_proficiency = influencer_english_proficiency;
    }

    public Post_StatusWrapper(Integer id,  Integer post_id, Integer brand_id, String brand_email, String brand_name, String post, String post_date, String status, String payment) {
        this.id = id;
        this.post_id = post_id;
        this.brand_id = brand_id;
        this.brand_email = brand_email;
        this.brand_name = brand_name;
        this.post = post;
        this.post_date = post_date;
        this.status = status;
        this.payment = payment;
    }

    public Post_StatusWrapper(Integer id, String brand_name, String influencer_name) {
        this.id = id;
        this.brand_name = brand_name;
        this.influencer_name = influencer_name;
    }


}


