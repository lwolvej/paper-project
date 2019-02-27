package org.duohuo.paper.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

/**
 * @author lwolvej
 */
@Configuration("captchaConfig")
@PropertySource(value = "classpath:/captcha.properties")
public class CaptchaConfig {

    @Value("${kaptcha.image.width}")
    private String kImageWidth;

    @Value("${kaptcha.image.height}")
    private String kImageHeight;

    @Value("${kaptcha.textproducer.char.string}")
    private String kTextproducerString;

    @Value("${kaptcha.textproducer.char.length}")
    private String kTextproducerLength;

    @Value("${kaptcha.border}")
    private String kBorder;

    @Value("${kaptcha.textproducer.font.color}")
    private String kTextproducerFontColor;

    @Value("${kaptcha.textproducer.font.size}")
    private String kTextproducerFontSize;

    @Value("${kaptcha.textproducer.font.names}")
    private String kTextproducerFontName;

    @Value("${kaptcha.background.clear.from}")
    private String kBackgroundClearFrom;

    @Value("${kaptcha.background.clear.to}")
    private String kBackgroundClearTo;

    @Value("${kaptcha.obscurificator.impl}")
    private String kObscurificatorImpl;

    @Value("${kaptcha.noise.impl}")
    private String kNoiseImpl;

    @Value("${kaptcha.noise.color}")
    private String kNoiseColor;

    @Value("${kaptcha.textproducer.char.space}")
    private String kTextproducerSpace;


    @Bean(name = "myCaptcha")
    public DefaultKaptcha captchaProducer() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();

        Properties properties = new Properties();
        //验证码宽度
        properties.setProperty("kaptcha.image.width", kImageWidth);
        //验证码高度
        properties.setProperty("kaptcha.image.height", kImageHeight);
        //生成验证码内容范围
        properties.setProperty("kaptcha.textproducer.char.string", kTextproducerString);
        // 验证码个数
        properties.setProperty("kaptcha.textproducer.char.length", kTextproducerLength);
        //是否有边框
        properties.setProperty("kaptcha.border", kBorder);
        //验证码字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", kTextproducerFontColor);
        //验证码字体大小
        properties.setProperty("kaptcha.textproducer.font.size", kTextproducerFontSize);
        // 验证码所属字体样式
        properties.setProperty("kaptcha.textproducer.font.names", kTextproducerFontName);
        properties.setProperty("kaptcha.background.clear.from", kBackgroundClearFrom);
        properties.setProperty("kaptcha.background.clear.to", kBackgroundClearTo);
        properties.setProperty("kaptcha.obscurificator.impl", kObscurificatorImpl);
        properties.setProperty("kaptcha.noise.impl", kNoiseImpl);
        //干扰线颜色
        properties.setProperty("kaptcha.noise.color", kNoiseImpl);
        //验证码文本字符间距
        properties.setProperty("kaptcha.textproducer.char.space", kTextproducerSpace);

        Config Config = new Config(properties);
        defaultKaptcha.setConfig(Config);

        return defaultKaptcha;
    }
}
