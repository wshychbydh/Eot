package cooleye.eot.media.downloader;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import cooleye.eot.media.util.MediaUtils;


@TargetApi(Build.VERSION_CODES.FROYO)
public class ImageLoader {
    private ImageCache imageCache;
    // FixMe add new Field
    private int corner = -1;

    public ImageLoader() {
        this(-1);
    }

    public ImageLoader(int corner) {
        imageCache = new ImageCache();
        this.corner = corner;
    }


    /**
     * Download the specified image from the Internet and binds it to the
     * provided ImageView. The binding is immediate if the image is found in the
     * cache and will be done asynchronously otherwise. A null bitmap will be
     * associated to the ImageView if an error occurs.
     *
     * @param url       The URL of the image to load.
     * @param imageView The ImageView to bind the downloaded image to.
     */
    public void load(final ImageView imageView, String url) {
        imageCache.resetPurgeTimer();
        Bitmap bitmap = imageCache.getBitmapFromCache(url);
        if (bitmap == null) {
            new BitmapDownloaderTask(imageView, url)
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            if (corner != -1) {
                imageView.setImageBitmap(toRoundCorner(bitmap, corner));
            } else {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        if (bitmap == null) {
            return null;
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width != height) {
            int min = Math.min(width, height);
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, min, min);
        }

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getWidth());
        final RectF rectF = new RectF(rect);
        if (pixels < 8 || pixels > bitmap.getWidth()) {
            pixels = bitmap.getWidth() / 2;
        }
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private String mUrl;
        private WeakReference<ImageView> imageViewWeakReference;
        private Bitmap bitmap = null;

        public BitmapDownloaderTask(ImageView imageView, String url) {
            imageViewWeakReference = new WeakReference<>(imageView);
            this.mUrl = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return MediaUtils.getVideoFirstFrame(mUrl, 420, 420);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                return;
            }
            if (isCancelled()) {
                bitmap.recycle();
                return;
            }

            // add bitmap to cache
            imageCache.addBitmapToCache(mUrl, bitmap);

            if (imageViewWeakReference != null) {
                ImageView imageView = imageViewWeakReference.get();
                if (imageView != null) {
                    if (corner != -1) {
                        imageView.setImageBitmap(toRoundCorner(bitmap, corner));
                    } else {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }

        @Override
        protected void onCancelled() {
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
                return;
            }
            super.onCancelled();
        }
    }
}
