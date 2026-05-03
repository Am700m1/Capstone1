package org.example.ecommercewebsite.Service;

import org.example.ecommercewebsite.Model.MerchantStock;

import java.util.ArrayList;

public class MerchantStockService {
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public Boolean addMerchantStock(MerchantStock merchantStock){
        for(MerchantStock merchantStock1: merchantStocks){
            if(merchantStock1.getId().equalsIgnoreCase(merchantStock.getId())){
                return false;
            }
        }
        merchantStocks.add(merchantStock);
        return true;
    }

    public ArrayList<MerchantStock> getMerchantStocks(){
        return merchantStocks;
    }

    public Boolean updateMerchantStocks(String id, MerchantStock merchantStock){
        for(int i = 0; i < merchantStocks.size(); i++){
            if(merchantStocks.get(i).getId().equalsIgnoreCase(id)){
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    public Boolean deleteMerchantStocks(String id){
        for(MerchantStock merchantStock: merchantStocks){
            if(merchantStock.getId().equalsIgnoreCase(id)){
                merchantStocks.remove(merchantStock);
                return true;
            }
        }
        return false;
    }
}
