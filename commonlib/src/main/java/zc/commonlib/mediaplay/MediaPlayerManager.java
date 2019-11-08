package zc.commonlib.mediaplay;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import zc.commonlib.BuildConfig;

/**
 * 音频播放管理器
 * <p>
 * 0. 播放、暂停、停止、释放
 * 1. 下载和播放过程更多的回调处理，支持Listener和广播接收者
 * 2. 下载使用FileDownloader，支持断点续传和文件完整性和一致性的检验
 * 3. 支持在线流播放，同步下载。
 */
public class MediaPlayerManager {
    public static final String AUDIO_DOWNLOAD_PREPARE = "com.audio.action.DOWNLOAD_PREPARE";
    public static final String AUDIO_DOWNLOAD_SUCCESS = "com.audio.action.DOWNLOAD_SUCCESS";
    public static final String AUDIO_DOWNLOADING = "com.audio.action.DOWNLOADING";
    public static final String AUDIO_DOWNLOAD_CANCEL = "com.audio.action.DOWNLOAD_CANCEL";
    public static final String AUDIO_DOWNLOAD_FAILED = "com.audio.action.DOWNLOAD_FAILED";

    public static final String AUDIO_PREPARE = "com.audio.action.PREPARE";
    public static final String AUDIO_START = "com.audio.action.START";
    public static final String AUDIO_PROGRESS = "com.audio.action.PROGRESS";
    public static final String AUDIO_PAUSE = "com.audio.action.PAUSE";
    public static final String AUDIO_ERROR = "com.audio.action.ERROR";
    public static final String AUDIO_STOP = "com.audio.action.STOP";
    public static final String AUDIO_COMPLETE = "com.audio.action.COMPLETE";
    public static final String AUDIO_RELEASE = "com.audio.action.RELEASE";

    private static final String TAG = MediaPlayerManager.class.getSimpleName();

    public static final String AUDIO_DIR = "offline" + File.separator + "music";

    private static Context sContext;
    private MediaPlayer player;
    private String mAudioUrl = "";
    private int progress = 0;
    private boolean mUntilDownloaded = false;
    private PlayingStatus playStatus = PlayingStatus.DEFAULT;
    private DownLoadStatus downloadStatus = DownLoadStatus.DEFAULT;
    private MediaPlayerListener mMediaPlayerListener;
    private MediaDownLoadListener mMediaDownLoadListener;
    private ProgressHandler mProgressHandler;
    private RefreshRunnable mRefreshRunnable;
    private AudioDownloadListener mDownloadListener;
    private BaseDownloadTask mAudioDownloadTask;

    private MediaPlayerManager() {
        mProgressHandler = new ProgressHandler(this);
        mRefreshRunnable = new RefreshRunnable(this);
        initFileDownload();
    }

    public static MediaPlayerManager getInstance(Context context) {
        sContext = context.getApplicationContext();
        return SingletonInstance.INSTANCE;
    }

    private void initFileDownload() {
        FileDownloader.setup(sContext);
        FileDownloader.setGlobalPost2UIInterval(100);
        FileDownloader.setGlobalHandleSubPackageSize(1);
        FileDownloader.getImpl().pauseAll();
    }

    /**
     * 点击播放音频，下载完成后播放
     *
     * @param audioUrl
     */
    public void clickStartPlay(String audioUrl) {
        clickStartPlay(audioUrl, false, true);
    }

    /**
     * 点击播放音频
     *
     * @param audioUrl 音频的资源Url
     * @param isStream 是否使用在线播放
     * @param isPause  是否暂停正在播放的音频
     */
    public void clickStartPlay(String audioUrl, boolean isStream, boolean isPause) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "mAudioUrl:" + mAudioUrl + "  audioUrl:" + audioUrl + "   isStream:" + isStream + "   isPause:" + isPause);
        if (!mAudioUrl.equals(audioUrl)) {
            releaseMediaPlayer();
            startNetAudioPlay(audioUrl, true, !isStream);
        } else if (downloadStatus == DownLoadStatus.CANCEL || downloadStatus == DownLoadStatus.FAILED) {
            releaseMediaPlayer();
            startNetAudioPlay(audioUrl, true, !isStream);
        } else if (player != null && playStatus == PlayingStatus.PAUSE) {
            startPlay();
        } else if (player != null && playStatus == PlayingStatus.START && isPause) {
            pausePlay();
        }
    }

    /**
     * 播放在线资源，会在音频下载完成后播放
     *
     * @param audioUrl
     */
    public void startNetAudioPlay(String audioUrl) {
        startNetAudioPlay(audioUrl, true, true);
    }

    /**
     * 播放在线资源
     * <p>
     * 如果不存在本地文件，
     * download 为 true，会下载音频，false 不会下载
     * <p>
     * untilDownloaded
     * true，会下载音频，并且下载完成后再播放
     * false，会判断本地缓存音频是否存在，如果存在就会使用本地音频播放，不存在就在线播放
     *
     * @param audioUrl        资源url
     * @param downloadFile    是否下载
     * @param untilDownloaded 是否下载之后再播放 if(untilDownloaded) , file download  complete then play audio
     */
    public void startNetAudioPlay(String audioUrl, boolean downloadFile, boolean untilDownloaded) {
        if (TextUtils.isEmpty(audioUrl)) {
            return;
        }
        mAudioUrl = audioUrl;
        mUntilDownloaded = untilDownloaded;
        if (downloadFile || untilDownloaded) {
            mDownloadListener = new AudioDownloadListener();
            File audioFile = generateAudioFile(audioUrl);
            mAudioDownloadTask = FileDownloader.getImpl()
                    .create(audioUrl)
                    .setListener(mDownloadListener)
                    .setPath(audioFile.getPath())
                    .setAutoRetryTimes(1)
                    .setCallbackProgressMinInterval(1000);
            mAudioDownloadTask.start();
        }
        if (!untilDownloaded) {
            File audioFile = generateAudioFile(audioUrl);
            if (audioFile.exists() && audioFile.length() > 0) {
                startNativeAudioPlayer(audioFile.getAbsolutePath(), false);
            } else {
                startNativeAudioPlayer(audioUrl, true);
            }
        }
    }

    /**
     * 播放音频
     *
     * @param audioPath 音频文件的url，http://xx.mp3 或者 本地路径/~~/~~/xx,mp3
     * @param isStream  是否为在线播放的 URL，存入错误会造成卡顿
     */
    public void startNativeAudioPlayer(String audioPath, boolean isStream) {
        if (player != null)
            player.reset();
        try {
            player = null;
            player = new MediaPlayer();
            player.setDataSource(sContext, Uri.parse(audioPath));
            if (isStream) {
                player.prepareAsync();
            } else {
                player.prepare();
            }
        } catch (IOException e) {
            e.printStackTrace();
            onAudioError();
        }
        if (player == null) {
            onAudioError();
            return;
        } else if (isStream) {
            onAudioPrepare();
        }
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return onAudioError();
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                 onAudioCompleted();
            }
        });
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                startPlay();
            }
        });

    }

    /**
     * 播放（继续播放）音频
     */
    public void startPlay() {
        if (player != null) {
            player.start();
            onAudioStart();
        }
    }


    /**
     * 暂停音频播放
     */
    public void pausePlay() {
        if (isPlaying()) {
            player.pause();
            onAudioPause();
        }
    }

    /**
     * 是否正在播放
     *
     * @return player != null && player.isPlaying()
     */
    public boolean isPlaying() {
        return player != null && player.isPlaying();
    }

    /**
     * 暂停播放音频，暂停下载
     */
    public void pauseMediaPlayer() {
        if (player != null && player.isPlaying()) {
            pausePlay();
        } else {
            cancelDownLoad();
        }
    }

    /**
     * 停止播放音频
     */
    public void stopMediaPlayer() {
        if (player != null && player.isPlaying()) {
            player.stop();
            onAudioStop();
        }
    }

    /**
     * 释放播放器，在onDestroy 或者 onDestroyView中调用
     */
    public void releaseMediaPlayer() {
        cancelDownLoad();
        if (player != null) {
            player.stop();
            player.reset();
            player.release();
            player = null;
        }
        onAudioRelease();
    }

    /**
     * 停止下载（暂停）
     */
    private void cancelDownLoad() {
        if (downloadStatus == DownLoadStatus.PREPARE || downloadStatus == DownLoadStatus.DOWNLOADING) {
            if (mAudioDownloadTask != null)
                mAudioDownloadTask.pause();
            mDownloadListener = null;//置空Listener，防止pause调用在pending回调之前出现异常情况
            onDownloadCancel();
        }
    }

    public int getCurrentPosition() {
        int currentPosition = 0;
        if (player != null) {
            currentPosition = player.getCurrentPosition();
        }
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        if (player != null ) {
            player.seekTo(currentPosition);
        }
    }

    public int getDuration() {
        int duration = 0;
        if (player != null) {
            duration = player.getDuration();
        }
        return duration;
    }

    public boolean checkPlayerNull() {
        return player == null;
    }

    /**
     * 播放进度，百分比
     *
     * @return
     */
    public int getProgress() {
        return progress;
    }

    /**
     * 播放状态 {@link PlayingStatus}
     *
     * @return
     */
    public PlayingStatus getPlayingStatus() {
        return playStatus;
    }

    /**
     * 下载状态 {@link DownLoadStatus}
     *
     * @return
     */
    public DownLoadStatus getDownLoadStatus() {
        return downloadStatus;
    }

    public String getPlayingAudioUrl() {
        return mAudioUrl;
    }

    private File generateAudioFile(String audioUrl) {
        //todo 动态判断文件类型
        //命名1 url md5
//        String fileName = Md5Util.getStringMD5(audioUrl) + audioUrl.substring(audioUrl.lastIndexOf("."));
        //命名2 截取资源名
        String fileName = audioUrl.substring(audioUrl.lastIndexOf("/") + 1);
        File file = newFile(AUDIO_DIR, fileName);
        return file;
    }

    private  File newFile(String dirName, String fileName) {
        if (sdCardAvailable()) {
            return newFile(true, dirName, fileName, true);
        } else {
            return newFile(false, dirName, fileName, true);
        }
    }

    private  File newFile(boolean inSDCard, String dirName,
                               String fileName, boolean createDirIfNeeded) {
        return new File(newDir(inSDCard, dirName, createDirIfNeeded), fileName);
    }


    private  boolean sdCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    private  File newDir(boolean inSDCard, String dirName,
                              boolean createDirIfNeeded) {
        File dir = null;
        if (inSDCard) {
            dir = new File(sContext.getExternalFilesDir(null), dirName);
        } else {
            dir = new File(sContext.getFilesDir(), dirName);
        }

        if (createDirIfNeeded && !dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    private void onDownloadCancel() {
        downloadStatus = DownLoadStatus.CANCEL;
        if (mMediaDownLoadListener != null)
            mMediaDownLoadListener.onCancel();
        Intent intent = new Intent(AUDIO_DOWNLOAD_CANCEL);
        sContext.sendBroadcast(intent);
    }

    private boolean onAudioError() {
        playStatus = PlayingStatus.ERROR;
        if (mMediaPlayerListener != null)
            mMediaPlayerListener.onError();
        Intent intent = new Intent(AUDIO_ERROR);
        sContext.sendBroadcast(intent);
        return true;
    }

    private void onAudioPrepare() {
        playStatus = PlayingStatus.PREPARE;
        if (mMediaPlayerListener != null)
            mMediaPlayerListener.onPrepare();
        Intent intent = new Intent(AUDIO_PREPARE);
        sContext.sendBroadcast(intent);
    }

    private void onAudioStart() {
        playStatus = PlayingStatus.START;
        if (mMediaPlayerListener != null)
            mMediaPlayerListener.onStart();
        Intent intent = new Intent(AUDIO_START);
        sContext.sendBroadcast(intent);
        mProgressHandler.removeCallbacks(mRefreshRunnable);
        mProgressHandler.post(mRefreshRunnable);
    }

    private void onAudioPause() {
        playStatus = PlayingStatus.PAUSE;
        if (mMediaPlayerListener != null)
            mMediaPlayerListener.onPause();
        Intent intent = new Intent(AUDIO_PAUSE);
        sContext.sendBroadcast(intent);
        mProgressHandler.removeCallbacks(mRefreshRunnable);
    }

    private void onAudioStop() {
        playStatus = PlayingStatus.STOP;
        if (mMediaPlayerListener != null)
            mMediaPlayerListener.onStop();
        Intent intent = new Intent(AUDIO_STOP);
        sContext.sendBroadcast(intent);
        mProgressHandler.removeCallbacks(mRefreshRunnable);
    }

    private void onAudioCompleted() {
        playStatus = PlayingStatus.COMPLETE;
        mAudioUrl = "";
        if (mMediaPlayerListener != null)
            mMediaPlayerListener.onCompletion();
        Intent intent = new Intent(AUDIO_COMPLETE);
        sContext.sendBroadcast(intent);
        mProgressHandler.removeCallbacks(mRefreshRunnable);
    }

    private void onAudioRelease() {
        playStatus = PlayingStatus.RELEASE;
        mAudioUrl = "";
        if (mMediaPlayerListener != null) {
            mMediaPlayerListener.onRelease();
        }
        Intent intent = new Intent(AUDIO_RELEASE);
        sContext.sendBroadcast(intent);
        mProgressHandler.removeCallbacks(mRefreshRunnable);
    }

    public void setMediaPlayerListener(MediaPlayerListener playerListener) {
        this.mMediaPlayerListener = playerListener;
    }

    public void setMediaDownloadListener(MediaDownLoadListener downloadListener) {
        this.mMediaDownLoadListener = downloadListener;
    }


    public enum PlayingStatus {
        DEFAULT, PREPARE, ERROR, START, PAUSE, STOP, COMPLETE, RELEASE
    }

    public enum DownLoadStatus {
        DEFAULT, PREPARE, DOWNLOADING, SUCCEED, FAILED, CANCEL
    }


    public interface MediaPlayerListener {

        void onPrepare();

        void onStart();

        void onPause();

        void onProgress(long currentPosition, long totalDuration);

        void onStop();

        void onCompletion();

        void onRelease();

        void onError();


    }

    public interface MediaDownLoadListener {

        void onPrepare();

        void isDownLoading();

        void onProgress(int soFarBytes, int totalBytes);

        void onSuccess();

        void onFailed();

        void onCancel();
    }

    private static class SingletonInstance {
        private static final MediaPlayerManager INSTANCE = new MediaPlayerManager();
    }

    private static class ProgressHandler extends Handler {
        private WeakReference<MediaPlayerManager> activityWeakReference;

        public ProgressHandler(MediaPlayerManager playerManager) {
            activityWeakReference = new WeakReference<MediaPlayerManager>(playerManager);
        }

        @Override
        public void handleMessage(Message msg) {
            MediaPlayerManager mediaPlayerManager = activityWeakReference.get();
            if (mediaPlayerManager != null && mediaPlayerManager.player != null) {
                if (mediaPlayerManager.getCurrentPosition() == mediaPlayerManager.player.getDuration())
                    mediaPlayerManager.mProgressHandler.removeCallbacks(mediaPlayerManager.mRefreshRunnable);
                if (mediaPlayerManager.mMediaPlayerListener != null)
                    mediaPlayerManager.mMediaPlayerListener.onProgress(
                            mediaPlayerManager.getCurrentPosition(), mediaPlayerManager.player.getDuration());
                Intent intent = new Intent(AUDIO_PROGRESS);
                sContext.sendBroadcast(intent);
                mediaPlayerManager.progress = (mediaPlayerManager.getCurrentPosition() * 100) / mediaPlayerManager.player.getDuration();
            }
        }
    }

    private static class RefreshRunnable implements Runnable {
        private WeakReference<MediaPlayerManager> activityWeakReference;

        public RefreshRunnable(MediaPlayerManager activity) {
            activityWeakReference = new WeakReference<MediaPlayerManager>(activity);
        }

        @Override
        public void run() {
            MediaPlayerManager activity = activityWeakReference.get();
            if (activity != null) {
                activity.mProgressHandler.sendEmptyMessage(0);
                activity.mProgressHandler.postDelayed(this, 1000);
            }
        }
    }

    private class AudioDownloadListener extends FileDownloadSampleListener {

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.pending(task, soFarBytes, totalBytes);
            if (task.getListener() != mDownloadListener || !task.getUrl().equals(mAudioUrl) || !mUntilDownloaded)
                return;
            if (mMediaDownLoadListener != null)
                mMediaDownLoadListener.onPrepare();
            downloadStatus = DownLoadStatus.PREPARE;
            Intent intent = new Intent(AUDIO_DOWNLOAD_PREPARE);
            sContext.sendBroadcast(intent);
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.paused(task, soFarBytes, totalBytes);
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.progress(task, soFarBytes, totalBytes);
            if (task.getListener() != mDownloadListener || !task.getUrl().equals(mAudioUrl) || !mUntilDownloaded)
                return;
            downloadStatus = DownLoadStatus.DOWNLOADING;
            //todo progress data
            Intent intent = new Intent(AUDIO_DOWNLOADING);
            sContext.sendBroadcast(intent);
            if (mMediaDownLoadListener != null) {
                mMediaDownLoadListener.isDownLoading();
                mMediaDownLoadListener.onProgress(soFarBytes, totalBytes);
            }

        }

        @Override
        protected void completed(BaseDownloadTask task) {
            super.completed(task);
            if (task.getListener() != mDownloadListener || !task.getUrl().equals(mAudioUrl) || !mUntilDownloaded)
                return;
            downloadStatus = DownLoadStatus.SUCCEED;
            Intent intent = new Intent(AUDIO_DOWNLOAD_SUCCESS);
            sContext.sendBroadcast(intent);
            if (mMediaDownLoadListener != null) mMediaDownLoadListener.onSuccess();
            startNativeAudioPlayer(generateAudioFile(mAudioUrl).getAbsolutePath(), false);
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            super.error(task, e);
            if (task.getListener() != mDownloadListener || !task.getUrl().equals(mAudioUrl) || !mUntilDownloaded)
                return;
            downloadStatus = DownLoadStatus.FAILED;
            Intent intent = new Intent(AUDIO_DOWNLOAD_FAILED);
            sContext.sendBroadcast(intent);
            if (mMediaDownLoadListener != null) mMediaDownLoadListener.onFailed();
            mAudioUrl = "";
        }
    }

}
