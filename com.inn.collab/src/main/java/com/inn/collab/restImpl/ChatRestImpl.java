package com.inn.collab.restImpl;

import com.inn.collab.rest.ChatRest;

import com.inn.collab.constents.CollabConstents;
import com.inn.collab.service.ChatService;
import com.inn.collab.utils.CollabUtils;
import com.inn.collab.wrapper.ChatWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatRestImpl  implements ChatRest {

    @Autowired
    ChatService chatService;
}
