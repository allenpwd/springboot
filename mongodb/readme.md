## Aggregation
### pipeline参数
#### $project
返回哪些字段,select,说它像select其实是不太准确的,因为aggregate是一个阶段性管道操作符\
,$project是取出哪些数据进入下一个阶段管道操作,真正的最终数据返回还是在group等操作中;
#### $match
放在group前相当于where使用,放在group后面相当于having使用
#### $sort
排序1升-1降 sort一般放在group后,也就是说得到结果后再排序,如果先排序再分组没什么意义; 
#### $limit
相当于limit m,不能设置偏移量
#### $skip
跳过第几个文档
#### $unwind
把文档中的数组元素打开,并形成多个文档,参考Example1
#### $group: { _id: <expression>, <field1>: { <accumulator1> : <expression1> }
注意所有字段名前面都要加$,否则mongodb就为以为不加$的是普通常量,其中accumulator又包括以下几个操作符
- $sum
- $avg
- $first
- $last
- $max
- $min
- $push
- $addToSet

如果group by null就是 count(*)的效果
 
#### $geoNear
取某一点的最近或最远,在LBS地理位置中有用
 
#### $out
把结果写进新的集合中。注意1,不能写进一个分片集合中。注意2,不能写进
#### $redact
字段所处的document结构的级别.
-       语法：{ $redact: <expression> }

$redact 跟$cond结合使用，并在$cond里面使用了if 、then、else表达式，if-else缺一不可，$redact还有三个重要的参数：
- $$DESCEND： 返回包含当前document级别的所有字段，并且会继续判字段包含内嵌文档，内嵌文档的字段也会去判断是否符合条件。
- $$PRUNE：返回不包含当前文档或者内嵌文档级别的所有字段，不会继续检测此级别的其他字段，即使这些字段的内嵌文档持有相同的访问级别。
- $$KEEP：返回包含当前文档或内嵌文档级别的所有字段，不再继续检测此级别的其他字段，即使这些字段的内嵌文档中持有不同的访问级别。

### options参数
#### explain
返回指定aggregate各个阶段管道的执行计划信息
#### allowDiskUse
每个阶段管道限制为100MB的内存，如果大于100MB的数据可以先写入临时文件。\
设置为true时，aggregate操作可时可以先将数据写入对应数据目录的子目录中的唯一并以_tmp结尾的文档中。