import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.util.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.util.DBObjectUtils;
import org.springframework.test.context.junit4.SpringRunner;
import pwd.allen.MongoMain;
import pwd.allen.entity.Order;
import pwd.allen.entity.User;
import pwd.allen.service.OrderService;

import java.util.*;

/**
 * @author 门那粒沙
 * @create 2021-03-27 14:58
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoMain.class)
public class OrderTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private OrderService orderService;

    @Test
    public void save() {
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setUserId("2");
        order.setUserName("three");
        for (int i = 0; i < 10; i++) {
            order.setPrice(new Random().nextFloat() * 10);
            order.setOrderName("order" + i);
            order.setId("three" + i);
            orderService.save(order);
        }

    }

    /**
     * 按照用户信息分组，统计用户单价大于2的订单
     * {
     *     "aggregate": "__collection__",
     *     "pipeline": [
     *         {
     *             "$match": {
     *                 "price": {
     *                     "$gte": 2
     *                 }
     *             }
     *         },
     *         {
     *             "$group": {
     *                 "_id": {
     *                     "userName": "$userName",
     *                     "userId": "$userId"
     *                 },
     *                 "orderNum": {
     *                     "$sum": 1
     *                 },
     *                 "priceArr": {
     *                     "$push": "$price"
     *                 },
     *                 "sumPrice": {
     *                     "$sum": "$price"
     *                 },
     *                 "avgPrice": {
     *                     "$avg": "$price"
     *                 }
     *             }
     *         },
     *         {
     *             "$sort": {
     *                 "_id.userName": 1
     *             }
     *         },
     *         {
     *             "$skip": {
     *                 "$numberLong": "0"
     *             }
     *         },
     *         {
     *             "$limit": {
     *                 "$numberLong": "10"
     *             }
     *         }
     *     ]
     * }
     */
    @Test
    public void tempAggr() {
        //<editor-fold desc="方法一">
        Criteria criteria = Criteria.where("price").gte(2);
        Sort sort = Sort.by(Sort.Direction.ASC, "userName");
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),//条件
                Aggregation.group("userName", "userId") //分组字段
                        .count().as("orderNum")     //统计订单数
                        .first("price").as("firstPrice")    //分组中第一个价格
                        .last("price").as("lastPrice")    //最后一个价格
                        .min("price").as("minPrice")    //最小价格
                        .addToSet("price").as("priceSet")    //每个分组中的price字段放到一个集合中，不重复
                        .push("price").as("priceArr") //每个分组中的price字段放到一个集合中
                        .sum("price").as("sumPrice")    //统计总价
                        .avg("price").as("avgPrice"),   //统计平均价
                Aggregation.sort(sort),//排序
                Aggregation.skip(0L),//过滤
                Aggregation.limit(10)//页数
        );
        // 解除mongodb 查询数据默认占用最大内存的(默认100M).不然会抛出异常:Exceeded memory limit for $group, but didn't allow external sort.
        agg.withOptions(Aggregation.newAggregationOptions().allowDiskUse(true).build());

        AggregationResults<Map> outputType = mongoTemplate.aggregate(agg, "order", Map.class);
        List<Map> list = outputType.getMappedResults();
        System.out.println(list);
        //</editor-fold>



        //<editor-fold desc="方法二">
        BasicDBObject matchObj = BasicDBObject.parse("{\n" +
                "    \"$match\": {\n" +
                "        \"price\": {\n" +
                "            \"$gte\": 2\n" +
                "        }\n" +
                "    }\n" +
                "}");
        BasicDBObject groupObj = BasicDBObject.parse("{\n" +
                "    \"$group\": {\n" +
                "        \"_id\": {\n" +
                "            \"userName\": \"$userName\",\n" +
                "            \"userId\": \"$userId\"\n" +
                "        },\n" +
                "        \"orderNum\": {\n" +
                "            \"$sum\": 1\n" +
                "        },\n" +
                "        \"firstPrice\": {\n" +
                "            \"$first\": \"$price\"\n" +
                "        },\n" +
                "        \"lastPrice\": {\n" +
                "            \"$last\": \"$price\"\n" +
                "        },\n" +
                "        \"priceArr\": {\n" +
                "            \"$push\": \"$price\"\n" +
                "        },\n" +
                "        \"sumPrice\": {\n" +
                "            \"$sum\": \"$price\"\n" +
                "        },\n" +
                "        \"avgPrice\": {\n" +
                "            \"$avg\": \"$price\"\n" +
                "        }\n" +
                "    }\n" +
                "}");
        BasicDBObject sortObj = BasicDBObject.parse("{\n" +
                "    \"$sort\": {\n" +
                "        \"_id.userName\": 1\n" +
                "    }\n" +
                "}");
        AggregateIterable<Map> result = mongoTemplate.getCollection("order").aggregate(Arrays.asList(matchObj, groupObj, sortObj), Map.class);
        for (Map map : result) {
            System.out.println(map);
        }
        result.toCollection();
        //</editor-fold>

    }
}
