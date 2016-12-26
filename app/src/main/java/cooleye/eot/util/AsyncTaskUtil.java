package cooleye.eot.util;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;


/**
 * 异步工具
 *
 * @author ycb
 * @date 2015-3-26
 */
public class AsyncTaskUtil<T> {

    private IAsyncTaskCallback<T> callback;

    public AsyncTaskUtil(IAsyncTaskCallback<T> asyncTaskCallback) {
        this.callback = asyncTaskCallback;
    }

    public interface IAsyncTaskCallback<T> {

        /**
         * 异步方法
         */
        public abstract T asyncMethod();

        /**
         * 同步方法
         *
         * @param result
         */
        public abstract void syncMethod(T result);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void start() {
        new AsyncTask<Void, Integer, T>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected T doInBackground(Void... params) {
                if (callback != null) {
                    return (T) callback.asyncMethod();
                }
                return null;
            }

            @Override
            protected void onPostExecute(T t) {
                if (callback != null) {
                    callback.syncMethod(t);
                }
            }
        }.execute();
    }
}
