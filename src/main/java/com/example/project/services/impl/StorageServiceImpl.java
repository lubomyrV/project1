package com.example.project.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.example.project.services.StorageService;
import org.springframework.stereotype.Service;


@Service
public class StorageServiceImpl implements StorageService {

    private String location = System.getProperty("user.home") + File.separator + "images" + File.separator;
    private Path pathDirectory = Paths.get(location);

    @Override
    public void init() {
        try {
            Files.createDirectories(pathDirectory);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void deleteImg(String path) {
        File file = new File(path);
        if(!path.equals(location)) {
            file.delete();
        }
    }

    @Override
    public void copyFile(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    @Override
    public void renameFile(String oldPath, String newPath) {
        File file = new File(oldPath);
        File file2 = new File(newPath);
        file.renameTo(file2);
    }
}
