package news.chen.yu.ionic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.OnBotEventListener;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.qiyukf.unicorn.api.YSFOptions;


public class Qiyukf extends CordovaPlugin {
    public static final String TAG = "cordova-plugin-qiyukf";
    private String appKey;
    private String appName;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        this.appKey = preferences.getString("app_key", "");
        this.appName = preferences.getString("app_name", "");
        Unicorn.init(cordova.getActivity(), this.appKey, options(), new UnicornImageLoader() {

            @Nullable
            @Override
            public Bitmap loadImageSync(String uri, int width, int height) {
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(false)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();

                // check cache
                boolean cached = true;
                ImageDownloader.Scheme scheme = ImageDownloader.Scheme.ofUri(uri);
                if (scheme == ImageDownloader.Scheme.HTTP
                        || scheme == ImageDownloader.Scheme.HTTPS
                        || scheme == ImageDownloader.Scheme.UNKNOWN) {
                    // non local resource
                    cached = MemoryCacheUtils.findCachedBitmapsForImageUri(uri, ImageLoader.getInstance().getMemoryCache()).size() > 0
                            || DiskCacheUtils.findInCache(uri, ImageLoader.getInstance().getDiskCache()) != null;
                }

                if (cached) {
                    ImageSize imageSize = (width > 0 && height > 0) ? new ImageSize(width, height) : null;
                    Bitmap bitmap = ImageLoader.getInstance().loadImageSync(uri, imageSize, options);
                    if (bitmap == null) {
                        Log.e(TAG, "load cached image failed, uri =" + uri);
                    }
                    return bitmap;
                }

                return null;
            }

            @Override
            public void loadImage(String uri, int width, int height, final ImageLoaderListener listener) {
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(false)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();
                ImageSize imageSize = (width > 0 && height > 0) ? new ImageSize(width, height) : null;

                ImageLoader.getInstance().loadImage(uri, imageSize, options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        if (listener != null) {
                            listener.onLoadComplete(loadedImage);
                        }
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        super.onLoadingFailed(imageUri, view, failReason);
                        if (listener != null) {
                            listener.onLoadFailed(failReason.getCause());
                        }
                    }
                });
            }
        });
    }

    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.onBotEventListener = new OnBotEventListener() {
            @Override
            public boolean onUrlClick(Context context, String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
                return true;
            }
        };
        return options;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("open")) {
            this.open();
            return true;
        } else {
            return false;
        }
    }

    private void open() {
        ConsultSource source =  new ConsultSource(null, this.appName, "");
        Unicorn.openServiceActivity(cordova.getActivity(), this.appName, source);
    }
}