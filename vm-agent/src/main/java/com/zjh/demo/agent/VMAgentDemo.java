package com.zjh.demo.agent;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

/**
 * @author zhengjianhui
 * @Created Date: 10/26/19
 * @Description:
 * @Modify by:
 */
public class VMAgentDemo {

    private final static ThreadMXBean THREAD_MX_BEAN = ManagementFactory.getThreadMXBean();

    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中
     */
    public static void premain(String agentArgs, Instrumentation inst) throws InterruptedException {
        System.out.println("=========premain方法执行1========");

        test();


    }

    private static void test() {
        Thread t = new Thread(() -> {
            while (true) {
                System.err.println("==========开始打印堆栈===========");
                ThreadInfo[] threadInfos = THREAD_MX_BEAN.dumpAllThreads(false, false);
                // ...
                for (ThreadInfo threadInfo : threadInfos) {
                    String threadName = threadInfo.getThreadName();
                    // ...
                    StackTraceElement[] stackTraceElements = threadInfo.getStackTrace();
                    // ...
                    for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                        StackTraceElement stackTraceElement = stackTraceElements[i];
                        System.out.println(stackTraceElement);
                    }
                }

                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        t.start();
    }

    /**
     * 如果不存在 premain(String agentArgs, Instrumentation inst)
     * 则会执行 premain(String agentArgs)
     */
    public static void premain(String agentArgs) {
        System.out.println("=========premain方法执行2========");
        System.out.println(agentArgs);
    }


    /**
     * vm 启动后增加代理, 调用的是这个方法
     * jattach pid load /absolute/path/to/agent/libagent.so true
     */
    public static void agentmain(String args, Instrumentation ins) {
        System.out.println("=========agentmain========");
        test();
    }


}
