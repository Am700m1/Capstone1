package org.example.ecommercewebsite.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Model.Category;
import org.example.ecommercewebsite.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final CategoryService categoryService;

    @Getter
    ArrayList<Product> products = new ArrayList<>();


    public Integer addProduct(Product product){
        ArrayList<Category> categories = categoryService.getCategories();
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
        ArrayList<Category> categories = categoryService.getCategories();
        Boolean exist = false;
        for(Category category: categories){
            if(category.getId().equalsIgnoreCase(product.getCategoryID())){
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

    public ArrayList<Product> getProductsByCategory(String categoryId){
        ArrayList<Product> sameCategoryProducts = new ArrayList<>();

        for(Product product: products){
            if(product.getCategoryID().equalsIgnoreCase(categoryId)){
                sameCategoryProducts.add(product);
            }
        }
        
        return sameCategoryProducts;
    }

    public ArrayList<Product> sortBasedOnPrice(){
        ArrayList<Product> sortedBasedOnPrice = products;
        sortedBasedOnPrice.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if (o1.getPrice() == o2.getPrice()) {
                    return 0;
                }
                return o1.getPrice() < o2.getPrice() ? -1 : 1;
            }
        });

        return sortedBasedOnPrice;
    }


    public ArrayList<Product> sortBasedOnName(){
        ArrayList<Product> sortedBasedOnName = products;
        sortedBasedOnName.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return sortedBasedOnName;
    }

}
