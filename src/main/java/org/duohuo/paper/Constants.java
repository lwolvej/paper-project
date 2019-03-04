package org.duohuo.paper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class Constants {

    //验证码的过期时间，单位秒
    public static final long CAPTCHA_EXPIRE_TIME = 60 * 60 * 1000L;

    //字母开头，允许小写字母和数字，5到16位(用户名规范)
    public static final String USER_NAME_REGEX = "^[a-z][a-z0-9]{4,15}$";

    //字母开头，允许小写字母和数字，5到16位(密码规范)
    public static final String USER_PASSWORD_REGEX = "^[a-z][a-z0-9]{4,15}$";

    //通过判断文件后缀查看是否是标准的excel
    public static final String FILE_EXCEL_REGEX = ".*(.xlsx|.XLSX)$";

    //通过判断文件后缀查看是否是标准的zip压缩包
    public static final String FILE_ZIP_REGEX = ".*(.zip|.ZIP)$";

    public static final String FILE_IMAGE_REGEX = ".*(.png|.PNG)";

    //26个字母不分大小写
    public static final String LETTER_REGEX = "^[A-Za-z]+$";

    public static final String NUMBER_REGEX = "^[1-9]\\d*$";

    //类别的过期时间：一天
    public static final long CATEGORY_EXPIRE_TIME = 60 * 60 * 24L;

    //年的过期时间：一天
    public static final long TIME_YEAR_EXPIRE_TIME = 60 * 60 * 24L;

    //分页每个页面的大小
    public static final Integer PAGE_SIZE = 10;

    //下载的日期格式
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy:MM:dd-HH:mm:ss");

    public static final String PAPER_KEYWORD_TYPE_AC = "accessionNumber";

    public static final String PAPER_KEYWORD_TYPE_DOI = "doi";

    public static final String PAPER_KEYWORD_TYPE_AR = "articleName";

    public static final String TEMP_FILE_ESI_HOT_PAPER = "temp_file_esi_hot_paper";

    public static final String TEMP_FILE_ESI_HIGHLY_PAPER = "temp_file_esi_highly_paper";

    public static final String TEMP_FILE_SCHOOL_HOT_PAPER = "temp_file_school_hot_paper";

    public static final String TEMP_FILE_SCHOOL_HIGHLY_PAPER = "temp_file_school_highly_paper";
}
