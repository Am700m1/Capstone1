package org.example.ecommercewebsite.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Api.ApiResponse;
import org.example.ecommercewebsite.Model.Merchant;
import org.example.ecommercewebsite.Service.MerchantService;
import org.example.ecommercewebsite.Service.MerchantStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;

    @GetMapping("/get-merchants")
    public ResponseEntity<?> getMerchants(){
        ArrayList<Merchant> merchants = merchantService.getMerchants();
        if(merchants.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no merchants added yet!"));
        }else{
            return ResponseEntity.status(200).body(merchants);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(merchantService.addMerchant(merchant)){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant was added successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("Another Merchant with the same ID exists!"));
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        if(merchantService.updateMerchant(id, merchant)){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant was updated successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("Merchant was not found!"));
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id){
        if(merchantService.deleteMerchant(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant was deleted successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("Merchant was not found!"));
        }
    }

    @PutMapping("/add-merchant-stocks/{productId}/{merchantId}/{stock}")
    public ResponseEntity<?> addMerchantStocks(@PathVariable String productId, @PathVariable String merchantId, @PathVariable Integer stock){
        return switch (merchantStockService.addMerchantStocks(productId, merchantId, stock)){
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("Merchant Stocks were added successfully"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("Merchant ID does not exist!"));
            case 3 -> ResponseEntity.status(400).body(new ApiResponse("Product ID does not exist!"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Merchant Stock does not exist!"));
        };
    }

}
