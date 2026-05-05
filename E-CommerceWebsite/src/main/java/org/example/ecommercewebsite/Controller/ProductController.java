package org.example.ecommercewebsite.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Api.ApiResponse;
import org.example.ecommercewebsite.Model.Product;
import org.example.ecommercewebsite.Service.CategoryService;
import org.example.ecommercewebsite.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get-products")
    public ResponseEntity<?> getProducts(){
        ArrayList<Product> products = productService.getProducts();

        if(products.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no products added yet!"));
        }else{
            return ResponseEntity.status(200).body(products);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        return switch (productService.addProduct(product)) {
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("Product was added successfully"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("Another product with the same id exists!"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Category ID does not exist!"));
        };
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        return switch (productService.updateProduct(id, product)){
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("Product was updated successfully"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("Category ID does not exist!"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Product was not found!"));
        };
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        if(productService.deleteProduct(id)){
            return ResponseEntity.status(200).body(new ApiResponse("Product was deleted successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("Product was not found!"));
        }
    }


    @GetMapping("/get-by-category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String categoryId){
        ArrayList<Product> products = productService.getProductsByCategory(categoryId);
        if(products.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no products in that category!"));
        }else{
            return ResponseEntity.status(200).body(products);
        }
    }

    @GetMapping("/get-products/price-sorted")
    public ResponseEntity<?> sortBasedOnPrice(){
        ArrayList<Product> sortedProducts = productService.sortBasedOnPrice();

        if(sortedProducts.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no products added yet!"));
        }else{
            return ResponseEntity.status(200).body(sortedProducts);
        }
    }

    @GetMapping("/get-products/name-sorted")
    public ResponseEntity<?> sortBasedOnName(){
        ArrayList<Product> sortedProducts = productService.sortBasedOnName();

        if(sortedProducts.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no products added yet!"));
        }else{
            return ResponseEntity.status(200).body(sortedProducts);
        }
    }
}
