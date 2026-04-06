package com.app.url.shortener.service;

import com.app.url.shortener.Exception.UrlMappingNotFoundException;
import com.app.url.shortener.model.UrlMappingDTO;
import com.app.url.shortener.model.Urlmapping;
import com.app.url.shortener.repository.UrlMappingRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
//@AllArgsConstructor
//@NoArgsConstructor
//constructor will not work if @Value is used, since Spring injects @Value after object creation
public class UrlMappingService {

    private String myServerUrl;
    private UrlMappingRepository urlMappingRepository;

    public UrlMappingService(@Value("${myserver.url}") String myServerUrl, UrlMappingRepository urlMappingRepository) {
        this.myServerUrl = myServerUrl;
        this.urlMappingRepository = urlMappingRepository;
    }

    @PostConstruct
    public void init() {
        log.info("myServerUrl {}", myServerUrl);
    }

    public UrlMappingDTO shortenUrl(String originalUrl) {
        String shortUrl = createShortUrl(originalUrl);
        Urlmapping urlmapping = new Urlmapping();
        urlmapping.setOriginalUrl(originalUrl);
        urlmapping.setShortUrl(shortUrl);
        urlmapping.setCreatedTime(LocalDateTime.now());
        Urlmapping savedUrlMapping = urlMappingRepository.save(urlmapping);
        urlmapping.setShortUrl(myServerUrl + "/" +shortUrl);
        UrlMappingDTO urlMappingDTO = toUrlMappingDTO(savedUrlMapping);
        return urlMappingDTO;
    }

    private UrlMappingDTO toUrlMappingDTO(Urlmapping urlMapping) {
        UrlMappingDTO urlMappingDTO = new UrlMappingDTO();
        urlMappingDTO.setOriginalUrl(urlMapping.getOriginalUrl());
        urlMappingDTO.setShortUrl(urlMapping.getShortUrl());
        urlMappingDTO.setCreatedTime(urlMapping.getCreatedTime());
        urlMappingDTO.setId(urlMapping.getId());
        return urlMappingDTO;
    }

    private String createShortUrl(String originalUrl) {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder(6);
        for(int i = 0; i < 6; i++) {
            stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return stringBuilder.toString();
    }

    public String getOriginalUrl(String shortUrl) {
        Urlmapping urlmapping = urlMappingRepository.getUrlMappingByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlMappingNotFoundException("UrlMapping Not Found"));
        urlmapping.setClickCount(urlmapping.getClickCount() + 1);
        urlMappingRepository.save(urlmapping);
        return urlmapping.getOriginalUrl();
    }
}
