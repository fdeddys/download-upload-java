package com.ddabadi.testingupload.rest;

import com.ddabadi.testingupload.service.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
@RequestMapping("api")

public class FileController {

    @Autowired private FileServiceImpl fileService;

    @CrossOrigin
    @PostMapping("uploadfile")
    public String uploadFile(@RequestParam("file")MultipartFile file){

        String fileName = fileService.save(file);

        return fileName;
    }
    @CrossOrigin
    @GetMapping("prevFile")
    public ResponseEntity<Resource> previewFile(HttpServletRequest request) throws IOException {
        Resource resource = fileService.loadFile();

        String contentType = null;

        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        System.out.println("isi content type " + contentType);
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

}
