package com.app.url.shortener.repository;

import com.app.url.shortener.model.Urlmapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlMappingRepository extends JpaRepository<Urlmapping, Long> {
    Optional<Urlmapping> getUrlMappingByShortUrl(String shortUrl);
}
