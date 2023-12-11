package com.inn.collab.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatWrapper {
    private Integer conv_id;

    private Integer sender_id;

    private Integer receiver_id;

    private String msg;

    private String msg_date;

    public ChatWrapper(Integer conv_id, Integer sender_id, Integer receiver_id, String msg, String msg_date) {
        this.conv_id = conv_id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.msg = msg;
        this.msg_date = msg_date;
    }
}
