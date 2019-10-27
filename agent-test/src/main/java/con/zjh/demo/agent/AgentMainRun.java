package con.zjh.demo.agent;

import java.util.concurrent.TimeUnit;

/**
 * @author zhengjianhui
 * @Created Date: 10/27/19
 * @Description:
 * @Modify by:
 */
public class AgentMainRun {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            System.out.println("========业务处理=========");
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
