package pwd.allen;

import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lenovo
 * @create 2021-03-11 16:08
 **/
@Data
public class Pager<T> {

    private Map<String, String> parameters = new HashMap<>();
    private Pageable pageable;
    private List<T> list;
    private long total;
}
