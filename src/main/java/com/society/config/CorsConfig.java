package com.society.config;



import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.web.filter.CorsFilter;



@Configuration

public class CorsConfig {



    @Bean

    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();



        config.setAllowCredentials(true);



// ➡️ FIX 1: ADD VERCELL URL HERE ⬅️

        config.addAllowedOrigin("http://localhost:5173");

        config.addAllowedOrigin("https://society-management-frontend-ten.vercel.app");

        config.addAllowedOrigin("https://society-management-backend-qqme.onrender.com");
// We often use allow all origins during final deployment to be safe:

// config.addAllowedOrigin("*"); // Use this if the two lines above don't work.



// ➡️ FIX 2: ADD REQUIRED HEADERS HERE ⬅️

        config.addAllowedHeader("Origin");

        config.addAllowedHeader("Content-Type");

        config.addAllowedHeader("Accept");

        config.addAllowedHeader("Authorization"); // CRITICAL for sending the fake (and later, real) token



// config.addAllowedHeader("*"); // Use this if the lines above don't work.



        config.addAllowedMethod("*");



        source.registerCorsConfiguration("/**", config); // Use "/**" for all paths

        return new CorsFilter(source);

    }

}

