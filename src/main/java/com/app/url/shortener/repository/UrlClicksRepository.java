package com.app.url.shortener.repository;

import com.app.url.shortener.model.UrlClicks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlClicksRepository extends JpaRepository<UrlClicks, Long> {

    Optional<UrlClicks> getByUrlMapping_ShortUrl(String shortUrl);
}
