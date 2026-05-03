package org.example.ecommercewebsite.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Product {
    @NotEmpty(message = "ID must be filled!")
    private String id;

    @NotEmpty(message = "Product Name must be filled!")
    @Size(min = 3, message = "Product must consist of at least three characters!")
    private String name;

    @NotEmpty(message = "Product Price must be filled!")
    private Double price;

    @NotEmpty(message = "Category ID must be filled!")
    @Size(min = 3, message = "")
    private String categoryID;
}
