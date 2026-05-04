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
public class MerchantStockService {
    private final ProductService productService;
    private final MerchantService merchantService;



    @Getter
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public Integer addMerchantStock(MerchantStock merchantStock){
        ArrayList<Product> products = productService.getProducts();
        ArrayList<Merchant> merchants = merchantService.getMerchants();

        boolean merchantExist = false;
        for(MerchantStock merchantStock1: merchantStocks){
            if(merchantStock1.getId().equalsIgnoreCase(merchantStock.getId())){
                return 2;
            }
        }
        for(Merchant merchant: merchants){
            if(merchant.getId().equalsIgnoreCase(merchantStock.getMerchantId())){
                merchantExist = true;
                break;
            }
        }

        if(!merchantExist) {
            return 3;
        }else{
            for(Product product: products){
                if(product.getId().equalsIgnoreCase(merchantStock.getProductId())){
                    merchantStocks.add(merchantStock);
                    return 1;
                }
            }
            return 4;
        }
    }

    public Integer updateMerchantStock(String id, MerchantStock merchantStock){
        ArrayList<Product> products = productService.getProducts();
        ArrayList<Merchant> merchants = merchantService.getMerchants();

        boolean merchantExist = false;
        boolean productExist = false;

        for(Merchant merchant: merchants){
            if(merchant.getId().equalsIgnoreCase(merchantStock.getMerchantId())){
                merchantExist = true;
                break;
            }
        }

        if(!merchantExist){
            return 3;
        }else {
            for (Product product : products) {
                if (product.getId().equalsIgnoreCase(merchantStock.getProductId())) {
                    productExist = true;
                    break;
                }
            }
        }

        if(!productExist){
            return 4;
        }else {
            for (int i = 0; i < merchantStocks.size(); i++) {
                if (merchantStocks.get(i).getId().equalsIgnoreCase(id)) {
                    merchantStocks.set(i, merchantStock);
                    return 1;
                }
            }
        }
        return 0;
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

    public Integer addMerchantStocks(String productID, String merchantID, Integer stock){
        ArrayList<Product> products = productService.getProducts();
        ArrayList<Merchant> merchants = merchantService.getMerchants();

        boolean productExist = false;
        boolean merchantExist = false;
        for(Merchant merchant: merchants){
            if(merchant.getId().equalsIgnoreCase(merchantID)){
                merchantExist = true;
                break;
            }
        }

        if(!merchantExist){
            return 2;
        }

        for(Product product: products){
            if(product.getId().equalsIgnoreCase(productID)){
                productExist = true;
                break;
            }
        }

        if(!productExist){
            return 3;
        }

        for(MerchantStock merchantStock: merchantStocks){
            if(merchantStock.getMerchantId().equalsIgnoreCase(merchantID) && merchantStock.getProductId().equalsIgnoreCase(productID)){
                merchantStock.setStock(merchantStock.getStock() + stock);
                return 1;
            }
        }

        return 0;
    }
}
