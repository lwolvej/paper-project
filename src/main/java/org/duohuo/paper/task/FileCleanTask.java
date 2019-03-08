package org.duohuo.paper.task;

import org.duohuo.paper.constants.FolderConstant;
import org.duohuo.paper.utils.ExcelUtil;
import org.duohuo.paper.utils.FileUtil;
import org.duohuo.paper.utils.RegexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * <p>定时清理可能会残留的zip文件，和临时文件夹，虽然可能aop已经清理掉了</p>
 *
 * @author lwolvej
 */
@Component("fileCleanTask")
public class FileCleanTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCleanTask.class);

    //每天凌晨三点检查和jar同级的目录中是否还有zip文件，有则删除
    @Scheduled(cron = "0 0 3 1/1 * ?")
    public void zipFileClean() {
        LOGGER.info("开始清理zip文件");
        String jarPath = ExcelUtil.getJarPath();
        File file = new File(jarPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) continue;
                    String fileName = f.getName();
                    if (RegexUtil.zipFileValidation(fileName)) {
                        if (f.delete()) {
                            LOGGER.info("成功删除一个残留的zip文件:" + fileName);
                        } else {
                            LOGGER.warn("删除zip文件失败:" + fileName);
                        }
                    }
                }
            }
        } else {
            LOGGER.warn("jar路径获取可能存在错误:" + jarPath);
        }
    }

    //每天五点检查jar同级目录下临时文件夹是否还存在，存在则删除
    @Scheduled(cron = "0 0 5 1/1 * ?")
    public void tempFolderDelete() {
        LOGGER.info("开始清理临时文件夹");
        String jarPath = ExcelUtil.getJarPath();
        File file = new File(jarPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        String directoryName = f.getName();
                        if (directoryName.contains(FolderConstant.TEMP_FILE_ESI_HIGHLY_PAPER)) {
                            FileUtil.fileDelete(f.getAbsolutePath());
                            LOGGER.info("成功删除esi高被引临时目录");
                        } else if (directoryName.contains(FolderConstant.TEMP_FILE_ESI_HOT_PAPER)) {
                            FileUtil.fileDelete(f.getAbsolutePath());
                            LOGGER.info("成功删除esi热点临时目录");
                        } else if (directoryName.contains(FolderConstant.TEMP_FILE_SCHOOL_HIGHLY_PAPER)) {
                            FileUtil.fileDelete(f.getAbsolutePath());
                            LOGGER.info("成功删除本校高被引临时目录");
                        } else if (directoryName.contains(FolderConstant.TEMP_FILE_SCHOOL_HOT_PAPER)) {
                            FileUtil.fileDelete(f.getAbsolutePath());
                            LOGGER.info("成功删除本校热点临时目录");
                        }
                    }
                }
            }
        } else {
            LOGGER.warn("jar路径获取可能存在错误:" + jarPath);
        }
    }
}
