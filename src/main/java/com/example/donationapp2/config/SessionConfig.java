package com.example.donationapp2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class SessionConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("DONATION_SESSION");
        serializer.setCookieMaxAge(3600); // 1 hour session timeout
        serializer.setCookiePath("/");
        return serializer;
    }
}