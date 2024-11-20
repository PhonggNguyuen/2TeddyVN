package com.project.shopapp.controlers;
import com.project.shopapp.dtos.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO  , BindingResult result){
      try{
          if(result.hasErrors()){
              List<String> errorMessages = result.getFieldErrors()
                      .stream().map(FieldError::getDefaultMessage).toList();
              return ResponseEntity.badRequest().body(errorMessages);
          }
          if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
              return ResponseEntity.badRequest().body("Password does not math");
          }
         return ResponseEntity.ok("register successfully");
      }catch (Exception e)
      {
          return ResponseEntity.badRequest().body(e.getMessage());
      }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO userLoginDTO ){
        return ResponseEntity.ok("some Token");
    }
}
