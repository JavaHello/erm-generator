# erm-generator-diff-maven-plugin
此`maven`插件读取`.erm`文件生成表清单，和`sql`脚本.

## 支持功能
功能|描述
:---:|:---:
generate-excel|生成`excel`表清单
generate-markdown|生成`markdown`表清单
generate-mysql-ddl|生成`MySql`差异表清单

### generate-mysql-ddl 功能说明
此功能用于维护在日常开发中的表结构修改`DDL`，使用时配置两个版本的`.erm`文件，执行此插件生成两个版本的表结构差异`DDL`。

## 使用方式
### 配置`pom.xml`

增加 `maven plugin`
```xml
<plugin>
    <groupId>com.github.javahello</groupId>
    <artifactId>erm-generator-diff-maven-plugin</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <configuration>
        <!-- 生成文件输出目录 -->
        <outputDirectory>./.dev-out</outputDirectory>
        <!-- 数据库名 -->
        <dbName>demo</dbName>
        <!-- 当前erm文件 -->
        <newErmFiles>
            <ermFile>${basedir}/src/main/resources/erms/db.erm</ermFile>
        </newErmFiles>
        <!-- 上一版本erm文件 -->
        <oldErmFiles>
            <ermFile>${basedir}/src/main/resources/old_erms/db.erm</ermFile>
        </oldErmFiles>
    </configuration>
</plugin>
```


参考示例 [erm-generator-example](./erm-generator-example)