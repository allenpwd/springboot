package pwd.allen.base.listener;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 回调时机：容器启动后
 *
 * @author 门那粒沙
 * @create 2019-09-01 21:47
 **/
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationRunner.run..." + args);
    }
}
