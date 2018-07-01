package com.example.pdfuploader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.pdfuploader.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class PdfUploaderApplication {

  public static void main(String[] args) {
    SpringApplication.run(PdfUploaderApplication.class, args);
  }
}
