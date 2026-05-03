package org.example.ecommercewebsite.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "Category ID must be filled!")
    private String id;

    @NotEmpty(message = "Category name must be filled!")
    @Size(min = 3, message = "Category name must consist of at least three characters!")
    private String name;
}
