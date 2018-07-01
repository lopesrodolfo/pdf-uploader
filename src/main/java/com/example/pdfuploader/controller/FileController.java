package com.example.pdfuploader.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.pdfuploader.payload.UploadFileResponse;
import com.example.pdfuploader.service.FileStorageService;
import com.example.pdfuploader.service.PdfUtilsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class FileController {

  @Autowired private FileStorageService fileStorageService;

  @Autowired private HttpServletRequest request;

  @Autowired private PdfUtilsService pdfUtilsService;

  @ApiOperation(value = "Enviar um novo documento", response = UploadFileResponse.class,
      notes = "Essa operação assina o PDF enviado e armazena no servidor.") @ApiResponses(value = {
          @ApiResponse(code = 200, message = "Retorna um UploadFileResponse com HASH gerado e dados do envio",
              response = UploadFileResponse.class),
          @ApiResponse(code = 500,
              message = "Caso tenhamos algum erro vamos retornar um UploadFileResponse com a Exception",
              response = UploadFileResponse.class)

  }) @PostMapping("/uploadFile") public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
      @RequestParam("cpf") String cpf) {

    String hash = pdfUtilsService.generateHashToPdf(cpf, request.getRemoteAddr());

    String fileName = fileStorageService.storeFile(file, hash);

    return new UploadFileResponse(fileName, hash, file.getContentType(), file.getSize());
  }

}
