package com.example.commonProject.service;

import com.example.commonProject.dto.ProductDto;
import com.example.commonProject.entity.Product;
import com.example.commonProject.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final String uploadDir = "uploads/";

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductDto productDto, MultipartFile multipartFile) throws IOException {
        String urlImg = saveFile(multipartFile);
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(urlImg);
        return productRepository.save(product);
    }

    public String saveFile(MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String newFileName =uploadPath.getFileName() + "_";

        Files.copy(multipartFile.getInputStream(), uploadPath.resolve(newFileName));

        return  uploadPath.resolve(newFileName).toString();
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
