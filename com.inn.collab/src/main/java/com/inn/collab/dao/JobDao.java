package com.inn.collab.dao;

import com.inn.collab.POJO.Job_Post;
import com.inn.collab.wrapper.JobWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface JobDao extends JpaRepository<Job_Post, Integer> {

    List<JobWrapper> brandSeePosts(@Param("id") Integer id);
    List<JobWrapper> InfluencerSeePosts();

    Job_Post findRowById(@Param("post_id") Integer post_id);

    @Transactional
    @Modifying
    Integer deleteRowsByBrandId(@Param("id") Integer id);

}
