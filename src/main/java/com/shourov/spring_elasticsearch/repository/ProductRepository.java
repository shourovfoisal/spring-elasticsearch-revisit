package com.shourov.spring_elasticsearch.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.shourov.spring_elasticsearch.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final ElasticsearchClient elasticsearchClient;

    public String createOrUpdateDocument(Product product) throws IOException {
        IndexResponse response = elasticsearchClient.index(
                i -> i
                .index("products")
                .id(product.getId())
                .document(product)
        );

        Map<String, String> responseMessages = Map.of("Created", "Document has been created.",
                "Updated", "Document has been updated."
        );

        return responseMessages.getOrDefault(response.result().name(), "Error has occurred.");
    }

}
