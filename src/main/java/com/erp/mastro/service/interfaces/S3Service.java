package com.erp.mastro.service.interfaces;

public interface S3Service {
    public void downloadFile(String keyName);
    public void uploadFile(String keyName, String uploadFilePath, String id, String ftype);

    public void deleteProductImage(Long productId, String fileNamde);
}
