package pwd.allen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *
 * @author 门那粒沙
 * @create 2019-08-18 22:26
 **/
@SpringBootApplication  // 会将主配置类所在的包当作base-package
public class AppMain {

    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
    }
}
