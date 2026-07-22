package com.chatop.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration Spring MVC pour exposer les images uploadées via HTTP.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  // Path du répertoire de stockage des images
  @Value("${app.upload.dir}")
  private String uploadDir;

  /**
   * Mappe toute requête vers /images/** vers uploadDir.
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/images/**")
        .addResourceLocations("file:" + uploadDir + "/");
  }
}