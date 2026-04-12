package com.app.url.shortener.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class Urlmapping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	private String originalUrl;
	@Column(unique = true)
	private String shortUrl;
	private LocalDateTime createdTime;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
