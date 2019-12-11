package pwd.allen;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lenovo
 * @create 2019-12-11 15:17
 **/
public class Java8Test {

    public static List<Map<String,Object>> getData(){
        Map<String,Object> map1=new HashMap<>();
        map1.put("id","1");
        map1.put("name","小张");
        map1.put("age","10");
        map1.put("sex","男");
        Map<String,Object> map2=new HashMap<>();
        map2.put("id","2");
        map2.put("name","小李");
        map2.put("age","15");
        map2.put("sex","女");
        Map<String,Object> map3=new HashMap<>();
        map3.put("id","3");
        map3.put("name","小王");
        map3.put("age","30");
        map3.put("sex","男");
        List<Map<String,Object>> list=new ArrayList<>();
        list=  Arrays.asList(map1,map2,map3);
        return list;
    }

    @Test
    public void test() {
        List<Map<String, Object>> list = getData();
        System.out.println(list);
        List<Map<String, Object>> list2 = list.stream().filter(stu -> {
                    if (Integer.parseInt(stu.get("age").toString()) > 20) {
                        return true;
                    } else {
                        return false;
                    }
                }
        ).collect(Collectors.toList());
        System.out.println(list2);
    }
}
