package org.example.ecommercewebsite.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Merchant {
    @NotEmpty(message = "ID must be filled!")
    private String id;

    @NotEmpty(message = "Merchant name must be filled!")
    @Size(min = 3, message = "Merchant name must consist of at least three characters!")
    private String name;
}
