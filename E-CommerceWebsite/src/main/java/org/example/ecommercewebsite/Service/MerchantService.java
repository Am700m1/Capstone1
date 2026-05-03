package org.example.ecommercewebsite.Service;

import org.example.ecommercewebsite.Model.Merchant;

import java.util.ArrayList;

public class MerchantService {
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

    public ArrayList<Merchant> getMerchants(){
        return merchants;
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
