package com.erp.mastro.Store;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.exception.FileStoreException;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MastroFileStoreBase {

    private static Environment env;
    private static Path rootFolder;
    private static Path userFolder;

    public static Path getRootFolder() throws FileStoreException {
        if (MastroFileStoreBase.rootFolder == null) {
            MastroFileStoreBase.rootFolder = Paths.get(getEnvironment().getProperty("MastroFileDB.rootFolder"));
            createFolderIfDoesntExist(MastroFileStoreBase.rootFolder);
        }
        return MastroFileStoreBase.rootFolder;
    }

    public static void createFolderIfDoesntExist(Path folderPath) throws FileStoreException {
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                MastroLogUtils.error(MastroFileStoreBase.class, "Cannot create directory : {}.", folderPath.toString(), e);
                throw new FileStoreException("Root Directory creation interrupted");
            }
        }
    }

    public static Path getUserFolder() throws FileStoreException {
        if (MastroFileStoreBase.userFolder == null) {
            MastroFileStoreBase.userFolder = getRootFolder().resolve(getEnvironment().getProperty("Mastro.userFolderName"));
            createFolderIfDoesntExist(MastroFileStoreBase.userFolder);
        }
        return userFolder;
    }

    public static Environment getEnvironment() {
        if (MastroFileStoreBase.env == null) {
            MastroFileStoreBase.env = MastroFileStoreAutowiredPipes.instance.getEnv();
        }
        return env;
    }

    public static void deleteFile(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            MastroLogUtils.error(MastroFileStoreBase.class, "Error deleting File : {}.", file.toString(), e);
        }
    }

    public static Path getFilePath(String userId, String filePath) throws FileStoreException {
        return getUserFolder().resolve(userId).resolve(filePath);
    }

}
