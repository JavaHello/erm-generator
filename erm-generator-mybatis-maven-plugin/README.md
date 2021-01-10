# erm-generator-mybatis-maven-plugin
此`maven`插件基于`mybatis-generator`扩展，支持读取`.erm`文件生成 `Java Dao`层代码。

## 使用方式
### 配置`pom.xml`

增加`.erm`文件路径`ermPath`变量
```xml
<properties>
    <ermPath>${basedir}/src/main/resources/erms</ermPath>
</properties>
```

增加 `maven plugin`
```xml
<plugin>
    <groupId>com.github.javahello</groupId>
    <artifactId>erm-generator-mybatis-maven-plugin</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <configuration>
        <verbose>true</verbose>
        <overwrite>true</overwrite>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <phase>generate-sources</phase>
        </execution>
    </executions>
</plugin>
```

### 配置`generatorConfig.xml`文件
增加 `.erm`文件
```xml
<ermSources path="${ermPath}">
  <ermSource value="db.erm"/>
  <ermSource value="db2.erm"/>
</ermSources>
```

### 执行
执行 `mvn install` 生成`MyBatis`实体类和`mapper`文件，并编译打包。
```shell
mvn install
```

参考示例 [erm-generator-example](./erm-generator-example)