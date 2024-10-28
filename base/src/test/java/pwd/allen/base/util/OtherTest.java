package pwd.allen.base.util;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

/**
 * @author pwdan
 * @create 2024-10-28 15:25
 **/
public class OtherTest {

    /**
     * 根据文件后缀获取Content-Type
     * 映射信息在 spring-web-5.3.9.jar!\org\springframework\http\mime.types
     */
    @Test
    public void test() {
        String key = "ahdslaf.jpg"; // image/jpeg
        key = "test.doc";   // application/msword
        key = "d:/test.xls";   // application/vnd.ms-excel
        key = "d:/test.xlsx";   // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
        System.out.println(MediaTypeFactory.getMediaType(key).orElse(MediaType.APPLICATION_OCTET_STREAM).toString());
    }
}
