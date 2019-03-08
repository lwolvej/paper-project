package org.duohuo.paper.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import org.duohuo.paper.exceptions.ZipFileException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public final class FileUtil {

    private FileUtil() {

    }

//    public static String getTypeFilePath(Integer type) {
//        String path = ExcelUtil.getJarPath() + File.separator;
//        switch (type) {
//            case 1:
//                path = path.concat(Constants.TEMP_FILE_ESI_HIGHLY_PAPER);
//                break;
//            case 2:
//                path = path.concat(Constants.TEMP_FILE_ESI_HOT_PAPER);
//                break;
//            case 3:
//                path = path.concat(Constants.TEMP_FILE_SCHOOL_HIGHLY_PAPER);
//                break;
//            case 4:
//                path = path.concat(Constants.TEMP_FILE_SCHOOL_HOT_PAPER);
//                break;
//            default:
//                throw new RuntimeException();
//        }
//        return path.concat(System.currentTimeMillis() + File.separator);
//    }


    public static byte[] createZipWithOutPutStreams(final Map<String, byte[]> fileMap) {
        ZipOutputStream zipOutputStream;
        for (Map.Entry<String, byte[]> file : fileMap.entrySet()) {
            InputStream inputStream = new ByteArrayInputStream(file.getValue());

        }

        return null;
    }

    public static void fileDelete(final String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            fileDelete(file);
        }
    }

    //删除临时文件
    public static void fileDelete(final List<String> filePaths, final String targetPath) {
        for (String path : filePaths) {
            File file = new File(targetPath + path);
            if (file.isDirectory()) {
                fileDelete(file);
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void fileDelete(final File directFile) {
        File[] files = directFile.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    fileDelete(file);
                } else {
                    file.delete();
                }
            }
        }
        directFile.delete();
    }

    /*
    需要zip存储路径，解压之后的文件路径，以及文件名称
     */
    public static List<String> decompressZipFile(final byte[] data, final String targetPath, final String decompressPath, final String fileName) {
        String originalZipFilePath = targetPath + File.separator + fileName;
        //这是压缩文件
        File file = saveFile(data, originalZipFilePath);
        ZipFile zipFile;
        List<String> resultPathList = new ArrayList<>();
        try {
            zipFile = new ZipFile(file);
            zipFile.setFileNameCharset("utf-8");
            zipFile.extractAll(decompressPath);
            List fileHeaderList = zipFile.getFileHeaders();
            if (fileHeaderList == null) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
                throw new ZipFileException("上传zip为空:" + fileName);
            }
            for (Object o : fileHeaderList) {
                resultPathList.add(((FileHeader) o).getFileName());
            }
        } catch (Exception e) {
            throw new ZipFileException("Zip处理出错:" + fileName);
        } finally {
            //最后删除压缩文件
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
        return resultPathList;
    }

    private static File saveFile(final byte[] data, final String zipFilePath) {
        File file = new File(zipFilePath);
        OutputStream outputStream = null;
        try {
            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(data);
            outputStream.flush();
        } catch (Exception e) {
            file.deleteOnExit();
            throw new ZipFileException(e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception ignore) {
                }
            }
        }
        return file;
    }
}
