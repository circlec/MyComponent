package zc.commonlib.utils.glide;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

/**
 * @作者 zhouchao
 * @日期 2022/7/24
 * @描述
 */
interface ImageEngine {
    /**
     * Loading image
     *
     * @param context
     * @param url
     * @param imageView
     */
    void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);


    /**
     * Load album catalog pictures
     *
     * @param context
     * @param url
     * @param imageView
     */
    void loadFolderImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);

    /**
     * Load GIF image
     *
     * @param context
     * @param url
     * @param imageView
     */
    void loadAsGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);

    /**
     * Load picture list picture
     *
     * @param context
     * @param url
     * @param imageView
     */
    void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);
}
