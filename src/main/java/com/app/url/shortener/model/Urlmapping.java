package com.app.url.shortener.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Urlmapping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String originalUrl;
	private String shortUrl;
	private int clickCount = 0;
	private LocalDateTime createdTime;
	
//	@ManyToOne
//	@JoinColumn(name = "user_id")
//	private User user;
}
