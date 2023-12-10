package com.inn.collab.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class JobWrapper {

    private Integer post_id;

    private Integer brand_id;

    private String name;

    private String email;

    private String post;

    private String post_date;

    public JobWrapper(Integer post_id, Integer brand_id, String name, String email, String post, String post_date) {
        this.post_id = post_id;
        this.brand_id = brand_id;
        this.name = name;
        this.email = email;
        this.post = post;
        this.post_date = post_date;
    }

}

