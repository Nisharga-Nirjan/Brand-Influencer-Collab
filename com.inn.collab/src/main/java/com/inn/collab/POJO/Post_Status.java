package com.inn.collab.POJO;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;

@NamedQuery(name = "Post_Status.HireRecordExist", query = "Select COUNT(p) From Post_Status p Where p.applied_influencers_id=:id And p.post_id=:post_id")
@NamedQuery(name = "Post_Status.brandSeePostOffers", query = "Select new com.inn.collab.wrapper.Post_StatusWrapper(p.id, p.post_id, p.applied_influencers_id, p.status, p.payment, p.brand_id, p.brand_email, p.brand_name, p.post, p.post_date, p.influencer_email, p.influencer_name, p.gender, p.influencer_origin_city, p.influencer_location, p.influencer_phone, p.influencer_website, p.influencer_facebook, p.influencer_instagram, p.influencer_twitter, p.influencer_youtube, p.influencer_experience_year, p.influencer_english_proficiency) FROM Post_Status p WHERE p.post_id = :post_id")
@NamedQuery(name = "Post_Status.influencerHireStatus", query = "Select new com.inn.collab.wrapper.Post_StatusWrapper(p.id, p.post_id, p.brand_id, p.brand_email, p.brand_name, p.post, p.post_date, p.status, p.payment) FROM Post_Status p WHERE p.applied_influencers_id = :id")
@NamedQuery(name = "Post_Status.findRowById",query = "select p from Post_Status p where p.id=:id")
@NamedQuery(name = "Post_Status.getPaymentInfo", query = "select new com.inn.collab.wrapper.Post_StatusWrapper(p.id, p.brand_name, p.influencer_name) From Post_Status p where p.id =: id")
@NamedQuery(name = "Post_Status.deleteRowsByBrandId", query = "Delete From Post_Status p where p.brand_id =: id")
@NamedQuery(name = "Post_Status.deleteRowsByInfluencerId", query = "Delete From Post_Status p where p.applied_influencers_id =: id")

//Using getter and setter by default
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "post_status")
public class Post_Status implements Serializable {

    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;



    @Column(name = "post_id", length = 11)
    private Integer post_id;

    @Column(name = "applied_influencers_id", length = 11)
    private Integer applied_influencers_id;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "payment", length = 10)
    private String payment;

    //brand side
    @Column(name = "brand_id", length = 11)
    private Integer brand_id;

    @Column(name = "brand_email", length = 50)
    private String brand_email;

    @Column(name = "brand_name", length = 50)
    private String brand_name;

    @Column(name = "post", length = 1000)
    private String post;

    @Column(name = "post_date")
    private String post_date;

    //influencer side
    @Column(name= "influencer_email", length = 50)
    private String influencer_email;

    @Column(name= "influencer_name", length = 50)
    private String influencer_name;

    @Column(name= "gender", length = 20)
    private String gender;

    @Column(name= "influencer_origin_city", length = 20)
    private String influencer_origin_city;

    @Column(name= "influencer_location", length = 100)
    private String influencer_location;

    @Column(name = "influencer_phone", length = 11)
    private String influencer_phone;

    @Column(name = "influencer_website", length = 100)
    private String influencer_website;

    @Column(name = "influencer_facebook", length = 100)
    private String influencer_facebook;

    @Column(name = "influencer_instagram", length = 100)
    private String influencer_instagram;

    @Column(name = "influencer_twitter", length = 100)
    private String influencer_twitter;

    @Column(name = "influencer_youtube", length = 100)
    private String influencer_youtube;

    @Column(name = "influencer_experience_year", length = 50)
    private String influencer_experience_year;

    @Column(name = "influencer_english_proficiency", length = 50)
    private String influencer_english_proficiency;



}

