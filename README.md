PDF Uploader
================================

O projeto consiste na implementa��o de um Web Service utilizando Java + SpringBoot. 

A aplica��o permite que o usu�rio fa�a o envio de arquivos PDF juntamente com um CPF. Uma chave HASH a partir das informa��es de data e hora do servidor, cpf informado e ip do usu�rio ser� gerada, impressa no rodap� do aqruivo PDF e o mesmo � armazenado no servidor.

Inicializando Aplica��o
-------------

Inicie a aplica��o apartir da classe "PdfUploaderApplication.java" executando como Java Aplication. O servi�o estar� dispon�vel atrav�s do link http://localhost:8080/uploadFile.

Testando Com Swagger
---------

Com a aplica��o executando, estar� dispon�vel atrav�s do link http://localhost:8080/swagger-ui.html uma documenta��o completa do servi�o dispon�vel.

![Pagina Swagger](imgs/swaggerIndex.png)

No link "List Operations" estar� listado os m�todos presentes no Web Service.

![Pagina Swagger2](imgs/swaggerIndex2.png)

O arquivo salvo poder� ser acessado na pasta uploads existente na raiz do projeto.
