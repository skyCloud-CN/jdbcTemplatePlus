#   Jdbc-Template-Plus

[![codebeat badge](https://codebeat.co/badges/b060749d-601c-4035-8f7e-ed17a3f39c39)](https://codebeat.co/projects/github-com-skycloud-cn-jdbctemplateplus-master)
[![Build Status](https://travis-ci.org/skyCloud-CN/jdbcTemplatePlus.svg?branch=master)](https://travis-ci.org/skyCloud-CN/jdbcTemplatePlus)

Spring based Persistence Framewok for Java Web developers, By using this, you Can

- **Never write Sql**
- **Develop Dao in 1 minute**
- **Write Dynamic Sql just like Stream Api**
- **Sharding,  LogicDelete and Other Functions only By One Annotation**



## Table Of Contents


- [Background](#background)
- [GettingStart](#GettingStart)
- [Usage](#usage)
- [License](#license)




## Background

Developing Persistence Layer is really **Boooooring** , If you are a Java web developer, then you will definitely agree with what I said.

I have tried a log Persisitence Frame work such as Mybatis, JdbcTemplate, SpringDataJPA. They are all Excellent Framewok, but still have some shortcomings.

Mybatis support dynamic Sql but need to write lots of xml files, which cost  lots of time.By using MybatisGenerator xml files can be generated but they are unmaintainable and it's annoying to have so many xml in Project

JdbcTemplate do lots of  low level job for you, so you can only  write  SQL, but I always get runtime error tell me SQL Syntax Error when developing because a missing "," or "`" and really hard to find where is wrong.

SpringDataJPA is my favourate framework. But it is too heavy and generated SQL is a little cumbersome(For a developer at Internet company, 99% of SQL is single table query ).  Dynamic sql is supported but readability is not so good

So this is why I wrote this framework



## GettingStart

**I will use a example of a user table to tell you how to get start with this framework**

model look like this

> @Data and @ToString annotation is from Lombok, they have nothing to do with this framework

```java
@Data
@ToString
public class User {
    private Long id;
    private String name;
    private Date updated;
    private Date created;
    private Boolean deleted;
}
```

### 1. Add Maven Dependency

> currently this project is not deployed  to central repo, but soon it will

```xml
<dependency>
  <groupId>io.github.skycloud-cn</groupId>
  <artifactId>jdbc-template-plus</artifactId>
  <version>1.0.0</version>
</dependency>
```

### 2. Add Annotation

add @Table and @PrimaryKey annotion like below

```java
@Data
@ToString
@Table(tableName = "user")
public class User {
    @PrimaryKey
    private Long id;
    private String name;
    private Date updated;
    private Date created;
    private Boolean deleted;
}
```

### 3. Define Helper Class

this class is for help you write sql condition,  **param in Column must be exactlly same with you column name in database**

```java
public class Columns {
    public static final Column ID = new Column("id");
    public static final Column NAME = new Column("name");
    public static final Column UPDATED = new Column("updated");
    public static final Column CREATED = new Column("created");
    public static final Column DELETED = new Column("deleted");
}
```

### 4. Define DAO

Basic Method is all in BaseStorage class so **No Code** is  need

```java
@Service
public class UserDAO extends BaseStorage<User, Long>{

}
```

### 5.DbAndModelNamingCheck(Optional)

This step is depends on your Java Model and database column name mapping, if your Java Model property is **exactly same** with database column name, this step is not need.

If your database column naming by **underScore** like `sky_cloud` and Java Model property is naming by **camelCase** like `skyCloud`, you need to call method below **before any SQL are executed**

```
FastDaoConfig.setMapUnderscoreToCamelCase(true)
```

Otherwise, you need to tell framework you Database column name by annotation like below

```java
@Data
@ToString
@Table(tableName = "user")
public class User {
    @PrimaryKey
    private Long id;
    private String name;
    @ColumnMap(jdbcType = JDBCType.TIMESTAMP)
    private Long updated;
    private Date created;
    @ColumnMap(column = "deleted")
    private Boolean exist;
}
```

> @ColumnMap used in exist field tells `exist` field should map to column `deleted`
>
> @ColumnMap used in updated field tells this field is actually `TIMESTAMP` type in database but not `BIGINT`

## Usage

### Method Supported by BaseStorage

```java

    DATA selectByPrimaryKey(PRIM_KEY key);

    List<DATA> selectByPrimaryKeys(Collection<PRIM_KEY> keys);

    List<DATA> selectByPrimaryKeys(PRIM_KEY... keys);

    int insert(DATA t);

    int insertSelective(DATA t);

    int updateByPrimaryKey(DATA t);
   
    int updateByPrimaryKeySelective(DATA t);

    int deleteByPrimaryKey(PRIM_KEY t);

    List<DATA> select(QueryRequest queryRequest);

    int count(CountRequest countRequest);

    int update(UpdateRequest updateRequest);

    int delete(DeleteRequest deleteRequest);

```



### Dynamic SQL API

here is a example for a query demand. I want to find User data created within a period of time, and also want to find user by name, these condition is optional, and I need all data are not logic deleted, data should be sort by Id, and with a page limit of 20.

```java
QueryRequest request=Request.queryRequest()
                .beginAndCondition()
                .andIgnoreIllegal(NAME.like(name).matchLeft().matchRight())
                .andIgnoreIllegal(CREATED.gt(dateBegin).lt(dateEnd))
                .and(DELETED.equal(false))
                .endCondition()
                .addSort(ID, OrderEnum.ASC)
                .limit(20)
                .offset(0);
dao.select(request);
```

### Plugins

Lots of useful functions is supported by plugins and can be active by **only one Annotation**

#### AutoFill

AutoFill is useful when you need to set `updated`,`created` field to db, by annotation `@AutoFill` these field can be autofilled when insert and updated if these property is null

```java
@AutoFill(fillValue = AutoFillValueEnum.NOW,onOperation = {AutoFillOperation.INSERT,AutoFillOperation.UPDATE})
private Long updated;
@AutoFill(fillValue = AutoFillValueEnum.NOW,onOperation= AutoFillOperation.INSERT)
private Date created;
```

#### LogicDelete

LogicDelete can automaticly  add condition `logicDelete=false` when query, update, count if there is no other condition about logicDelete property, and autoFill logicDeleteField to 0 or false when insert. 

currently only `Integer`, `Boolean`, `Long` are supported

```java
@LogicDelete
private Boolean deleted;
```

#### Sharding

execute `ShardUtil.setShardSuffixOnce(suffixHere)` before execute a SQL request, suffix will automaticly add to your table name in sql;

## License

JdbcTemplatePlus is under the Apache 2.0 license. See the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) file for details.
