package com.app.url.shortener.service;

import com.app.url.shortener.Exception.UrlMappingNotFoundException;
import com.app.url.shortener.model.UrlClicks;
import com.app.url.shortener.model.UrlMappingDTO;
import com.app.url.shortener.model.Urlmapping;
import com.app.url.shortener.model.User;
import com.app.url.shortener.repository.UrlClicksRepository;
import com.app.url.shortener.repository.UrlMappingRepository;
import com.app.url.shortener.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
//@NoArgsConstructor
//constructor will not work if @Value is used, since Spring injects @Value after object creation
//so used @Value in the constructor
public class UrlMappingService {

//    private String myServerUrl;
    private UrlMappingRepository urlMappingRepository;
    private UrlClicksRepository urlClicksRepository;
    private UserRepository userRepository;

//    public UrlMappingService(@Value("${myserver.url}") String myServerUrl, UrlMappingRepository urlMappingRepository,  UrlClicksRepository urlClicksRepository) {
//        this.myServerUrl = myServerUrl;
//        this.urlMappingRepository = urlMappingRepository;
//        this.urlClicksRepository = urlClicksRepository;
//    }

    public UrlMappingDTO shortenUrl(String originalUrl) {
        String shortUrl = createShortUrl(originalUrl);
        Urlmapping urlmapping = new Urlmapping();
        urlmapping.setOriginalUrl(originalUrl);
        urlmapping.setShortUrl(shortUrl);
        urlmapping.setCreatedTime(LocalDateTime.now());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        urlmapping.setUser(user);
        Urlmapping savedUrlMapping = urlMappingRepository.save(urlmapping);
//        urlmapping.setShortUrl(myServerUrl + "/" +shortUrl);
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
        urlMappingRepository.save(urlmapping);

        Optional<UrlClicks> urlClicksOptional = urlClicksRepository.getByUrlMapping_ShortUrl(shortUrl);
        if(urlClicksOptional.isPresent()) {
            UrlClicks urlClicks = urlClicksOptional.get();
            urlClicks.setClickCount(urlClicks.getClickCount() + 1);
            urlClicksRepository.save(urlClicks);
        }
        else {
            UrlClicks urlClicks = new UrlClicks();
            urlClicks.setUrlMapping(urlmapping);
            urlClicks.setClickCount(1);
            urlClicksRepository.save(urlClicks);
        }
        return urlmapping.getOriginalUrl();
    }
}
