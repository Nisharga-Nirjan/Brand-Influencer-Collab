package com.inn.collab.dao;
import com.inn.collab.POJO.Chat;
import com.inn.collab.POJO.Job_Post;
import com.inn.collab.wrapper.ChatWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
public interface ChatDao  extends JpaRepository<Chat, Integer>{

}
