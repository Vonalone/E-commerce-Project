package com.edu.ecommerceproject.service;

import com.edu.ecommerceproject.dto.request.ProductDTO;
import com.edu.ecommerceproject.models.entities.Product;
import com.edu.ecommerceproject.models.enums.CategoryEnum;
import com.edu.ecommerceproject.models.enums.ProductStatusEnum;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getProductById(Integer id);
    Product addProduct(Integer idSeller,String token,Product product);
    String deleteProduct(Integer id);
    Product updateProduct(Product product);
    List<ProductDTO> getAllProductOfSeller(Integer id);
    List<ProductDTO> getAllProductsOfCategory(CategoryEnum categoryEnum);
    List<ProductDTO> getAllProductsOfStatus(ProductStatusEnum statusEnum);
    Product updateProductQuantityById(Integer id,ProductDTO productDTO);
}
