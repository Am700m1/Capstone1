package org.example.ecommercewebsite.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Api.ApiResponse;
import org.example.ecommercewebsite.Model.MerchantStock;
import org.example.ecommercewebsite.Service.MerchantStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant-stock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;


    public ResponseEntity<?> getMerchantStocks(){
        ArrayList<MerchantStock> merchantStocks = merchantStockService.getMerchantStocks();

        if(merchantStocks.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no merchant stocks added yet!"));
        }else{
            return ResponseEntity.status(200).body(merchantStocks);
        }
    }

    public ResponseEntity<?> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock){
        return switch (merchantStockService.addMerchantStock(merchantStock)){
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("Merchant stock was added successfully"));
            case 2-> ResponseEntity.status(400).body(new ApiResponse("Another Merchant Stock with the same ID exists!"));
            case 3 -> ResponseEntity.status(400).body(new ApiResponse("Merchant ID does not exist!"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Product ID does not exist!"));
        };
    }


    public ResponseEntity<?> updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock){
        return switch (merchantStockService.updateMerchantStock(id, merchantStock)) {
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("Merchant stock was updated successfully"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("Another Merchant Stock with the same ID exists!"));
            case 3 -> ResponseEntity.status(400).body(new ApiResponse("Merchant ID does not exist!"));
            case 4 -> ResponseEntity.status(400).body(new ApiResponse("Product ID does not exist!"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Merchant Stock was not found!"));
        };
    }


    public ResponseEntity<?> deleteMerchantStock(String id){
        if(merchantStockService.deleteMerchantStocks(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock was deleted successfully!"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("Merchant stock was not found!"));
        }
    }


}
