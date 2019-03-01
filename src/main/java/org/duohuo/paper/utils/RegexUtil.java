package org.duohuo.paper.utils;

import org.duohuo.paper.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexUtil {

    private RegexUtil() {
    }

    public static Boolean numberRegex(String number) {
        return validation(Constants.NUMBER_REGEX, number);
    }

    public static Boolean letterRegex(String letter) {
        return validation(Constants.LETTER_REGEX, letter);
    }

    public static Boolean imageRegex(String imageName) {
        return validation(Constants.FILE_IMAGE_REGEX, imageName);
    }

    public static Boolean userNameValidation(String userName) {
        return validation(Constants.USER_NAME_REGEX, userName);
    }

    public static Boolean zipFileValidation(String fileName) {
        return validation(Constants.FILE_ZIP_REGEX, fileName);
    }

    public static Boolean passwordValidation(String password) {
        return validation(Constants.USER_PASSWORD_REGEX, password);
    }

    public static Boolean excelFileValidation(String fileName) {
        return validation(Constants.FILE_EXCEL_REGEX, fileName);
    }

    private static Boolean validation(String regex, String data) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }
}