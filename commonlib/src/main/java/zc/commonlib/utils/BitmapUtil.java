package zc.commonlib.utils;

import android.graphics.Bitmap;

/**
 * @作者 zhouchao
 * @日期 2021/3/25
 * @描述 bitmap工具
 */
public class BitmapUtil {

    /**
     * 自定义裁剪，根据第一个像素点(左上角)X和Y轴坐标和需要的宽高来裁剪
     *
     * @param srcBitmap
     * @param firstPixelX
     * @param firstPixelY
     * @param needWidth
     * @param needHeight
     * @return
     */
    public static Bitmap cropBitmap(Bitmap srcBitmap, int firstPixelX, int firstPixelY, int needWidth, int needHeight) {

        if (firstPixelX + needWidth > srcBitmap.getWidth()) {
            needWidth = srcBitmap.getWidth() - firstPixelX;
        }
        if (firstPixelY + needHeight > srcBitmap.getHeight()) {
            needHeight = srcBitmap.getHeight() - firstPixelY;
        }
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap, firstPixelX, firstPixelY, needWidth, needHeight);

        return cropBitmap;
    }

}
