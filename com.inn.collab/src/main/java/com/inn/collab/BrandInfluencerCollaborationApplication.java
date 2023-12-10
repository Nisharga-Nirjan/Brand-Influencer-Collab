package com.inn.collab;

import com.inn.collab.POJO.User_Signup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@SpringBootApplication
public class BrandInfluencerCollaborationApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrandInfluencerCollaborationApplication.class, args);
	}

}


