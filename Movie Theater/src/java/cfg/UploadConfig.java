/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cfg;

/**
 *
 * @author Admin
 */
public class UploadConfig {
    private String uploadLocation;

    public UploadConfig(String uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

    public UploadConfig() {
        
        if (System.getenv("GROOVY_UPLOAD_LOCATION")!= null) {
            this.uploadLocation = System.getenv("GROOVY_UPLOAD_LOCATION");
        }
    }
    
    public String getUploadLocation() {
        return uploadLocation;
    }

    public void setUploadLocation(String uploadLocation) {
        this.uploadLocation = uploadLocation;
    }
    
    
    
    
    
    
}
