package pwd.allen.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author 门那粒沙
 * @create 2021-03-27 14:55
 **/
@Document(collection = "order")
@Data
public class Order {
    @Id
    private String id;
    private String userId;
    private float price;
    private String userName;
    private String orderName;
    private Date createDate;
}
