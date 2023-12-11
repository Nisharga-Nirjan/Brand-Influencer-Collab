package com.inn.collab.POJO;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "chat")
public class Chat  implements Serializable{
    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conv_id")
    private Integer conv_id;

    @Column(name = "sender_id", length = 11)
    private Integer sender_id;

    @Column(name = "receiver_id", length = 11)
    private Integer receiver_id;

    @Column(name = "msg", length = 1000)
    private String msg;

    @Column(name = "msg_date")
    private String msg_date; // Store as String

    public Chat() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
        this.msg_date = now.format(formatter);
    }
}
