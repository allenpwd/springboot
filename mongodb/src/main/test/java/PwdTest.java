import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
}