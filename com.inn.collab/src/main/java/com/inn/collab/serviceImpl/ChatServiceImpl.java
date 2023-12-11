package com.inn.collab.serviceImpl;

import com.inn.collab.dao.ChatDao;
import com.inn.collab.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ChatServiceImpl  implements ChatService {

    @Autowired
    ChatDao chatDao;


}
