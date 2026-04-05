package com.app.url.shortener.controller;

import com.app.url.shortener.model.UrlMappingDTO;
import com.app.url.shortener.service.UrlMappingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@Controller
@RequestMapping("/api/url")
@AllArgsConstructor
public class UrlController {

    private UrlMappingService  urlMappingService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlMappingDTO> shorten(@RequestBody Map<String, String> req) {
        UrlMappingDTO urlMappingDTO = urlMappingService.shortenUrl(req.get("originalUrl"));
        return new ResponseEntity<>(urlMappingDTO,HttpStatus.CREATED);
    }


    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortUrl) {
        String originalUrl = urlMappingService.getOriginalUrl(shortUrl);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
