package com.edu.ecommerceproject.controller;


import com.edu.ecommerceproject.dto.request.ProductDTO;
import com.edu.ecommerceproject.models.entities.Product;
import com.edu.ecommerceproject.models.enums.CategoryEnum;
import com.edu.ecommerceproject.models.enums.ProductStatusEnum;
import com.edu.ecommerceproject.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id){
        return new ResponseEntity<>(productService.getProductById(id),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestParam Integer idSeller,@RequestHeader String token,@Valid @RequestBody Product product){
        return new ResponseEntity<>(productService.addProduct(idSeller, token, product),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        return new ResponseEntity<>(productService.deleteProduct(id),HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product){
        return new ResponseEntity<>(productService.updateProduct(product),HttpStatus.OK);
    }
    @GetMapping("/category/{categoryEnum}")
    public ResponseEntity<List<ProductDTO>> getAllProductOfCategory(@PathVariable CategoryEnum categoryEnum){
        return new ResponseEntity<>(productService.getAllProductsOfCategory(categoryEnum),HttpStatus.OK);
    }
    @GetMapping("/seller/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductOfSeller(@PathVariable Integer id){
        return new ResponseEntity<>(productService.getAllProductOfSeller(id),HttpStatus.OK);
    }
    @GetMapping("/status/{productStatusEnum}")
    public ResponseEntity<List<ProductDTO>> getAllProductOfStatus(@PathVariable ProductStatusEnum productStatusEnum){
        return new ResponseEntity<>(productService.getAllProductsOfStatus(productStatusEnum),HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProductQuantity(@PathVariable Integer id,@RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.updateProductQuantityById(id,productDTO),HttpStatus.OK);
    }


}
