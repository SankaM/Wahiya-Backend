package com.monda.edoctor.wahiya.client;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.monda.edoctor.wahiya.dto.req.UploadFileReq;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class AmazonClient {
    private AmazonS3 s3Client;

    @Value("${aws.endpoint-url}")
    private String endpointUrl;
    @Value("${aws.bucket-name}")
    private String bucketName;
    @Value("${aws.credentials.access-key}")
    private String accessKey;
    @Value("${aws.credentials.secret-key}")
    private String secretKey;

    private static String SEPARATOR = "/";
    @PostConstruct
    private void initializeAmazon(){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3Client = new AmazonS3Client(credentials);
    }

    public String uploadFile(UploadFileReq uploadFileReq) throws IOException{
        String fileUrl = "";
        try{
            File file = convertMultiPartToFile(uploadFileReq.getFile());
            String fileName = generateFileName(uploadFileReq);
            fileUrl = endpointUrl + SEPARATOR+bucketName+SEPARATOR+fileName;
            uploadFileToS3bucket(fileName, file);
            file.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
        return fileUrl;
    }

    private void uploadFileToS3bucket(String fileName, File file){
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
    }

    private String generateFileName(UploadFileReq uploadFileReq){
        return uploadFileReq.getDoctorId()+SEPARATOR+uploadFileReq.getPatientId()+SEPARATOR+uploadFileReq.getFile().getOriginalFilename().replace(" ","_");
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException{
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }




}
