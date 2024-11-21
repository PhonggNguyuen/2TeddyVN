package com.project.shopapp.controlers;


import com.project.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    // thêm mới orderdetail
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@RequestBody @Valid OrderDetailDTO orderDetailDTO  ){
                return ResponseEntity.ok("createOrderDetail here");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long Id) {
        try {
            return ResponseEntity.ok("Lấy chi tiêt đơn hàng với id = : " + Id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Lấy danh danh orderDetail của một order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId) {
        try {
            return ResponseEntity.ok("Lấy chi tiêt đơn hàng với id : " + orderId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO newOrderDetailData) {
        String responseMessage = "updateOrderDetail with id=" + id +
                ", newOrderDetailData=" + newOrderDetailData;
        return ResponseEntity.ok(responseMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(
            @Valid @PathVariable("id") Long id) {
        return ResponseEntity.noContent().build();
    }


}
