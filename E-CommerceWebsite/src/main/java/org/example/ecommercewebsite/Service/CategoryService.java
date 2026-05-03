package org.example.ecommercewebsite.Service;

import org.example.ecommercewebsite.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {
    ArrayList<Category> categories = new ArrayList<>();

    public Boolean addCategory(Category category){
        for(Category category1: categories){
            if(category1.getId().equalsIgnoreCase(category.getId())){
                return false;
            }
        }
        categories.add(category);
        return true;
    }

    public ArrayList<Category> getCategories(){
        return categories;
    }

    public Boolean updateCategory(String id, Category category){
        for(int i = 0; i < categories.size(); i++){
            if(categories.get(i).getId().equalsIgnoreCase(id)){
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    public Boolean deleteCategory(String id){
        for(Category category: categories){
            if(category.getId().equalsIgnoreCase(id)){
                categories.remove(category);
                return true;
            }
        }
        return false;
    }
}
