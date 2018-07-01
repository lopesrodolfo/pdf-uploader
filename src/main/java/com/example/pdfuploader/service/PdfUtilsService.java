package com.example.pdfuploader.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.pdfuploader.exception.FileStorageException;

@Service
public class PdfUtilsService {

  public String generateHashToPdf(String cpf, String remoteAddr) {

    StringBuilder key = new StringBuilder();
    key.append(cpf);
    key.append(new Date().toString());
    key.append(remoteAddr);

    String keyString = key.toString().replaceAll("[^0-9]", "");

    try {
      MessageDigest algorithm;

      algorithm = MessageDigest.getInstance("SHA-256");

      byte messageDigest[] = algorithm.digest(keyString.getBytes("UTF-8"));

      StringBuilder hexStringSenhaAdmin = new StringBuilder();
      for (byte b : messageDigest) {
        hexStringSenhaAdmin.append(String.format("%02X", 0xFF & b));
      }
      String senhahexAdmin = hexStringSenhaAdmin.toString();

      return senhahexAdmin;

    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {

      throw new FileStorageException("Could not generate Hash");
    }

  }

}
