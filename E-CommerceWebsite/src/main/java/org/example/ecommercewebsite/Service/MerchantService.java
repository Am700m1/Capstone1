package org.example.ecommercewebsite.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Model.Merchant;
import org.example.ecommercewebsite.Model.MerchantStock;
import org.example.ecommercewebsite.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final ProductService productService;

    @Getter
    ArrayList<Merchant> merchants = new ArrayList<>();


    public Boolean addMerchant(Merchant merchant){
        for(Merchant merchant1: merchants){
            if(merchant1.getId().equalsIgnoreCase(merchant.getId())){
                return false;
            }
        }
        merchants.add(merchant);
        return true;
    }

    public Boolean updateMerchant(String id, Merchant merchant){
        for(int i = 0; i < merchants.size(); i++){
            if(merchants.get(i).getId().equalsIgnoreCase(id)){
                merchants.set(i, merchant);
                return true;
            }
        }
        return false;
    }


    public Boolean deleteMerchant(String id){
        for(Merchant merchant: merchants){
            if(merchant.getId().equalsIgnoreCase(id)){
                merchants.remove(merchant);
                return true;
            }
        }
        return false;
    }




}
