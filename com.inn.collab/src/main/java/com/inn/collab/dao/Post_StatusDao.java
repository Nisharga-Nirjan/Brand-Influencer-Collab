package com.inn.collab.dao;

import com.inn.collab.POJO.Post_Status;
import com.inn.collab.wrapper.Post_StatusWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface Post_StatusDao extends JpaRepository<Post_Status, Integer>{
    Integer HireRecordExist(@Param("id") Integer id, @Param("post_id") Integer post_id);

    List<Post_StatusWrapper> brandSeePostOffers(@Param("post_id") Integer post_id);

    List<Post_StatusWrapper> influencerHireStatus(@Param("id") Integer id);

    Post_Status findRowById(@Param("id") Integer id);

    Post_StatusWrapper getPaymentInfo(@Param("id") Integer id); //post status id

    @Transactional
    @Modifying
    void deleteRowsByBrandId(@Param("id") Integer id);

    @Transactional
    @Modifying
    void deleteRowsByInfluencerId(@Param("id") Integer id);

}
