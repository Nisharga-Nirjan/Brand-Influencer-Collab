package com.inn.collab.dao;

import com.inn.collab.POJO.User_Signup;
import com.inn.collab.wrapper.UserWrapper;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<User_Signup, Integer> {

    User_Signup findByEmailId(@Param("email")String email);

    User_Signup findRowById(@Param("id") Integer id);
    String findRoleById(@Param("id") Integer id);
    List<UserWrapper> getAllUser();

    List<String> getAllAdmin();

    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status,@Param("id") Integer id);

    User_Signup findByEmail(String email);

    List<UserWrapper> SeeAllProfiles(@Param("id") Integer id);

    List<UserWrapper> SeeAllUserProfiles(@Param("id") Integer id);

    @Transactional
    @Modifying
    void deleteRowById(@Param("id") Integer id);
}

