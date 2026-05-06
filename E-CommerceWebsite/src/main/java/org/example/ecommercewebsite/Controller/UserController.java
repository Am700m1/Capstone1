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

    @PutMapping("/put-offer/{userId}/{productId}/{percentage}")
    public ResponseEntity<?> addOffer(@PathVariable String userId,@PathVariable String productId,@PathVariable Integer percentage){
        return switch (userService.addOffer(userId, productId, percentage)){
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("Offer was added successfully"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("You are not authorized to do this action!"));
            case 3 -> ResponseEntity.status(400).body(new ApiResponse("Product was not found!"));
            case 4 -> ResponseEntity.status(400).body(new ApiResponse("Percentage is more than 1 or less than 0!"));
            default -> ResponseEntity.status(400).body(new ApiResponse("User was not found!"));
        };
    }

    @GetMapping("/get-offers")
    public ResponseEntity<?> getDiscountedProducts(){
        ArrayList<Product> discountedProducts = userService.getDiscountedProducts();
        if(discountedProducts.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no discounted products yet!"));
        }else{
            return ResponseEntity.status(200).body(discountedProducts);
        }
    }

    @PostMapping("/add-to-wishlist/{userId}/{productId}")
    public ResponseEntity<?> addToWishList(@PathVariable String userId, @PathVariable String productId){
        return switch (userService.addToWishList(userId, productId)){
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("Product was added to the wishlist successfully"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("Product was not found!"));
            case 3 -> ResponseEntity.status(400).body(new ApiResponse("User was not found!"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Product is already added to wishlist!"));
        };
    }

    @GetMapping("/get-wishlist/{userId}")
    public ResponseEntity<?> getWishList(@PathVariable String userId){
        ArrayList<Product> wishlist = userService.getWishlist(userId);

        if(wishlist.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There is no products inside the wishlist!"));
        }else{
            return ResponseEntity.status(200).body(wishlist);
        }
    }

    @DeleteMapping("/block/{adminId}/{userId}")
    public ResponseEntity<?> blockUser(@PathVariable String adminId,@PathVariable String userId){
        return switch (userService.blockUser(adminId, userId)){
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("User was Blocked/removed successfully"));
            case 2 -> ResponseEntity.status(400).body(new ApiResponse("You are not authorized to do this action!"));
            default -> ResponseEntity.status(400).body(new ApiResponse("User was not found!"));
        };
    }

}
