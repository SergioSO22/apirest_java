// src/main/java/com/ejemplo/crud/config/AwsConfig.java
package com.ejemplo.crud.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${aws.accessKeyId:}")
    private String awsAccessKey;

    @Value("${aws.secretKey:}")
    private String awsSecretKey;

    @Value("${aws.region:us-east-1}")
    private String awsRegion;

    @Bean
    public AWSSecretsManager awsSecretsManager() {
        AWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        
        return AWSSecretsManagerClientBuilder.standard()
                .withRegion(awsRegion)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public String getRdsSecret() {
        try {
            AWSSecretsManager client = awsSecretsManager();
            GetSecretValueRequest request = new GetSecretValueRequest()
                    .withSecretId("rds-credentials");
            
            GetSecretValueResult result = client.getSecretValue(request);
            String secret = result.getSecretString();
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(secret);
            
            return jsonNode.get("password").asText();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo credenciales de Secrets Manager", e);
        }
    }
}
