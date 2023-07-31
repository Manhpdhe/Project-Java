/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.Map;

/**
 *
 * @author Admin
 */
public class StringReplacer {
    public static String replace (String src, String marker, Map<String,String> params) {
        String out = src;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String param = String.format("%s%s%s",marker,key,marker);
            out = out.replaceAll(param, value);
        }
        return out;
    }
}
