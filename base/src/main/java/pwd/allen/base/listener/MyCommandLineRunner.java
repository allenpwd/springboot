package pwd.allen.base.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 回调时机：容器启动后
 *
 * @author 门那粒沙
 * @create 2019-09-01 21:49
 **/
@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner.run..." + Arrays.asList(args));
    }
}
