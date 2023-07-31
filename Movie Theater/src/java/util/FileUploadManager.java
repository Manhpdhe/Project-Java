/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import cfg.UploadConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class FileUploadManager {
    
    private UploadConfig uploadCfg;
    
    public FileUploadManager(UploadConfig cfg) {
        this.uploadCfg = cfg;
    }

    public UploadConfig getUploadCfg() {
        return uploadCfg;
    }

    public void setUploadCfg(UploadConfig cfg) {
        this.uploadCfg = cfg;
    }
    
    public void loadFile(String extraPath, Part part) {
        
    }
    
    public String pathGet(String extraPath) {
        String fixFormatPath = extraPath.replace("\\", "/");
        String filePath = String.format("%s/%s", uploadCfg.getUploadLocation(), fixFormatPath);
        return filePath;
    }
    
    public String saveFile(String extraPath, Part part) {
        try {
            
            String newFileName = RandomString.generate();
            String filename = part.getSubmittedFileName();
            String extension = filename.substring(filename.lastIndexOf(".")+1);
            String fixFormatPath = extraPath.replace("\\", "/");
            String folderPath = String.format("%s/%s", uploadCfg.getUploadLocation(), fixFormatPath);
            String fileFull = String.format("%s.%s",newFileName, extension);
            Files.createDirectories(Paths.get(folderPath));
            File file = new File(folderPath, fileFull);

            try (InputStream input = part.getInputStream()) {
                Files.copy(input, file.toPath());
            }
            
            return fileFull;
            
        } catch (IOException ex) {
            Logger.getLogger(FileUploadManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
}
