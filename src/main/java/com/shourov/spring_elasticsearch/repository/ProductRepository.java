package com.shourov.spring_elasticsearch.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import com.shourov.spring_elasticsearch.constant.AppConstants;
import com.shourov.spring_elasticsearch.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final ElasticsearchClient elasticsearchClient;

    public String createOrUpdateDocument(Product product) throws IOException {
        IndexResponse response = elasticsearchClient.index(
                i -> i
                        .index(AppConstants.PRODUCT_INDEX_NAME)
                        .id(product.getId())
                        .document(product)
        );

        Map<String, String> responseMessages = Map.of(
                "Created", "Document has been created.",
                "Updated", "Document has been updated."
        );

        return responseMessages.getOrDefault(response.result().name(), "Error has occurred.");
    }

    public Product findDocumentById(String productId) throws IOException {
        return elasticsearchClient.get(req -> req.index(AppConstants.PRODUCT_INDEX_NAME).id(productId), Product.class).source();
    }

    public String deleteDocumentById(String productId) throws IOException {
        DeleteResponse response = elasticsearchClient.delete(req -> req.index(AppConstants.PRODUCT_INDEX_NAME).id(productId));
        return response.result().name().equalsIgnoreCase("NOT_FOUND") ? "Document not found with id " + productId : "Successfully deleted";
    }

    public List<Product> findAll() throws IOException {
        SearchRequest searchRequest = SearchRequest.of(req -> req.index(AppConstants.PRODUCT_INDEX_NAME));

        SearchResponse<Product> response = elasticsearchClient.search(searchRequest, Product.class);

        List<Product> productList = new ArrayList<>();
        response.hits().hits().forEach(hit -> {
            productList.add(hit.source());
        });
        return productList;
    }

    public String bulkSave(List<Product> productList) throws IOException {
        BulkRequest.Builder br = new BulkRequest.Builder();

        productList.forEach(product -> {
            br.operations(operation -> operation.index(i -> i.index(AppConstants.PRODUCT_INDEX_NAME).id(product.getId()).document(product)));
        });

        BulkResponse response = elasticsearchClient.bulk(br.build());

        if(response.errors()) {
            return "Error on bulk save";
        } else {
            return "Successfully saved bulk";
        }
    }
}
