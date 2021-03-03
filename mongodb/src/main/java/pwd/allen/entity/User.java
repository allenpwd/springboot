package pwd.allen.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author lenovo
 * @create 2021-03-03 14:39
 **/
@Document(collection = "user")
@Data
public class User {
    @Id
    private String id;
    private String name;
    private Integer age;
    private Date birthday;
}
