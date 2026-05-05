package org.example.ecommercewebsite.Model;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "Product ID must be filled!")
    private String id;

    @NotEmpty(message = "Product Name must be filled!")
    @Size(min = 3, message = "Product must consist of at least three characters!")
    private String name;

    @NotNull(message = "Product Price must be filled!")
    private Double price;

    @NotEmpty(message = "Category ID must be filled!")
    @Size(min = 3, message = "")
    private String categoryID;

    @NotNull(message = "is offer must be filled!")
    @AssertFalse
    private Boolean isOffer;
}
