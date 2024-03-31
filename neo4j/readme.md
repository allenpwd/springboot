### 官方文档
- https://neo4j.com/docs/
- spring-data-neo4j（6.1.3版本）：https://docs.spring.io/spring-data/neo4j/docs/6.1.3/reference/html/#getting-started
- cql语法：https://neo4j.com/docs/cypher-manual/4.2/clauses/


社区版只能单机使用，最多可以使用数十亿个节点、关系和属性。

- 如果节点存在关系，delete不了，MATCH (n) DETACH DELETE n


### 问题
##### The server does not support any of the protocol versions supported by this driver. Ensure that you are using driver and server versions that are compatible with one another.
引入的neo4j-java-driver的版本要和neo4j版本一致
4以上版本需要jdk11以上