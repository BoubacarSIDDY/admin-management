package com.groupeisi.adminmanagement.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * gère toutes les exceptions liées à des réquêtes faites par les clients
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestException extends RuntimeException { // RuntimeException est la classe de base qui gère toutes les exceptions dans Spring Boot
    private String message;
    private HttpStatus status;
}