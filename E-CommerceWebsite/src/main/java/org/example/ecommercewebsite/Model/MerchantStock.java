package org.example.ecommercewebsite.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "Merchant Stock ID must be filled!")
    private String id;

    @NotEmpty(message = "Product ID must be filled!")
    private String productId;

    @NotEmpty(message = "Merchant ID must be filled!")
    private String merchantId;

    @NotEmpty(message = "Stock must be filled!")
    @Positive(message = "Stock must be a positive number!")
    @Min(value = 11, message = "Stock must be more than 10 initially!")
    private Integer stock;
}
