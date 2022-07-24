package zc.commonlib;

import android.app.Application;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import zc.commonlib.utils.FileUtil;

/**
 * @作者 zhouchao
 * @日期 2019/11/7
 * @描述
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;
    public static final String DIR_CRASH_LOG = "CrashLog";
    private SimpleDateFormat currTimeFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRouter();
        catchException();
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    private void initRouter() {
        if (BuildConfig.DEBUG) {
            //一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

    private void catchException() {
        System.out.println("unCaughtException--");
        // 程序崩溃时触发线程
        MyUncaughtExceptionHandler uncaughtExceptionHandler = new MyUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
    }

    private class MyUncaughtExceptionHandler implements
            Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            // 保存错误日志
            saveCrashInfo2File(ex);
            // 关闭当前应用
            ActivityManager.finishAllActivities();
        }
    }
    private void saveCrashInfo2File(Throwable ex) {
        if (ex == null || !FileUtil.sdCardAvailable()) return;
        ex.printStackTrace();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String crashContent = writer.toString();
        Log.e(BaseApplication.getInstance().getPackageName(), crashContent);
        try {
            File crashFile = FileUtil.newFile(DIR_CRASH_LOG,
                    currTimeFormat.format(new Date()) + ".txt");
            PrintWriter pw = new PrintWriter(crashFile);
            pw.write(crashContent);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
