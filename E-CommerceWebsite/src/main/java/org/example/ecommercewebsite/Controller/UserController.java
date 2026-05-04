package org.example.ecommercewebsite.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommercewebsite.Api.ApiResponse;
import org.example.ecommercewebsite.Model.User;
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

}
