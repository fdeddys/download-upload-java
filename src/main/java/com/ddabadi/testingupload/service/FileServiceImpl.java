package com.ddabadi.testingupload.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl {

    private String namaFile = "tesAja.jpeg";

    public String save(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")){
                // error
            }
//            Path target = Paths.get("d:/tes/" + file.getOriginalFilename()).toAbsolutePath().normalize();
            Path target = Paths.get("d:/tes/" + namaFile).toAbsolutePath().normalize();

            try {
                Files.createDirectories(target);
                Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            System.out.println(e);
            return "failed";
        }
        return fileName.toString();
    }

    public Resource loadFile(){
//        Path target = Paths.get("d:/tes/129.jpg" ).toAbsolutePath().normalize();
        Path target = Paths.get("d:/tes/" + namaFile ).toAbsolutePath().normalize();
        try {
            Resource resource = new UrlResource(target.toUri());
            if (resource.exists()){
                return resource;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
