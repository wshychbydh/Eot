package com.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by cool on 16-9-29.
 */

public class ThreadFactory {

    public static void main(String[] args){
        CountDownLatch downLatch = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(15, () -> System.out.println("end********************end"));
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i =0 ; i< 5 ; i ++){
            service.execute(new Thread1(cyclicBarrier));
            service.execute(new Thread2(cyclicBarrier));
            service.execute(new Thread3(cyclicBarrier));
        }
    }

    public static class Thread1 implements Runnable,Delayed {
        CyclicBarrier mDownLatch;

        public Thread1(CyclicBarrier downLatch) {
            mDownLatch = downLatch;
        }

        @Override
        public void run() {
            try {
                sleep(1000);
                System.out.println("-----Thread1-------->");
                mDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        @Override
        public long getDelay(TimeUnit timeUnit) {
            return 0;
        }

        @Override
        public int compareTo(Delayed delayed) {
            return 0;
        }
    }

    public static class Thread2 implements Runnable  {
        CyclicBarrier mDownLatch;

        public Thread2(CyclicBarrier downLatch) {
            mDownLatch = downLatch;
        }

        @Override
        public void run() {
            try {
                sleep(2000);
                System.out.println("-----Thread2-------->");
                mDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Thread3 implements Runnable {
        CyclicBarrier mDownLatch;

        public Thread3(CyclicBarrier downLatch) {
            mDownLatch = downLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println("-----Thread3-------->");
                System.out.println("-----Thread3---await----->");
                mDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

}
