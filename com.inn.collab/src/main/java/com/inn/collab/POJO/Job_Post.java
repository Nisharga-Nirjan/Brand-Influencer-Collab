package com.inn.collab.POJO;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.text.ParseException;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@NamedQuery(name = "Job_Post.brandSeePosts", query = "Select new com.inn.collab.wrapper.JobWrapper(j.post_id, j.brand_id, j.name, j.email, j.post, j.post_date) FROM Job_Post j WHERE j.brand_id = :id")
@NamedQuery(name = "Job_Post.InfluencerSeePosts", query = "Select new com.inn.collab.wrapper.JobWrapper(j.post_id, j.brand_id, j.name, j.email, j.post, j.post_date) FROM Job_Post j")
@NamedQuery(name = "Job_Post.findRowById", query = "select j from Job_Post j where j.post_id =: post_id")
@NamedQuery(name = "Job_Post.deleteRowsByBrandId", query = "Delete From Job_Post j where j.brand_id =: id")


//Using getter and setter by default
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "job_post")
public class Job_Post implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer post_id;

    @Column(name = "brand_id", length = 11)
    private Integer brand_id;

    @Column(name= "name", length = 50)
    private String name;


    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "post", length = 1000)
    private String post;

    @Column(name = "post_date")
    private String post_date; // Store as String

    public Job_Post() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
        this.post_date = now.format(formatter);
    }




}

