package org.example.ecommercewebsite.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Api.ApiResponse;
import org.example.ecommercewebsite.Model.Category;
import org.example.ecommercewebsite.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/get-categories")
    public ResponseEntity<?> getCategories(){
        ArrayList<Category> categories = categoryService.getCategories();

        if(categories.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no categories added yet!"));
        }else{
            return ResponseEntity.status(200).body(categories);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        if(categoryService.addCategory(category)){
            return ResponseEntity.status(200).body(new ApiResponse("Category was added successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("Another category with the same ID exists!"));
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable @Valid String id, @RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        if(categoryService.addCategory(category)){
            return ResponseEntity.status(200).body(new ApiResponse("Category was updated successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("Category was not found!"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        if(categoryService.deleteCategory(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Category was deleted successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("Category was not found!"));
        }
    }

}
