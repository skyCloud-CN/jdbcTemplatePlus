
# What is Jdbc-Template-Plus?(EarlyAccess)

JdbcTemplatePlus is an lightweight, fast, simple to use toolkit base on spring-jdbcTemplate.

This toolkit can simplify you DAO development and get rid of SQL, most importantly ,save your development time

# Features
- Basic CRUD with no code needed
- Dynamic SQL Support
- (Almost)No configuration needed
- SQL Injection defence
- Custom Plugin Support

# Getting started

- Add Maven dependency(NOT UPDATED)

```xml
<dependency>
  <groupId>io.github.skycloud</groupId>
  <artifactId>jdbc-template-plus</artifactId>
  <version>1.0.0</version>
</dependency>
```

- Define Model

```java
@Table(tableName = "user")
public class User {
    @PrimaryKey
    private Long id;

    private String name;

    private String text;
}
```

> if your table column is underscore  and  your model is in camelcase, you should set config like below
>
> ```java
> FastDaoConfig.setMapUnderscoreToCamelCase(true)
> ```
>

- Modify your Mapper/Dao/Storage by extends BaseStorage, BaseStorage provides plenty of useful method

```java
public class UserDAO extends BaseStorage<User,Long>{
  
}
```

- Use it

  - Query

    ```java
    List<User> user=userDao.selectByPrimaryKeys(1L,2L,3L)
    ```
  
  - Update/Insert
  
    ```java
    User user;
    userDao.updateByPrimaryKeySelective(user);
    userDao.insert(user);
    ```
    
  - Delete
  
    ```java
    userDao.deleteByPrimaryKey(1L)
    ```

- Dynamic Request

JdbcTemplatePlus support dynamic Sql request by powerful stream API, before use that you need to define a Helper class like below. and it is the only thing you need to do

```java
public class Columns {

    public static final Column ID = new Column("id");

    public static final Column NAME = new Column("name");

    public static final Column TEXT = new Column("text");
}
```

then you can write dynamic SQL like this

```java
List<String> names;
QueryRequest request = Request.queryRequest()
                .beginAndCondition()
  							// if names is empty or null, this condition will be ignored
                .andIgnoreIllegal(NAME.equal(names))
                .and(Condition.or()
                     .or(TEXT.like("new").matchLeft())
                     .or(TEXT.equal("None"))
                // if allowEmpty, when there is no condition, request will be translate to
                // SELECT * FROM `user`
                // or else ,request will return a Collections.emptyList();
                .allowEmpty()
                .endCondition();
userDao.select(request);
```





# License

JdbcTemplatePlus is under the Apache 2.0 license. See the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) file for details.
