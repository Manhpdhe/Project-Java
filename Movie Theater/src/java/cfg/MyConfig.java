/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cfg;

/**
 *
 * @author Admin
 */
public class MyConfig extends UploadConfig {
    // Left empty for server defined
    // You can change the below config base on your own
    private static String uploadDir = "D:\\upload";
    
    public static UploadConfig retrieve() {
        if (uploadDir == null || uploadDir.equals("")) {
            return new UploadConfig();
        }
        else {
            return new UploadConfig(uploadDir);
        }
    }
}
