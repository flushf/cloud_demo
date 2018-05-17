package org.dec;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.dec.util.RequestMethod;
import org.dec.util.RequestTools;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author Decimon
 * @date 2018/4/28
 */
public class ThreadPoolTest {
    @Test
    public void test() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(RequestMethod.GET.toString().equals("GET"));
        });
        singleThreadPool.shutdown();
    }

    @Test
    public void testTask() throws ExecutionException, InterruptedException {
        final int a = 5, b = 4;
        FutureTask<Integer> task = new FutureTask<Integer>(() -> {
            int c = a + b;
            return c;
        });
        new Thread(task).start();
        Integer integer = task.get();
        System.out.println(integer);
    }
    /*
    ①sleep(long millis): 在指定的毫秒数内让当前正在执行的线程休眠（暂停执行）
    ②join():指等待线程终止。
    yield();暂停当前正在执行的线程对象，并执行其他线程
    * */

    @Test
    public void testThread() throws InterruptedException {
        final String name = "thread";
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
                for (int i = 0; i < 10; i++) {
                    try {
                        sleep((int) Math.random() * 10);
                        if (i == 1) {
                            this.join();
//                            Thread.yield();
                        }
                        System.out.println("子线程" + name + "运行 : " + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
            }

        };
        thread.setName(name);
        thread.start();
    }

}
