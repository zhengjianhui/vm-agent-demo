package con.zjh.demo.agent;

import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.util.Optional;

/**
 * @author zhengjianhui
 * @Created Date: 10/27/19
 * @Description:
 * @Modify by:
 */
public class AgentMain {

    public static void main(String[] args) {

        //指定jar路径
        String agentFilePath = "/Users/zhengjianhui/Documents/vm-agent-demo/vm-agent/target/agentDemo.jar";

        //需要attach的进程标识
        String applicationName = "AgentMainRun";

        //查到需要监控的进程
        Optional<String> jvmProcessOpt = Optional.ofNullable(VirtualMachine.list()
                .stream()
                .filter(jvm -> {
                    System.out.println(jvm.displayName());
                    return jvm.displayName().contains(applicationName);
                })
                .findFirst().get().id());

        if (!jvmProcessOpt.isPresent()) {
            return;
        }

        File agentFile = new File(agentFilePath);
        try {
            String jvmPid = jvmProcessOpt.get();
            System.out.println("Attaching to target JVM with PID: " + jvmPid);
            VirtualMachine jvm = VirtualMachine.attach(jvmPid);
            jvm.loadAgent(agentFile.getAbsolutePath());
            jvm.detach();
            System.out.println("Attached to target JVM and loaded Java agent successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
