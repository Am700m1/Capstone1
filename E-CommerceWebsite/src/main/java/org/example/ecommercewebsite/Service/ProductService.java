package org.example.ecommercewebsite.Service;

import org.example.ecommercewebsite.Model.Product;

import java.util.ArrayList;

public class ProductService {
    ArrayList<Product> products = new ArrayList<>();

    public Boolean addProduct(Product product){
        for(Product product1: products){
            if(product1.getId().equalsIgnoreCase(product.getId())){
                return false;
            }
        }
        products.add(product);
        return true;
    }

    public ArrayList<Product> getProducts(){
        return products;
    }

    public Boolean updateProduct(String id, Product product){
        for(int i = 0; i< products.size(); i++){
            if(products.get(i).getId().equalsIgnoreCase(id)){
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    public Boolean deleteProduct(String id){
        for(Product product: products){
            if(product.getId().equalsIgnoreCase(id)){
                products.remove(product);
                return true;
            }
        }
        return false;
    }

}
