package org.wangwei.util.forkjoin;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 任务有返回值
 * 
 * @author wangwei-ww
 */
public class TestRecursiveTask {
    public final static ForkJoinPool mainPool = new ForkJoinPool();

    public static void main(String[] args) {
        int n = 26;
        int[] a = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            a[i] = rand.nextInt(1000);
        }
        SubTask0 task = new SubTask0(a, 0, n);
        int count = mainPool.invoke(task);
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println("\n数组中共出现了" + count + "个1");
    }
}

class SubTask0 extends RecursiveTask<Integer> {

    private static final long serialVersionUID = 1L;

    private int[] a;
    private int beg;
    private int end;

    public SubTask0(int[] a, int beg, int end) {
        super();
        this.a = a;
        this.beg = beg;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (end - beg > 1) {
            int mid = (beg + end) / 2;
            SubTask0 t1 = new SubTask0(a, beg, mid);
            SubTask0 t2 = new SubTask0(a, mid, end);
            invokeAll(t1, t2);
            try {
                result = t1.get() + t2.get();
            }
            catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        else {
            result = count(a[beg]);
        }
        return result;
    }

    // 统计一个整数中出现了几个1
    private int count(int n) {
        int result = 0;
        while (n > 0) {
            if (n % 10 == 1) {
                result++;
            }
            n = n / 10;
        }
        return result;
    }
}
