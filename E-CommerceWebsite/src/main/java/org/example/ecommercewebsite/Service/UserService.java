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

    @Getter
    ArrayList<Product> wishList = new ArrayList<>();

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
        productService.addToPurchasedList(productId);
        return 1;
    }

    public Integer addProduct(String userId, Product product){
        ArrayList<Product> products = productService.getProducts();
        User user = users.stream().filter(user1 -> user1.getId().equalsIgnoreCase(userId)).findFirst().orElse(null);

        if(user == null){
            return 0;
        }else if(!user.getRole().equalsIgnoreCase("admin")){
            return 2;
        }

        Integer productCheck = productService.addProduct(product);
        if(productCheck == 1){
            return 1;
        }else if(productCheck == 2){
            return 3;
        }else{
            return 4;
        }
    }

    public Integer addOffer(String userId, String productId, Double percentage){
        ArrayList<Product> products = productService.getProducts();
        User user = users.stream().filter(user1 -> user1.getId().equalsIgnoreCase(userId)).findFirst().orElse(null);
        Product product = products.stream().filter(product1 -> product1.getId().equalsIgnoreCase(productId)).findFirst().orElse(null);

        if(user == null) return 0;
        if(product == null) return 3;
        if(percentage >= 1 || percentage <= 0) return 4;
        if(!user.getRole().equalsIgnoreCase("admin")){
            return 2;
        }

        product.setIsOffer(true);
        product.setPrice(product.getPrice() * (1 - percentage));
//        products.get(products.indexOf(product)).setIsOffer(true);
//        products.get(products.indexOf(product)).setPrice(product.getPrice() * (1 - percentage));
        return 1;
    }

    public ArrayList<Product> getDiscountedProducts(){
        ArrayList<Product> products = productService.getProducts();
        ArrayList<Product> discountedProducts = new ArrayList<>();

        for(Product product: products){
            if(product.getIsOffer()){
                discountedProducts.add(product);
            }
        }

        return discountedProducts;
    }

    public Integer addToWishList(String productId){
        ArrayList<Product> products = productService.getProducts();
        for(Product product1: wishList){
            if(product1.getId().equalsIgnoreCase(productId)){
                return 0;
            }
        }

        for(Product product: products){
            if(product.getId().equalsIgnoreCase(productId)){
                wishList.add(product);
                return 1;
            }
        }

        return 2;
    }

    public Integer blockUser(String adminId, String userId){
        User admin = users.stream().filter(user -> user.getId().equalsIgnoreCase(adminId) && user.getRole().equalsIgnoreCase("admin")).findFirst().orElse(null);
        User user = users.stream().filter(user1 -> user1.getId().equalsIgnoreCase(userId)).findFirst().orElse(null);

        if(admin == null) return 2;
        if(user == null) return 0;

        users.remove(user);
        return 1;
    }
}
