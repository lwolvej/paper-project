package org.duohuo.paper.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.duohuo.paper.annotation.Upload;
import org.duohuo.paper.constants.FolderConstant;
import org.duohuo.paper.utils.ExcelUtil;
import org.duohuo.paper.utils.FileUtil;
import org.duohuo.paper.utils.RegexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Aspect
@Component("uploadAspect")
public class UploadAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadAspect.class);

    @AfterReturning("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(upload)")
    public void uploadCleanAfterReturn(final Upload upload) {
        uploadClean();
    }

    @AfterThrowing("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(upload)")
    public void uploadCleanAfterThrow(final Upload upload) {
        uploadClean();
    }

    private void uploadClean() {
        //利用该切面，在每次上传处理结束，删除jar目录下相关文件
        String jarPath = ExcelUtil.getJarPath();
        File file = new File(jarPath);
        try {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        String tempName = f.getName();
                        if (f.isDirectory()) {
                            if (tempName.contains(FolderConstant.TEMP_FILE_ESI_HIGHLY_PAPER)) {
                                FileUtil.fileDelete(f.getAbsolutePath());
                                LOGGER.info("成功删除esi高被引相关目录{}", tempName);
                            } else if (tempName.contains(FolderConstant.TEMP_FILE_ESI_HOT_PAPER)) {
                                FileUtil.fileDelete(f.getAbsolutePath());
                                LOGGER.info("成功删除esi热点相关目录:{}", tempName);
                            } else if (tempName.contains(FolderConstant.TEMP_FILE_SCHOOL_HIGHLY_PAPER)) {
                                FileUtil.fileDelete(f.getAbsolutePath());
                                LOGGER.info("成功删除本校高被引相关目录:{}", tempName);
                            } else if (tempName.contains(FolderConstant.TEMP_FILE_SCHOOL_HOT_PAPER)) {
                                FileUtil.fileDelete(f.getAbsolutePath());
                                LOGGER.info("成功删除本校热点相关目录:{}", tempName);
                            }
                        } else {
                            if (RegexUtil.zipFileValidation(tempName)) {
                                FileUtil.fileDelete(f.getAbsolutePath());
                                LOGGER.info("成功删除相关zip文件:{}", tempName);
                            }
                        }
                    }
                }
            } else {
                LOGGER.warn("jar路径获取存在错误:" + jarPath);
            }
        } catch (Exception e) {
            LOGGER.error("删除相关文件出错:{}", e.getMessage());
        }
    }
}
