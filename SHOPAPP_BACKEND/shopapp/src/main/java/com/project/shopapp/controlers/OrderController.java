package com.project.shopapp.controlers;

import com.project.shopapp.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO orderDTO  , BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            return ResponseEntity.ok("order successfully");
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId) {
        try {
            return ResponseEntity.ok("Lấy ra danh sách order tu user_id");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // công việc của admin : cập nhật thông tin một order
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable long id, @Valid @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok("Cap nhat thong tin 1 order");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable long id) {
        // xóa mềm => cập nhật active = false
        return ResponseEntity.ok("xóa thông tin 1 order");
    }



}
