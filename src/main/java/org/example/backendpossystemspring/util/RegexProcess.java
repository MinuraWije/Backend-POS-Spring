package org.example.backendpossystemspring.util;

import java.util.regex.Pattern;

public class RegexProcess {
    public static boolean itemIdMatcher(String noteId) {
        String regexForNoteId = "^ITEM-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForNoteId);
        return regexPattern.matcher(noteId).matches();
    }
    public static boolean customerIdMatcher(String userId){
        String regexForUserId = "^CUSTOMER-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserId);
        return regexPattern.matcher(userId).matches();
    }
}
