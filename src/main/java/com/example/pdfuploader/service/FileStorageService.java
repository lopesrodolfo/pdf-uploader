package com.example.pdfuploader.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.pdfuploader.exception.FileStorageException;
import com.example.pdfuploader.property.FileStorageProperties;

@Service
public class FileStorageService {

  private final Path fileStorageLocation;

  @Autowired public FileStorageService(FileStorageProperties fileStorageProperties) {
    this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

    try {
      Files.createDirectories(this.fileStorageLocation);
    } catch (Exception ex) {
      throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
    }
  }

  public String storeFile(MultipartFile file, String hash) {

    // Normalize file name
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    try {

      // Check if the file's name contains invalid characters
      if (fileName.contains("..")) {
        throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
      }

      // Copy file to the target location (Replacing existing file with the same name)
      Path targetLocation = this.fileStorageLocation.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

      // Print hash into pdf file
      printMessageInFile(fileName, hash);

      return fileName;
    } catch (IOException ex) {
      throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
    }
  }

  private Void printMessageInFile(String fileName, String hash) {
    try {

      Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

      File file = new File(filePath.toString());
      PDDocument document = PDDocument.load(file);

      int numberOfPages = document.getNumberOfPages();

      for (int i = 0; i < numberOfPages; i++) {
        // Retrieving the pages of the document
        PDPage page = document.getPage(i);

        PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, false, true);

        // Begin the Content stream
        contentStream.beginText();

        // Setting the font to the Content stream
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

        // Setting the position for the line
        contentStream.newLineAtOffset(25, 25);

        // Adding text in the form of string
        contentStream.showText(hash);

        // Ending the content stream
        contentStream.endText();

        // Closing the content stream
        contentStream.close();
      }

      document.save(file);

      // Closing the document
      document.close();

    } catch (InvalidPasswordException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
