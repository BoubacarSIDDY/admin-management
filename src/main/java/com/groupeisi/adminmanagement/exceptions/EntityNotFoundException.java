package com.groupeisi.adminmanagement.exceptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * gère les exceptions liées à une entité non trouvée
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityNotFoundException extends RuntimeException {
    String message;
}