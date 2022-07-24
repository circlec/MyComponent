package zc.commonlib.utils;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import zc.commonlib.BaseApplication;

/**
 * @作者 zhouchao
 * @日期 2019/6/13
 * @描述
 */
public class FileUtil {
    private static Application getApplication() {
        return BaseApplication.getInstance();
    }

    /**
     * 保存数据到文件,如果文件夹不存在，会自动创建好
     *
     * @param inSDCard 是否保存到SDCard
     * @param dirName  保存到的文件夹名称
     * @param fileName 文件名
     * @param is       输入流
     * @return 写文件出错时返回false，成功时返回true
     */
    public static boolean saveFile(boolean inSDCard, String dirName,
                                   String fileName, InputStream is) {
        File outFile = newFile(inSDCard, dirName, fileName);

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = is.read(buffer)) != -1) {
                fos.write(buffer, 0, count);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static File createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        try {
            File image = File.createTempFile(imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    Environment.getExternalStorageDirectory() /* directory */);
            return image;
        } catch (IOException e) {
            //do noting
            return null;
        }
    }

    /**
     * 获取指定目录下的所有zip文件名
     *
     * @param dirPath
     * @return
     */
    public static ArrayList<String> zipFileName(String dirPath) {
        ArrayList<String> zipFileName = new ArrayList<String>();
        File mfile = new File(dirPath);
        File[] files = mfile.listFiles();

        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsZipFile(file.getPath())) {
                zipFileName.add(file.getPath());
            }
        }
        return zipFileName;

    }

    private static boolean checkIsZipFile(String fName) {
        boolean isZipFile = false;

        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("zip")) {
            isZipFile = true;
        } else {
            isZipFile = false;
        }
        return isZipFile;

    }

    /**
     * 创建一个文件夹文件对象，创建对象后是否自动在存储设备上创建文件夹由参数createDirIfNeeded指定
     *
     * @param inSDCard          是否在SDCard上创建
     * @param dirName           文件夹名称
     * @param createDirIfNeeded 是否自动在存储设备上创建对应的文件夹
     * @return 文件夹文件对象
     */
    public static File newDir(boolean inSDCard, String dirName,
                              boolean createDirIfNeeded) {
        File dir = null;
        if (inSDCard) {
            dir = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "Android" + File.separator + "data"
                    + File.separator + getApplication().getPackageName()
                    + File.separator + dirName);
        } else {
            dir = new File(getApplication().getFilesDir(), dirName);
        }

        if (createDirIfNeeded && !dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 创建一个文件夹文件对象，创建对象后会自动在存储设备上创建对应的文件夹，如不需要自动创建文件夹请使用 newDir(boolean
     * inSDCard, String dirName, boolean createDirIfNeeded) 并将最后一个参数置为false
     *
     * @param inSDCard 是否在SDCard上创建
     * @param dirName  文件夹名称
     * @return 文件夹文件对象
     */
    public static File newDir(boolean inSDCard, String dirName) {
        return newDir(inSDCard, dirName, true);
    }

    /**
     * 创建一个普通文件对象，创建对象后是否自动在存储设备上创建相关的文件夹由参数createDirIfNeeded指定
     *
     * @param inSDCard          是否在SDCard上创建
     * @param dirName           保存到的文件夹名称
     * @param fileName          文件名
     * @param createDirIfNeeded 是否在存储设备上自动创建相关的文件夹
     * @return 文件对象
     */
    public static File newFile(boolean inSDCard, String dirName,
                               String fileName, boolean createDirIfNeeded) {
        return new File(newDir(inSDCard, dirName, createDirIfNeeded), fileName);
    }

    /**
     * 创建一个普通文件对象，创建对象后会自动在存储设备上创建相关的文件夹，如不需要自动创建文件夹请使用 newFile(boolean
     * inSDCard, String dirName, String fileName, boolean createDirIfNeeded)
     * 并将最后一个参数置为false
     *
     * @param inSDCard 是否在SDCard上创建
     * @param dirName  保存到的文件夹名称
     * @param fileName 文件名
     * @return 文件对象
     */
    private static File newFile(boolean inSDCard, String dirName,
                                String fileName) {
        return newFile(inSDCard, dirName, fileName, true);
    }

    /**
     * 创建一个普通文件对象，创建对象后会自动在存储设备上创建相关的文件夹，如不需要自动创建文件夹请使用 newFile(boolean
     * inSDCard, String dirName, String fileName, boolean createDirIfNeeded)
     * 并将最后一个参数置为false
     *
     * @param dirName  保存到的文件夹名称
     * @param fileName 文件名
     * @return 文件对象
     */
    public static File newFile(String dirName, String fileName) {
        if (sdCardAvailable()) {
            return newFile(true, dirName, fileName, true);
        } else {
            return newFile(false, dirName, fileName, true);
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param inSDCard 是否在SDCard的文件
     * @param dirName  保存文件的文件夹名称
     * @param fileName 文件名
     * @return true代表文件存在，false为不存在
     */
    public static boolean exists(boolean inSDCard, String dirName,
                                 String fileName) {
        File file = newFile(inSDCard, dirName, fileName, false);
        return file.exists();
    }

    /**
     * 判断文件夹是否存在
     *
     * @param inSDCard 是否在SDCard的文件夹
     * @param dirName  文件夹名称
     * @return true 代表文件夹存在，false为不存在
     */
    public static boolean exists(boolean inSDCard, String dirName) {
        File dir = newDir(inSDCard, dirName, false);
        return dir.exists();
    }

    /**
     * 删除存储设备上的文件
     *
     * @param inSDCard 文件是否在SDCard
     * @param dirName  文件存储的文件夹
     * @param fileName 文件名
     * @return true代表文件删除成功，false为删除失败
     */
    public static boolean deleteFile(boolean inSDCard, String dirName,
                                     String fileName) {
        File file = newFile(inSDCard, dirName, fileName, false);
        return !file.exists() || file.delete();
    }

    /**
     * 删除指定的文件夹中的文件，满足filter条件的所有文件将被删除
     *
     * @param inSDCard 文件是否在SDCard
     * @param dirName  文件夹名称
     * @param filter   文件过滤条件，filter.accept(File file)接口返回true的文件将被删除
     */
    public static void deleteFiles(boolean inSDCard, String dirName,
                                   FileFilter filter) {
        File dir = newDir(inSDCard, dirName, false);
        if (dir.exists()) {
            if (filter != null) {
                for (File file : dir.listFiles()) {
                    if (filter.accept(file)) {
                        file.delete();
                    }
                }
            } else {
                for (File file : dir.listFiles()) {
                    file.delete();
                }
            }
        }
    }

    /**
     * 删除指定的文件夹中的所有文件
     *
     * @param inSDCard 文件是否在SDCard
     * @param dirName  文件夹名称
     */
    public static void deleteFiles(boolean inSDCard, String dirName) {
        deleteFiles(inSDCard, dirName, null);
    }

    /**
     * 删除文件夹中的所有文件，并将此文件夹也删除，如只需要删除文件夹中的文件，请使用deleteFiles
     *
     * @param inSDCard 文件是否在SDCard
     * @param dirName  文件夹名称
     */
    public static void deleteDir(boolean inSDCard, String dirName) {
        File dir = newDir(inSDCard, dirName, false);
        if (dir.exists()) {
            deleteFiles(inSDCard, dirName);
            dir.delete();
        }
    }

    /**
     * @Description: 删除除strFileName外的所有文件
     */
    public static boolean delAllFile(String dirPath, String strFileName) {
        File file = new File(dirPath);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        File[] tempList = file.listFiles();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            temp = tempList[i];
            if (!TextUtils.isEmpty(strFileName)) {
                if (!temp.getName().equals(strFileName)) {
                    temp.delete();
                }
            }
        }
        return false;
    }

    /**
     * 判断sdcard是否可用
     *
     * @param
     * @return
     */
    public static boolean sdCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String readFile(String Path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(
                    fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    /**
     * 复制文件
     *
     * @param filename 文件名
     * @param bytes    数据
     */
    public static void copy(String filename, byte[] bytes) {
        try {
            //如果手机已插入sd卡,且app具有读写sd卡的权限
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                FileOutputStream output = null;
                output = new FileOutputStream(filename);
                output.write(bytes);
                output.close();
            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    public static void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String saveBitmap(String dir, String fileName, Bitmap b) {
        String filePath = dir + fileName;
        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Uri 转 绝对路径
     *
     * @param uri
     * @return
     */
    public static String getFilePathByUri_BELOWAPI11(Uri uri, Context context) {
        // 以 content:// 开头的，比如  content://media/external/file/960
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            String path = null;
            String[] projection = new String[]{MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null,
                    null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }
        return null;
    }
}
