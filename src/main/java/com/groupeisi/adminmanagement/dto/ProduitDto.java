package com.groupeisi.adminmanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class ProduitDto {
    private int id;
    @NotNull(message = "the name is required")
    private String name;
    @NotNull(message = "the stock quantity is required")
    private double qtyStock;

    private List<ProduitDto> produitList = new ArrayList<ProduitDto>();
}