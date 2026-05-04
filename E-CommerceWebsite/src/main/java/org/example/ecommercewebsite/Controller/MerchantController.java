package org.example.ecommercewebsite.Controller;

import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Api.ApiResponse;
import org.example.ecommercewebsite.Model.Merchant;
import org.example.ecommercewebsite.Service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    public ResponseEntity<?> getMerchants(){
        ArrayList<Merchant> merchants = merchantService.getMerchants();
        if(merchants.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no merchants added yet!"));
        }else{
            return ResponseEntity.status(200).body(merchants);
        }
    }


    public ResponseEntity<?> addMerchant(Merchant merchant){
        if(merchantService.addMerchant(merchant)){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant was added successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("Another Merchant with the same ID exists!"));
        }
    }


    public ResponseEntity<?> updateMerchant(String id, Merchant merchant){
        if(merchantService.updateMerchant(id, merchant)){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant was updated successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("Merchant was not found!"));
        }
    }

    public ResponseEntity<?> deleteMerchant(String id){
        if(merchantService.deleteMerchant(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant was deleted successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("Merchant was not found!"));
        }
    }

}
