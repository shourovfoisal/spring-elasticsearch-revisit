package com.shourov.spring_elasticsearch.controller;

import com.shourov.spring_elasticsearch.model.Product;
import com.shourov.spring_elasticsearch.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository elasticSearchQuery;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() throws IOException {
        List<Product> products = elasticSearchQuery.findAll();
        log.info("No of Product Documents has been successfully retrieved: {}", products.size());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getDocumentById( @PathVariable String productId) throws IOException {
        Product product =elasticSearchQuery.findDocumentById(productId);
        log.info("Product Document has been successfully retrieved.");
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdateDocument( @RequestBody Product product) throws IOException {
        String response = elasticSearchQuery.createOrUpdateDocument(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> bulk( @RequestBody List<Product> product) throws IOException {
        String response = elasticSearchQuery.bulkSave(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteDocumentById( @PathVariable String productId) throws IOException {
        String message = elasticSearchQuery.deleteDocumentById(productId);
        log.info("Product Document has been successfully deleted. Message: {}", message);
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }
}