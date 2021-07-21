package com.restteam.ong.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AmazonS3ClientService {
    private AmazonS3 s3Client;
    @Value("${aws_access_key_id}")
    private String accessKey;
    @Value("${aws_secret_access_key}")
    private String secretKey;
    @Value("${aws_bucket_name}")
    private String bucketName;
    @Value("${aws_bucket_region}")
    private String region;
    @Value("${aws_endpoint_url}")
    private String endpointUrl;


    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion(region).build();
    }

    //En la request el archivo viene de tipo MultiPart, tengo que convertirlo a File
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        try {
            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    //Genero el nombre del archivo con el horario actual y el nombre del archivo
    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    //Este metodo sube el archivo al bucket en amazon s3, tambien lo setea como publico
    //es decir, cualquier usuario solo con la url puede obtenerlo en la web
    private void uploadFileTos3bucket(String fileName, File file) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    //Esta funcion es la que llama el service, se encarga de convertir a tipo File, generar nombre
    //generar la url del archivo, y subirlo.
    public String uploadFile(MultipartFile multipartFile) throws Exception {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return fileUrl;
    }

    //Esta funcion elimina un archivo del bucket solo con su url
    public String deleteFileFromS3Bucket(String fileUrl) throws Exception {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            if(s3Client.doesObjectExist(bucketName, fileName)){
                s3Client.deleteObject(bucketName,fileName);
                return "Successfully deleted";
            }
            throw new FileNotFoundException("File url dont matches with any object in the bucket");
        } catch (SdkClientException e) {
            throw new SdkClientException(e.getMessage());
        } catch (AmazonClientException e) {
            throw new AmazonClientException(e.getMessage());
        } catch (Exception e) {
          throw new Exception(e.getMessage());
        }

    }

}
