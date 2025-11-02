package com.modaexpress.modaexpress_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.modaexpress.repository")
public class MongoConfig {
    // Configuración por propiedades en application-dev.yml es suficiente para el MVP.
}
