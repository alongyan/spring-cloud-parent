package com.george.generator;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  mybatis-plus生成基础代码
 * </p>
 *
 * @author GeorgeChan 2019/9/15 13:33
 * @version 1.0
 * @since jdk1.8
 */
public class MyBatisPlusGenerator {
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 生成文件放置的文件夹目录
        String targetPath = "D:\\test";

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(targetPath);//这里写你自己的java目录
        gc.setFileOverride(true);//是否覆盖
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor("George Chan");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
//        dsc.setTypeConvert(new MySqlTypeConvert() {
//            // 自定义数据库表字段类型转换【可选】
//            @Override
//            public DbColumnType processTypeConvert(String fieldType) {
//                return super.processTypeConvert(fieldType);
//            }
//        });
        // 数据库连接
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/spring_cloud_parent?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC");
        mpg.setDataSource(dsc);
        // 包名
        String pkg = "com.george.{}";
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名前缀
        strategy.setTablePrefix(new String[]{"lay_", "es_","sys_"});//
        // 具体要生成文件的表
        strategy.setInclude(new String[]{"sys_resources","sys_role","sys_role_resources","sys_user","sys_user_role"});
        // 逻辑删除字段
        strategy.setLogicDeleteFieldName("delete_flag");
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(null);
        pc.setEntity(StrUtil.format(pkg, "model.entity"));
        pc.setMapper(StrUtil.format(pkg, "dao"));
        pc.setXml(StrUtil.format(pkg, "mapper"));
        pc.setService(StrUtil.format(pkg, "service"));
        pc.setServiceImpl(StrUtil.format(pkg, "service.impl"));
        pc.setController(StrUtil.format(pkg, "controller"));
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        mpg.setCfg(cfg);

        // 执行生成
        mpg.execute();

        // 打印注入设置
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }
}
