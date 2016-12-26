package cooleye.eot.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author ycb
 * @date 2014-9-23
 * @category 线程启动
 */

public class ThreadUtil {
	public static final ExecutorService THREAD_POOL_IMG = Executors
			.newFixedThreadPool(12);
	public static final ExecutorService THREAD_POOL = Executors
			.newFixedThreadPool(12);

	public static synchronized void addRunnable(final Runnable task) {
		if (task != null && !THREAD_POOL.isShutdown()) {
			THREAD_POOL.execute(task);
		}
	}

	public static void shutdown() {
		THREAD_POOL.shutdownNow();
		THREAD_POOL_IMG.shutdownNow();
	}
}
