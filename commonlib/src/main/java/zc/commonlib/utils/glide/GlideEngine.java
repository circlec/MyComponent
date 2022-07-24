package zc.commonlib.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;

import zc.commonlib.R;

/**
 * @作者 zhouchao
 * @日期 2021/2/25
 * @描述
 */
public class GlideEngine implements ImageEngine {
    private static GlideEngine instance;

    private GlideEngine() {
    }

    public static GlideEngine createGlideEngine() {
        if (null == instance) {
            synchronized (GlideEngine.class) {
                if (null == instance) {
                    instance = new GlideEngine();
                }
            }
        }
        return instance;
    }

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.commonlib_image_placeholder)
                .error(R.drawable.commonlib_image_placeholder)
                .into(imageView);
    }

    public void loadImage(@NonNull Context context, @NonNull int resourId, @NonNull ImageView imageView) {
        Glide.with(context)
                .load(resourId)
                .placeholder(R.drawable.commonlib_image_placeholder)
                .error(R.drawable.commonlib_image_placeholder)
                .into(imageView);
    }

    public void loadImage(@NonNull Context context, @NonNull Bitmap bitmap, @NonNull ImageView imageView) {
        Glide.with(context)
                .load(bitmap)
                .placeholder(R.drawable.commonlib_image_placeholder)
                .error(R.drawable.commonlib_image_placeholder)
                .into(imageView);
    }

    /**
     * 加载相册目录
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadFolderImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .override(180, 180)
                .centerCrop()
                .sizeMultiplier(0.5f)
                .apply(new RequestOptions().placeholder(R.drawable.commonlib_image_placeholder))
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.
                                        create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(8);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    /**
     * 加载gif
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadAsGifImage(@NonNull Context context, @NonNull String url,
                               @NonNull ImageView imageView) {
        Glide.with(context)
                .asGif()
                .load(url)
                .into(imageView);
    }

    /**
     * 加载图片列表图片
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context)
                .load(url)
                .override(200, 200)
                .centerCrop()
                .apply(new RequestOptions().placeholder(R.drawable.commonlib_image_placeholder))
                .into(imageView);
    }

    public void loadImage(@NonNull Context context, @NonNull String url,CustomTarget customTarget) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(customTarget);
    }
}
