package com.edu.ecommerceproject.repository;

import com.edu.ecommerceproject.dto.request.ProductDTO;
import com.edu.ecommerceproject.models.entities.Product;
import com.edu.ecommerceproject.models.enums.CategoryEnum;
import com.edu.ecommerceproject.models.enums.ProductStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("select new com.edu.ecommerceproject.dto.request.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity)"
    +"from Product p where p.categoryEnum =:categoryEnum")
    List<ProductDTO> findAllProductOfCategory(@Param("categoryEnum")CategoryEnum categoryEnum);

    @Query("select new com.edu.ecommerceproject.dto.request.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity)"
    +"from Product p where p.seller.sellerId=:id")
    List<ProductDTO> findAllProductOfSeller(@Param ("id") Integer id);

    @Query("select new com.edu.ecommerceproject.dto.request.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity)"
    +"from Product p where p.productStatusEnum =:statusEnum")
    List<ProductDTO> findAllProductOfStatus(@Param("statusEnum") ProductStatusEnum productStatusEnum);
}
