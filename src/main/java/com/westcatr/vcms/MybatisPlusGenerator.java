package com.westcatr.vcms;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.PostgreSqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

/**
 * mybatis-plus代码生成工具3.0版本 最新版 3.12
 * 详细配置：https://mp.baomidou.com/config/generator-config.html
 * 
 * @author xieshuang 若使用请在maven添加以下依赖 <dependency>
 *         <groupId>org.apache.velocity</groupId>
 *         <artifactId>velocity</artifactId> <version>RELEASE</version>
 *         </dependency>
 */
public class MybatisPlusGenerator {

    public static void main(String[] args) {

        // 模块名
        String module = "vcms";
        // 是否多模块项目
        boolean isModule = false;
        // 包路径
        String packAge = MybatisPlusGenerator.class.getPackage().getName();
        String package1 = packAge + "";
        // 作者
        String author = "admin";
        // 数据库用户名
        String username = "root";
        // 数据库密码
        String password = "Ls13548274447";
        // 数据库名
        String dataBase = "vcms_business";
        // 逻辑删除字段，不要为null或者空
        String logicDeleteFieldName = "deleted";
        // 需要生成的表，正则表达式匹配前缀
        String tableName = "";
        String tableNames="vc_table_cards_screen";
        String[] ExcludeTable = new String[] {};
        // 需要生成的表的前缀，生成后将不含前缀
        String[] tableQ = new String[] { "vc_" };

        // 是否是视图
        boolean isView = false;
        // 是否启用权限注解
        boolean enableIsecurity = true;
        // 是否启用保存日志注解
        boolean enableSaveLog = true;
        // 是否启用redis
        boolean enableRedis = true;
        // 是否在entity生成jsr303校验注解
        boolean enableValidator = true;
        // 是否生成 CRUD代码
        boolean enableCrud = true;
        // 是否使用 lombok
        boolean enableLombok = true;
        // 是否使用 Swagger
        boolean enableSwagger = true;
        // 时间类型对应策略
        DateType dateType = DateType.ONLY_DATE;
        String filePath;

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setIdType(IdType.ASSIGN_UUID);
        gc.setDateType(dateType);
        gc.setOpen(false);
        gc.setSwagger2(enableSwagger);
        String projectPath = System.getProperty("user.dir");
        if (isModule) {
            filePath = projectPath + "/" + module + "/src/main/java";
            gc.setOutputDir(projectPath + "/" + module + "/src/main/java");
        } else {
            filePath = projectPath + "/src/main/java";
            gc.setOutputDir(projectPath + "/src/main/java");
        }
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
        gc.setAuthor(author);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setSchemaName("public");
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setUrl("jdbc:mysql://localhost:3306/" + dataBase);
        /* if (isView) {
            dsc.setDbQuery(new PostgreSqlQuery() {

                @Override
                public String tableName() {
                    return "viewname";
                }

                @Override
                public String tablesSql() {
                    return "SELECT \n" + "\tA.viewname,\n"
                            + "\tobj_description ( relfilenode, 'pg_class' ) AS comments \n" + "FROM\n"
                            + "\tpg_views A,\n" + "\tpg_class B \n" + "WHERE\n" + "\tA.schemaname = '%s' \n"
                            + "\tAND A.viewname = B.relname";
                }
            });
        } */
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);全局大写命名 ORACLE 注意
        //strategy.setLikeTable(new LikeTable(tableName));
        strategy.setInclude(tableNames.split(","));
        strategy.setTablePrefix(tableQ);
        if (logicDeleteFieldName != null && !logicDeleteFieldName.equals("")) {
            strategy.setLogicDeleteFieldName(logicDeleteFieldName);
        }
        strategy.setEntityLombokModel(enableLombok);
        strategy.setRestControllerStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        if (ExcludeTable.length > 0) {
            // 排除生成的表
            strategy.setExclude(new String[] { "test" });
        }
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        mpg.setStrategy(strategy);
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("enableRedis", enableRedis);
                map.put("enableIsecurity", enableIsecurity);
                map.put("enableSaveLog", enableSaveLog);
                this.setMap(map);
            }
        };
        if (enableCrud) {
            List<FileOutConfig> focList = new ArrayList<>();
            // 自定义配置会被优先输出
            String templatePath = "/generator/query.java.vm";
            System.out.println(packToDir(package1));
            focList.add(new FileOutConfig(templatePath) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return filePath + packToDir(package1) + "/controller/query/" + tableInfo.getEntityName() + "Query"
                            + StringPool.DOT_JAVA;
                }
            });
            cfg.setFileOutConfigList(focList);
        }
        mpg.setCfg(cfg);
        TemplateConfig tc = new TemplateConfig();
        if (enableCrud) {
            tc.setController("/generator/controller.java.vm");
            tc.setService("/generator/service.java.vm");
            tc.setServiceImpl("/generator/serviceImpl.java.vm");
        }
        if (enableValidator) {
            tc.setEntity("/generator/entity.java.vm");
        }
        mpg.setTemplate(tc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(package1);
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);
        mpg.execute();
    }

    private static String packToDir(String s) {
        return "/" + String.join("/", Arrays.asList(s.split("\\.")));
    }
}
