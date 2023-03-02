package com.example.blogging.service.impl;

import com.example.blogging.service.FileService;
import org.apache.commons.lang3.RandomStringUtils;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService
{


    @Override
    public String savedFileName(MultipartFile multipartFile,String path) throws IOException {

        Path uploadPath = Paths.get(path);
        String fileName=multipartFile.getOriginalFilename();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileCode = RandomStringUtils.randomAlphanumeric(8);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            fileName=fileCode + "-" + fileName;
            System.out.printf(filePath.toString());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }

        return fileName;

    }

    @Override
    public InputStream getImage(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+ File.separator+fileName;
        InputStream is=new FileInputStream(fullPath);
        return is;
    }



    @Override
    public String saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            String fileCode = RandomStringUtils.randomAlphanumeric(8);
            fileName=fileCode+"_"+fileName;
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
