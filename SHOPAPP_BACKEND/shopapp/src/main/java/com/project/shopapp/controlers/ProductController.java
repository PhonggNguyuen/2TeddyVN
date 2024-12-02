package com.project.shopapp.controlers;
import com.github.javafaker.Faker;
import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.responses.ProductListResponse;
import com.project.shopapp.responses.ProductResponse;
import com.project.shopapp.services.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    @GetMapping("") //http://localhost:8088/api/v1/products
    public ResponseEntity<ProductListResponse> getProducts(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        // Two Pageable to thong tin trang va gioi han
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);

        // Lay tong so trang
        int totalPages = productPage.getTotalPages();

        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse.builder()
                .products(products)
                .totalPages(totalPages)
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId) {
        try {
            Product existingProduct =  productService.getProductById(productId);
            return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO ,
                                           BindingResult result) {
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream().map(FieldError ::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct = productService.createProduct(productDTO);

            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@PathVariable("id") Long productId,
                                         @RequestParam("files") List<MultipartFile> files) {
        try {
            Product existingProduct = productService.getProductById(productId);
            if (files == null || files.isEmpty()) {
                return ResponseEntity.badRequest().body("No files uploaded");
            }
            if (files.size() > 5) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You can only upload up to 5 images at a time");
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                // kiểm tra kích thước file và định dạng
                if (file.getSize() == 0) {
                    continue;
                }
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // Lưu file và cập nhật trong database
                String fileName = storeFile(file);
                ProductImage productImage = productService.createProductImage(existingProduct.getId(),
                        ProductImageDTO.builder().imageUrl(fileName).build());
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private String storeFile(MultipartFile file) throws IOException {
        // Lấy tên file gốc và làm sạch nó
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        // Đảm bảo file là duy nhất bằng cách tạo UUID
        String uniqueFilename = UUID.randomUUID().toString() + "-" + filename;
        // Kiểm tra nếu file có phải là ảnh
        if (!isImage(file)) {
            throw new IllegalArgumentException("The file is not an image.");
        }
        // Định nghĩa thư mục lưu file
        Path uploadDir = Paths.get("uploads");
        // Tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến nơi lưu file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
    }

    private boolean isImage(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(file.getInputStream());
        return mimeType.startsWith("image/");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(String.format("Product with id = %d deleted successfully " , id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
//    @PostMapping("/generateFakeProducts")
    private ResponseEntity<String> generateFakeProducts(){
        Faker faker = new Faker();
        for(int i = 0 ; i < 1000000 ; i++){
            String productName = faker.commerce().productName();
            if(productService.existsByName(productName)){
                continue;
            }
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price((float) faker.number().numberBetween(10 , 90000000))
                    .thumbnail("")
                    .description(faker.lorem().sentence())
                    .categoryId((long)faker.number().numberBetween(2 , 5))
                    .build();
            try {
                productService.createProduct(productDTO);
            }catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok().body("Faker products create successfully");
    }
    @PutMapping("/{id}")
    public  ResponseEntity<?> updateProduct(@PathVariable("id") Long id , @RequestBody ProductDTO productDTO){
        try {
            Product updateProduct = productService.updateProduct(id , productDTO);
            return ResponseEntity.ok(updateProduct);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
