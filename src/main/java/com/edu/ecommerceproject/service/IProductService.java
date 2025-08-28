package com.edu.ecommerceproject.service;

import com.edu.ecommerceproject.models.entities.Product;
import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getProductById(Integer id);
    Product addProduct(Integer idSeller,String token,Product product);
    String deleteProduct(Integer id);
    Product updateProduct(Product product);
}
