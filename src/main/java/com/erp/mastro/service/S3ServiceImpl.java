package com.erp.mastro.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.service.interfaces.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3ServiceImpl implements S3Service {

    private Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${jsa.s3.bucket}")
    String bucketName = Constants.S3_MASTRO;

    @Value("{jsa.aws.access_key_id}")
    private String s3AccessKey;

    @Value("${jsa.aws.secret_access_key}")
    private String s3SecretKey;

    @Override
    public void downloadFile(String keyName) {

        try {

            logger.info("Downloading an object");
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
            logger.info("Content-Type: " +
                    s3object.getObjectMetadata().getContentType());
            MastroLogUtils.error(s3object, "Error");
            logger.info("===================== Import File - Done! =====================");

        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
    }

    @Override
    public void uploadFile(String keyName, String uploadFilePath, String productId, String ftype) {

        try {

            File file = new File(uploadFilePath);
            bucketName = Constants.PRODUCTS_PATH + Constants.FILE_SEPARATOR + productId + Constants.FILE_SEPARATOR + ftype;

            s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
            logger.info("===================== Upload File - Done! =====================");
            file.delete();

        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
    }

    public void deleteProductImage(Long productId, String fileName) throws AmazonClientException {
        bucketName = Constants.PRODUCTS_PATH + Constants.FILE_SEPARATOR + productId + Constants.PRODUCT_IMG_FOLDER;
        s3client.deleteObject(bucketName, fileName);

    }

}
