package com.monda.edoctor.wahiya.client;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.monda.edoctor.wahiya.dto.req.AttachmentDetails;
import com.monda.edoctor.wahiya.dto.req.AttachmentReq;
import com.monda.edoctor.wahiya.dto.req.UploadFileReq;
import com.monda.edoctor.wahiya.model.AttachmentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;

@Slf4j
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

    public AttachmentDetails uploadFile(UploadFileReq uploadFileReq) throws IOException{
      //  String fileUrl = "";
        String attachmentKey ="";
        String attachmentName = uploadFileReq.getFile().getOriginalFilename().replace(" ","_");
        try{
            File file = convertMultiPartToFile(uploadFileReq.getFile());
            attachmentKey = generateFileName(uploadFileReq);
      //      fileUrl = endpointUrl + SEPARATOR+bucketName+SEPARATOR+attachmentKey;
            uploadFileToS3bucket(attachmentKey, file);
            file.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
        return AttachmentDetails.builder().attachmentName(attachmentName).attachmentKey(attachmentKey).build();
    }

    public ByteArrayOutputStream fetchFile(AttachmentEntity attachmentEntity){

        return downloadFileFromS3bucket(attachmentEntity.getAttachmentKey());
    }

    private void uploadFileToS3bucket(String fileName, File file){
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
    }

    private ByteArrayOutputStream downloadFileFromS3bucket(String fileKey){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            S3Object s3object = s3Client.getObject(bucketName, fileKey);
            InputStream is = s3object.getObjectContent();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (AmazonServiceException ase) {
            log.info("sCaught an AmazonServiceException from GET requests, rejected reasons:");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
            throw ase;
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException: ");
            log.info("Error Message: " + ace.getMessage());
            throw ace;
        }
        return baos;
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
