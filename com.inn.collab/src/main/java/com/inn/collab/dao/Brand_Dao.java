package com.inn.collab.dao;

import com.inn.collab.POJO.User_Signup;
import com.inn.collab.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface Brand_Dao extends JpaRepository<User_Signup, Integer> {

    UserWrapper Profile(@Param("id") Integer id);
}
