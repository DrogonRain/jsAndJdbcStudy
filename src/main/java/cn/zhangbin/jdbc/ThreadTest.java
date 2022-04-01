package cn.zhangbin.jdbc;

import java.util.concurrent.*;

public class ThreadTest<T> implements Callable<T> {

    private T data;

    public ThreadTest(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public T call() throws Exception {
        return this.data;
    }

    public static void main(String[] args) {
        // 创建线程池
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future<Integer> fu = threadPool.submit(new ThreadTest<Integer>(1));
        Future<String> fu2 = threadPool.submit(new ThreadTest<String>("demo1"));
        try {
            System.out.println("waiting thread to finish");
            System.out.println("fu.get() = " + fu.get());
            System.out.println("fu2.get() = " + fu2.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
