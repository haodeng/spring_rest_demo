package com.hao.demo.swagger;

import io.swagger.annotations.Contact;
import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(
        info = @Info(
                description = "Hao Rest Demo",
                version = "V1.0",
                title = "User demo API",
                contact = @Contact(
                        name = "Hao Deng",
                        email = "example@example.com",
                        url = "http://www.example.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        consumes = {"application/json", "application/xml"},
        produces = {"application/json", "application/xml"},
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        externalDocs = @ExternalDocs(value = "Also read this.", url = "http://example.com")
)
public interface ApiDocumentationConfig {

}