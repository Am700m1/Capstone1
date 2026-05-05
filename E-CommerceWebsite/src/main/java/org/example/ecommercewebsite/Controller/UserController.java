package org.example.ecommercewebsite.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Api.ApiResponse;
import org.example.ecommercewebsite.Model.Product;
import org.example.ecommercewebsite.Model.User;
import org.example.ecommercewebsite.Service.ProductService;
import org.example.ecommercewebsite.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-users")
    public ResponseEntity<?> getUsers(){
        ArrayList<User> users = userService.getUsers();

        if(users.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no users added yet!"));
        }else{
            return ResponseEntity.status(200).body(users);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(userService.addUser(user)){
            return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("Another product with the same id exists!"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        if(userService.updateUser(id, user)){
            return ResponseEntity.status(200).body(new ApiResponse("User was updated successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("User was not found!"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        if(userService.deleteUser(id)){
            return ResponseEntity.status(200).body(new ApiResponse("User was deleted successfully"));
        }else{
            return ResponseEntity.status(400).body(new ApiResponse("User was not found!"));
        }
    }


    @PutMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId,@PathVariable String productId,@PathVariable String merchantId){
        return switch (userService.buyProduct(userId, productId, merchantId)){
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("Product was purchased successfully"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("User was not found!"));
            case 3 -> ResponseEntity.status(400).body(new ApiResponse("Product was not found!"));
            case 4 -> ResponseEntity.status(400).body(new ApiResponse("Merchant was not found!"));
            case 5 -> ResponseEntity.status(400).body(new ApiResponse("User balance is not enough!"));
            case 6 -> ResponseEntity.status(400).body(new ApiResponse("Out of stock"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Merchant stock was not found!"));
        };
    }

    @PostMapping("/add-product/{userId}")
    public ResponseEntity<?> addProduct(@PathVariable String userId,@RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        return switch (userService.addProduct(userId, product)){
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("Product was added successfully"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("You do not have the authority to do this action!"));
            case 3 -> ResponseEntity.status(400).body(new ApiResponse("Another product with the same id exists!"));
            case 4 -> ResponseEntity.status(400).body(new ApiResponse("Category ID does not exist!"));
            default -> ResponseEntity.status(400).body(new ApiResponse("User was not found!"));
        };
    }

}
