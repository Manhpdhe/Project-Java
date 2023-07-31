/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Bui Tien Dung
 */
public class Response<T> {
    public static int FORBIDDEN = 403;
    public static int INTERNAL_SERVER_ERROR = 500;
    public static int BAD_GATEWAY = 502;
    public static int OK = 200;
    public static int NOT_FOUND = 404;
    
    private int status;
    private T returnObject;
    private String message;

    public Response() {
    }

    public Response(int status, T returnObject, String message) {
        this.status = status;
        this.returnObject = returnObject;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(T returnObject) {
        this.returnObject = returnObject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
    
}
