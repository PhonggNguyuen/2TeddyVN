package com.project.shopapp.controlers;
import com.project.shopapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @GetMapping("") //http://localhost:8088/api/v1/products
    public ResponseEntity<String> getProducts(@RequestParam("page") int page , @RequestParam("limit") int limit){
        return ResponseEntity.ok("get product here");
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") String productId) {
        return ResponseEntity.ok("Product with id: " + productId);
    }
    @PostMapping(value = "" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductDTO productDTO ,
//                                           @RequestPart("file") MultipartFile file ,
                                           BindingResult result) {
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream().map(FieldError ::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            List<MultipartFile> files = productDTO.getFiles();
            files = files == null ? new ArrayList<MultipartFile>() : files;
            for(MultipartFile file : files){
                // kiểm tra kích thước file và định dạng
                if(file.getSize() == 0){
                    continue;
                }
                    if(file.getSize() > 10 * 1024 * 1024){
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("file is too large ! Maximum size is 10MB");
                    }
                    String contentType = file.getContentType();
                    if(contentType == null || !contentType.startsWith("image/") ){
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                    }
                    // Lưu file và cập nhật thumnail trong DTO
                    String fileName = storeFile(file);
                    // Lưu vào đối tượng trong DB (product_images)

            }
            return ResponseEntity.ok("Product created successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        // Đảm bảo file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "-" + filename;

        java.nio.file.Path uploadDir = Paths.get("uploads");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // đường dẫn đầy đủ đến file
        java.nio.file.Path destination  = Paths.get(uploadDir.toString() , uniqueFilename);
        // Sao chép file vào mục đích
        Files.copy(file.getInputStream() , destination , StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        return ResponseEntity.ok("product with id = " + id + " deleted successfully ");
    }

}
