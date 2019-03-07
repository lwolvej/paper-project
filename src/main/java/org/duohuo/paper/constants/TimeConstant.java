package org.duohuo.paper.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class TimeConstant {

    //类别缓存的过期时间：一天
    public static final long CATEGORY_EXPIRE_TIME = 60 * 60 * 24L;

    //年缓存的过期时间：一天
    public static final long TIME_YEAR_EXPIRE_TIME = 60 * 60 * 24L;

    //验证码的过期时间，单位秒
    public static final long CAPTCHA_EXPIRE_TIME = 60 * 60 * 1000L;

    //用户登陆之后的有效期
    public static final int TIME_USER_SURVIVAL = 60 * 60 * 1000;

    //下载的日期格式
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy:MM:dd-HH:mm:ss");
}
