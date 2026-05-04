package org.example.ecommercewebsite.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Model.Category;
import org.example.ecommercewebsite.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final CategoryService categoryService;
    ArrayList<Category> categories = categoryService.getCategories();

    @Getter
    ArrayList<Product> products = new ArrayList<>();

    public Integer addProduct(Product product){
        for(Product product1: products){
            if(product1.getId().equalsIgnoreCase(product.getId())){
                return 2;
            }
        }
        for(Category category: categories){
            if(product.getCategoryID().equalsIgnoreCase(category.getId())){
                products.add(product);
                return 1;
            }
        }
        return 0;
    }

    public Integer updateProduct(String id, Product product){
        Boolean exist = false;
        for(Category category: categories){
            if(category.getId().equalsIgnoreCase(product.getId())){
                exist = true;
            }
        }
        if(exist) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId().equalsIgnoreCase(id)) {
                    products.set(i, product);
                    return 1;
                }
            }
        }else{
            return 2;
        }
        return 0;
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
