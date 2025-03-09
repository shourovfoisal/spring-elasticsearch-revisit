package com.shourov.spring_elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfiguration {

    @Bean
    public RestClient getRestClient() {
        return RestClient
                .builder(new HttpHost("localhost", 9200))
//                https://github.com/elastic/elasticsearch-py/issues/1933#issuecomment-1584354377
                .setDefaultHeaders(new BasicHeader[]{
                        new BasicHeader("Content-Type", "application/json")
                })
                .build();
    }

    @Bean
    public ElasticsearchTransport getElasticSearchTransport() {
        return new RestClientTransport(getRestClient(), new JacksonJsonpMapper());
    }

    @Bean
    public ElasticsearchClient getElasticsearchClient() {
        return new ElasticsearchClient(getElasticSearchTransport());
    }

}
