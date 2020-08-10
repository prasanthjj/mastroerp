package com.erp.mastro.Store;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.exception.FileStoreException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class MastroFileStore extends MastroFileStoreBase {

    public static void saveProductFile(String productId, Map<String, byte[]> files, FileType ftype) throws FileStoreException {
        Path folderPath = getProductFolderPath(productId, ftype);
        createFolderIfDoesntExist(folderPath);
        files.forEach((fileName, file) -> {
            try {
                Path filePath = getProductFilePath(productId, fileName, ftype);
                filePath.toFile().createNewFile();

                Files.write(filePath, file);
            } catch (Exception e) {
                MastroLogUtils.error(MastroFileStore.class, "Exception occurred while saving product images : {}", fileName, e);
            }
        });
    }

    public static Path getProductFolderPath(String templeId, FileType ftype) throws FileStoreException {
        return getUserFolder().resolve(templeId).resolve(getEnvironment().getProperty(getFileFolderPropKey(ftype)));
    }

    public static enum FileType {
        productImage,
    }

    public static Path getProductFilePath(String productId, String fileName, FileType ftype) throws FileStoreException {
        return getUserFolder()
                .resolve(productId)
                .resolve(getEnvironment().getProperty(getFileFolderPropKey(ftype)))
                .resolve(fileName);
    }

    private static String getFileFolderPropKey(FileType ftype) throws FileStoreException {
        switch (ftype) {

            case productImage:
                return "MastroFilesStoreProduct.productImageFolder";
            default:
                throw new FileStoreException("No proper folder for fileType : " + ftype.name());
        }
    }

}
