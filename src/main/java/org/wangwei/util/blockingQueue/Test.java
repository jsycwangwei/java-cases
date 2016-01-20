package org.wangwei.util.blockingQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(100);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    queue.add("" + i);
                }
            }
        });

        executor.execute(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        System.out.println("---");
                        System.out.println(queue.take());
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        executor.shutdown();
    }
}
