package com.project.shopapp.controlers;

import com.project.shopapp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    //Hiện thi tất cả categoey
    @GetMapping("") //http://localhost:8088/api/v1/categories
    public ResponseEntity<String> getAllCategory(@RequestParam("page") int page , @RequestParam("limit") int limit){
        return ResponseEntity.ok(String.format("getAllCategories , page = %d , limit = %d" , page , limit));
    }
    @PostMapping("")
    public ResponseEntity<?> insertCategory(@RequestBody @Valid CategoryDTO categoryDTO , BindingResult result){
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                        .stream().map(FieldError ::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("This is inserCategory" + categoryDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id){
        return ResponseEntity.ok("This is putCategory");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok("This is deleteCategory");
    }


}
