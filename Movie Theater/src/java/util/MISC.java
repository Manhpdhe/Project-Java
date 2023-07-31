/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class MISC {
    private static String alphaStr(int pos) {
        return pos < 0 ? "" : alphaStr((pos / 26) - 1) + (char)(65 + pos % 26);
    }
    
    public static List<String> alphabeticList(int count) {
        List<String> ls = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ls.add(alphaStr(i));
        }
        return ls;
    }
}
