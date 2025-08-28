package com.edu.ecommerceproject.service.impl;

import com.edu.ecommerceproject.exception.LoginException;
import com.edu.ecommerceproject.exception.ProductNotFoundException;
import com.edu.ecommerceproject.exception.SellerNotFoundException;
import com.edu.ecommerceproject.models.entities.Product;
import com.edu.ecommerceproject.models.entities.Seller;
import com.edu.ecommerceproject.repository.ProductRepository;
import com.edu.ecommerceproject.repository.SellerRepository;
import com.edu.ecommerceproject.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;
    private final AuthServiceImpl authServiceImpl;
    private final SellerRepository sellerRepository;

    @Override
    public List<Product> getAllProducts() {
       return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty())
            throw new ProductNotFoundException("Not Found Product");
        return  product.get();
    }

    @Override
    public Product addProduct(Integer idSeller,String token, Product product) {
        if(!token.contains("seller"))
            throw new LoginException("Just seller can add new product");
        authServiceImpl.checkStatusToken(token);
        Optional<Seller> seller = sellerRepository.findById(idSeller);
        if(seller.isEmpty())
            throw new SellerNotFoundException("Not Found any seller with this ID");
        product.setSeller(seller.get());
        return productRepository.save(product);
    }

    @Override
    public String deleteProduct(Integer id) {
     Optional<Product> productOptional = productRepository.findById(id);
     if(productOptional.isEmpty())
         throw new ProductNotFoundException("not found any product with this id");
        productRepository.delete(productOptional.get());
     return "Product deleted";
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> productOptional = productRepository.findById(product.getProductId());
        if(productOptional.isEmpty())
            throw new ProductNotFoundException("not found any product with this id");
        Product existingProduct = productOptional.get();
        existingProduct.setProductName(product.getProductName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setManufacturer(product.getManufacturer());
        existingProduct.setProductStatusEnum(product.getProductStatusEnum());
        existingProduct.setCategoryEnum(product.getCategoryEnum());
        return productRepository.save(existingProduct);
    }
}
