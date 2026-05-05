package org.example.ecommercewebsite.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Model.Merchant;
import org.example.ecommercewebsite.Model.MerchantStock;
import org.example.ecommercewebsite.Model.Product;
import org.example.ecommercewebsite.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ProductService productService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;

    @Getter
    ArrayList<User> users = new ArrayList<>();

    public Boolean addUser(User user){
        for(User user1: users){
            if(user1.getId().equalsIgnoreCase(user.getId())){
                return false;
            }
        }
        users.add(user);
        return true;
    }

    public Boolean updateUser(String id, User user){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getId().equalsIgnoreCase(id)){
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public Boolean deleteUser(String id){
        for(User user: users){
            if(user.getId().equalsIgnoreCase(id)){
                users.remove(user);
                return true;
            }
        }
        return false;
    }


    public Integer buyProduct(String userId, String productId, String merchantId){
        ArrayList<Product> products = productService.getProducts();
        ArrayList<Merchant> merchants = merchantService.getMerchants();
        ArrayList<MerchantStock> merchantStocks = merchantStockService.getMerchantStocks();

        Merchant merchant = merchants.stream().filter(merchant1 -> merchant1.getId().equalsIgnoreCase(merchantId)).findFirst().orElse(null);
        Product product = products.stream().filter(product1 -> product1.getId().equalsIgnoreCase(productId)).findFirst().orElse(null);
        User user = users.stream().filter(user1 -> user1.getId().equalsIgnoreCase(userId)).findFirst().orElse(null);
        MerchantStock merchantStock = merchantStocks.stream().filter(merchantStock1 -> merchantStock1.getProductId().equalsIgnoreCase(productId) && merchantStock1.getMerchantId().equalsIgnoreCase(merchantId)).findFirst().orElse(null);


        if (user == null) return 2;
        if (product == null) return 3;
        if (merchant == null) return 4;
        if (merchantStock == null) return 0;
        if (merchantStock.getStock() <= 0) return 6;
        if (user.getBalance() < product.getPrice()) return 5;

        user.setBalance(user.getBalance() - product.getPrice());
        merchantStock.setStock(merchantStock.getStock() - 1);
        return 1;

//        for(MerchantStock merchantStock: merchantStocks){
//            if(merchantStock.getProductId().equalsIgnoreCase(productId) && merchantStock.getMerchantId().equalsIgnoreCase(merchantId)){
//                if(merchantStock.getStock() > 0) {
//                    for(User user: users){
//                        if(user.getId().equalsIgnoreCase(userId)){
//                            userExist = true;
//                            for(Product product: products){
//                                if(product.getId().equalsIgnoreCase(productId)){
//                                    productExist = true;
//                                    if(user.getBalance() >= product.getPrice()){
//                                        user.setBalance(user.getBalance() - product.getPrice());
//                                        merchantStock.setStock(merchantStock.getStock() - 1);
//                                        return 1;
//                                    }else{
//                                        return 5;
//                                    }
//                                }else{
//                                    return 3;
//                                }
//                            }
//                        }else{
//                            return 2;
//                        }
//                    }
//                }else{
//                    return 6;
//                }
//            }else{
//                return 0;
//            }
//        }
    }
}
