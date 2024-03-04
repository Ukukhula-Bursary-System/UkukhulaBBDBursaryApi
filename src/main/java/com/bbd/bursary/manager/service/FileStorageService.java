package com.bbd.bursary.manager.service;

import com.bbd.bursary.manager.util.ExpirationLink;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Service
public class FileStorageService {

    private final Path FILE_STORAGE_LOCATION = Paths.get("uploads");

    public void createFileStorageDirectory() {
        try {
            Files.createDirectories(FILE_STORAGE_LOCATION);
        } catch (IOException e) {
            System.out.println("Directory already exists");
        }
    }

    public String save(MultipartFile file) throws IOException {
        String tokenizedFileName = ExpirationLink.generateToken(
                file.getOriginalFilename() +
                        System.currentTimeMillis()).replace('.', '_') +
                "." + file.getContentType().split("/")[1];
        Files.copy(file.getInputStream(), FILE_STORAGE_LOCATION.resolve(tokenizedFileName));
        return tokenizedFileName;
    }

    public Resource load(String filename) throws MalformedURLException {
        Path file = FILE_STORAGE_LOCATION.resolve(filename);
        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable())
            return resource;
        throw new MalformedURLException();
    }
}
