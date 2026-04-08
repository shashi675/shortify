package com.app.url.shortener.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "urlclicks")
public class UrlClicks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer clickCount;

    @OneToOne
    @JoinColumn(
        name = "shortUrl",
        referencedColumnName = "shortUrl"
    )
    private Urlmapping urlMapping;
}
