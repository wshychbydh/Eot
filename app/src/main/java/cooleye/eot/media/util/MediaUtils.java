package cooleye.eot.media.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.blankj.utilcode.utils.ImageUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

/**
 * Created by cool on 16-9-18.
 */
public class MediaUtils {

    public static Bitmap getVideoFirstFrame(@NonNull String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }


    public static Bitmap getVideoFirstFrame(@NonNull String path, int reqWidth, int reqHeight) {
        Bitmap bitmap = getVideoFirstFrame(path);
        final int height = bitmap.getHeight();
        final int width = bitmap.getWidth();
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return ImageUtils.scaleImage(bitmap, width / inSampleSize, height / inSampleSize);
    }

    public static String formatSize(long size) {
        int kb = 1024;
        int mb = kb * kb;
        if (size >= mb) {
            return String.format("%.1fMB", (double) size / mb);
        } else if (size >= kb) {
            return String.format("%dKB", size / kb);
        } else {
            return String.format("%dB", size);
        }
    }

    public static String formatDuration(long duration) {
        int minute = 60 * 1000;
        int hour = 60 * minute;

        if (duration >= hour) {
            int th = (int) (duration / hour);
            int tm = (int) ((duration - th * hour) / minute);
            if (tm == 0) {
                return String.format("%d小时", th);
            } else {
                return String.format("%d时%d分", th, tm);
            }
        } else if (duration >= minute) {
            int tm = (int) (duration / minute);
            int ts = (int) ((duration - tm * minute) / 1000);
            if (ts == 0) {
                return String.format("%d分钟", tm);
            } else {
                return String.format("%d分%d秒", tm, ts);
            }
        } else {
            return String.format("%d秒", duration / 1000);
        }
    }

    public static void loadImage(@NonNull SimpleDraweeView draweeView, @NonNull String path) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(new File
                (path)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .setAutoPlayAnimations(true)
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        draweeView.setController(controller);
    }
}
