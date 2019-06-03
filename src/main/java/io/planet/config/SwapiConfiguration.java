package io.planet.config;

import co.swapi.client.SwapiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwapiConfiguration {

    @Value("${SwapiConfiguration.connectionTimeout:10000}")
    private int connectionTimeout = 10000;

    @Value("${SwapiConfiguration.socketTimeout:10000}")
    private int socketTimeout = 10000;

   @Value("${SwapiConfiguration.maxConnections:100}")
    private int maxConnections = 100;

    private CloseableHttpClient createHttpClient() {
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionTimeout)
                .setConnectTimeout(connectionTimeout).setSocketTimeout(socketTimeout)
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(maxConnections);
        connManager.setDefaultMaxPerRoute(maxConnections);
        return HttpClientBuilder.create().setDefaultRequestConfig(config)
                .setConnectionManager(connManager).build();
    }

    @ConditionalOnMissingBean
    public ObjectMapper createObjectMapper(){
        return new ObjectMapper();
    }


    @Bean
    public SwapiClient swapiClient(ObjectMapper objectMapper){
        return new SwapiClient(createHttpClient(),objectMapper);
    }


}
