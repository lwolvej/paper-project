package org.duohuo.paper.annotation;

import java.lang.annotation.*;

/**
 * <p>利用该接口在每次上传成功返回结果之后，删除相关上传文件(只需要在论文上传(zip)处加)</p>
 *
 * @author lwolvej
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Upload {
}
