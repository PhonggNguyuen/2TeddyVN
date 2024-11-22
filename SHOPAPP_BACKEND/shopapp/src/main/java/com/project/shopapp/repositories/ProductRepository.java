package com.project.shopapp.repositories;

import com.project.shopapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.*;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {
    boolean existsByName (String name);
    Page<Product> findALl(Pageable pageable);  // phân trang
}
