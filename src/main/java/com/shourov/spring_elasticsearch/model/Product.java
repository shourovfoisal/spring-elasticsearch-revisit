package com.shourov.spring_elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@Document(indexName = "products")
public class Product {
    @Id
    private String id;

    @Field(type= FieldType.Text, name = "name")
    private String name;

    @Field(type=FieldType.Text, name = "category")
    private String category;

    @Field(type=FieldType.Double, name = "price")
    private Double price;

    @Field(type=FieldType.Boolean, name = "inStock")
    private boolean inStock;
}