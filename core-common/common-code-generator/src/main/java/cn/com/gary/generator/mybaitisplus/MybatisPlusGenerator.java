package cn.com.gary.generator.mybaitisplus;

import cn.com.gary.generator.common.PropertyUtil;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static cn.com.gary.generator.common.PropertyUtil.getPropertyValue;

/**
 * @author luxinglin
 * AutoGenerator MyBatis-Plus 代码生成器
 */
@Slf4j
public class MybatisPlusGenerator {
    /**
     * 配置文件路径
     */
    static final String PROPERTIES_FILE_PATH = "classpath:generator.properties";

    /**
     * 生成器入口
     *
     * @param args
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {
        //读取model生成配置
        PropertyUtil.getProperties(PROPERTIES_FILE_PATH);

        //执行生成动作
        runGenerate();

        log.info("Mybatis-Plus 数据模型生成成功");
    }

    /**
     * 具体生成逻辑
     */
    private static void runGenerate() throws IOException {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //生成文件输出目录
        gc.setOutputDir(projectPath + "/src/main/java");
        //开发人员
        gc.setAuthor(System.getProperty("user.name"));
        //是否打开输出目录
        gc.setOpen(false);
        //service命名方式
        gc.setServiceName("%sService");
        //service impl命名方式
        gc.setServiceImplName("%sServiceImpl");
        //自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(getPropertyValue("jdbc.url"));
        dsc.setDriverName(getPropertyValue("jdbc.driver.classname"));
        dsc.setUsername(getPropertyValue("jdbc.username"));
        dsc.setPassword(getPropertyValue("jdbc.password"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //父包模块名
        //父包名。// 自定义包路径  如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent(getPropertyValue("base.package"));
        pc.setEntity("dao.entity");
        pc.setMapper("dao.mapper");
        pc.setXml("dao.mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        //设置控制器包名
        pc.setController("api");

        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //自定义继承的Entity类全称，带包名
        ///strategy.setSuperEntityClass("com.baomidou.ant.comment.BaseEntity");
        //【实体】是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(true);
        //生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        //自定义继承的Controller类全称，带包名
        ///strategy.setSuperControllerClass("com.baomidou.ant.comment.BaseController");
        //需要包含的表名，允许正则表达式
        ///strategy.setInclude(scanner("user"));
        String tables = getPropertyValue("table.names");
        strategy.setInclude(tables.split(","));
        //驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        //表前缀
        ///strategy.setTablePrefix(pc.getModuleName() + "tb_");
        strategy.setTablePrefix(getPropertyValue("table.prefix"));
        mpg.setStrategy(strategy);
        mpg.execute();
    }


}