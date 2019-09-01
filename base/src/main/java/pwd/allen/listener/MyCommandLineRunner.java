package pwd.allen.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
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
