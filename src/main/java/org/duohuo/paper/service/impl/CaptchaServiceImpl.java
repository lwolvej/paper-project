package org.duohuo.paper.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.commons.codec.binary.Base64;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.repository.RedisRepository;
import org.duohuo.paper.service.CaptchaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("captchaServiceImpl")
public class CaptchaServiceImpl implements CaptchaService {

    @Resource(name = "redisRepository")
    private RedisRepository redisRepository;

    @Resource(name = "myCaptcha")
    private DefaultKaptcha myCaptcha;

    @Override
    public JsonResult getCaptcha() {
        //创建验证码内容
        String capText = myCaptcha.createText();
        //创建该验证码id
        String uuid = UUID.randomUUID().toString();
        //创建图片
        BufferedImage image = myCaptcha.createImage(capText);
        try {
            //转换为byte流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            //图片转换为base64编码
            String imageBase64 = Base64.encodeBase64String(outputStream.toByteArray());
            Map<String, Object> map = new HashMap<>();
            map.put("uuid", uuid);
            map.put("image", imageBase64);
            map.put("time", 60);
            //redis存储的时间为60s
            redisRepository.set(uuid, capText, 200L);
            return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name(), map);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
