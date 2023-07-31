/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mailing;

import cfg.MyConfig;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import util.FileUploadManager;
import util.StringReplacer;

/**
 *
 * @author Admin
 */
public class MailObject {

    private String subject;
    private String cc;
    private String content;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public static MailObject fromHTML(String subject, String contentSrc) {
        MailObject mo = new MailObject();
        mo.setSubject(subject);
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(contentSrc), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        String content = contentBuilder.toString();
        mo.setContent(content);
        
        return mo;
    }

    public static MailObject fromHTMLTemplate(String subject, String contentSrc, Map<String, String> params) {
        MailObject mo = new MailObject();
        mo.setSubject(subject);
        StringBuilder contentBuilder = new StringBuilder();
        try {
            FileUploadManager fum = new FileUploadManager(MyConfig.retrieve());
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(fum.pathGet(contentSrc)), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        String content = StringReplacer.replace(contentBuilder.toString(), "§", params);
        mo.setContent(content);
        return mo;
    }
}
