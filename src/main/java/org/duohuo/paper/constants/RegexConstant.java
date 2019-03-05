package org.duohuo.paper.constants;

public final class RegexConstant {

    //字母开头，允许小写字母和数字，5到16位(用户名规范)
    public static final String USER_NAME_REGEX = "^[a-z][a-z0-9]{4,15}$";

    //字母开头，允许小写字母和数字，5到16位(密码规范)
    public static final String USER_PASSWORD_REGEX = "^[a-z][a-z0-9]{4,15}$";

    //通过判断文件后缀查看是否是标准的excel
    public static final String FILE_EXCEL_REGEX = ".*(.xlsx|.XLSX)$";

    //通过判断文件后缀查看是否是标准的zip压缩包
    public static final String FILE_ZIP_REGEX = ".*(.zip|.ZIP)$";

    //验证图片名称是否是png
    public static final String FILE_IMAGE_REGEX = ".*(.png|.PNG)";

    //26个字母不分大小写
    public static final String LETTER_REGEX = "^[A-Za-z]+$";

    //验证一个字符串中是否只包含数字
    public static final String NUMBER_REGEX = "^[1-9]\\d*$";
}
