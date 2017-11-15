package com.example.project.services;

import java.io.File;
import java.io.IOException;

public interface StorageService {
    void init();
    void deleteImg(String path);
    void copyFile(File source, File dest) throws IOException;
}
