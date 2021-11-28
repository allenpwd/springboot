import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author 门那粒沙
 * @create 2021-03-28 16:40
 **/
public class PwdTest {

    /**
     */
    @Test
    public void date() throws ParseException {
        String dateStr = "2021-03-12 07:00:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        System.out.println(simpleDateFormat.parse(dateStr).getTime());

        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(simpleDateFormat.parse(dateStr).getTime());
    }

    @Test
    public void read() {
        List<Map> stages = JSONUtil.parseArray(FileUtil.readUtf8String("按月分组降序.json")).toList(Map.class);
        System.out.println(stages);
    }
}