package pwd.allen;

import org.junit.jupiter.api.RepeatedTest;

/**
 * @author lenovo
 * @create 2020-01-23 17:45
 **/
public class JunitTest {

    @RepeatedTest(5)
    public void repeat() {
        System.out.println("hello!");
    }
}
