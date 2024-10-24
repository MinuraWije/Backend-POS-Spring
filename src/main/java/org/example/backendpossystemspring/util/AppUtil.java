package org.example.backendpossystemspring.util;

import java.util.Base64;
import java.util.UUID;

public class AppUtil {
    public static String generateItemId(){
        return "Item- "+UUID.randomUUID();
    }
    public static String generateOrderId(){
        return "Order- "+UUID.randomUUID();
    }
    public static String generateCustomerId(){
        return "Customer- "+UUID.randomUUID();
    }
    public static String profilePicToBase64(byte [] profilePic){
        return Base64.getEncoder().encodeToString(profilePic);
    }
}
