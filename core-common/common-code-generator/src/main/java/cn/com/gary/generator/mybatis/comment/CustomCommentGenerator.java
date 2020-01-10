package cn.com.gary.generator.mybatis.comment;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * Mybatis 注释生成辅助类
 *
 * @author luxinglin
 * @since 2018-08-05
 */
public class CustomCommentGenerator extends DefaultCommentGenerator implements CommentGenerator {
    /**
     * 方法签名与方法描述映射
     */
    static Map<String, String> METHOD_DESC_MAP;

    static {
        METHOD_DESC_MAP = new HashMap<>();
        METHOD_DESC_MAP.put("countByExample", "按条件统计记录条数");
        METHOD_DESC_MAP.put("deleteByExample", "按条件删除记录");
        METHOD_DESC_MAP.put("deleteByPrimaryKey", "按主键删除记录");
        METHOD_DESC_MAP.put("insert", "全字段插入记录");
        METHOD_DESC_MAP.put("insertSelective", "按非空字段插入记录");
        METHOD_DESC_MAP.put("selectByExample", "按条件查询记录列表");
        METHOD_DESC_MAP.put("selectByPrimaryKey", "按主键查询记录");
        METHOD_DESC_MAP.put("updateByExampleSelective", "按条件更新记录非空字段");
        METHOD_DESC_MAP.put("updateByExample", "按条件更新记录所有字段");
        METHOD_DESC_MAP.put("updateByPrimaryKeySelective", "按主键更新记录非空字段");
        METHOD_DESC_MAP.put("updateByPrimaryKey", "按主键更新记录所有字段");
    }

    private Properties properties;
    private Properties systemPro;
    private boolean suppressDate;
    private boolean suppressAllComments;
    private String currentDateStr;

    public CustomCommentGenerator() {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        currentDateStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        // add no file level comments by default
        StringBuilder sb = new StringBuilder();
        sb.append("/**").append(System.lineSeparator());
        sb.append(" * ").append(compilationUnit.getType().getFullyQualifiedName()).append(System.lineSeparator());
        sb.append(" */");
        compilationUnit.addFileCommentLine(sb.toString());

        return;
    }

    /**
     * Adds a suitable comment to warn users that the element was generated, and
     * when it was generated.
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        super.addComment(xmlElement);
    }

    @Override
    public void addRootComment(XmlElement rootElement) {
        // add no document level comments by default
        return;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }


    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        addClassComment(innerClass, introspectedTable, false);
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());
        innerEnum.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        addFieldComment(field, introspectedTable, null);
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        if (introspectedColumn == null) {
            return;
        }

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        field.addJavaDocLine("/**");
        sb1.append(" * 字段名：").append(introspectedColumn.getActualColumnName());
        field.addJavaDocLine(sb1.toString());

        sb2.append(" * 字段备注：").append(introspectedColumn.getRemarks());
        field.addJavaDocLine(sb2.toString());
        field.addJavaDocLine(" */");
    }


    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine("/**");
        addJavadocTag(method, true);
        method.addJavaDocLine(" */");
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine("/**");

        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());

        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(introspectedColumn.getJavaProperty());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" */");
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());

        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());

        method.addJavaDocLine(" */");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append(" ");
        innerClass.addJavaDocLine(sb.toString());
        innerClass.addJavaDocLine(" * ");

        StringBuilder sb2 = new StringBuilder();
        sb2.append(" * @author ");
        sb2.append(systemPro.getProperty("user.name"));
        innerClass.addJavaDocLine(sb2.toString());

        StringBuilder sb3 = new StringBuilder();
        sb3.append(" * @since ");
        sb3.append(currentDateStr);
        innerClass.addJavaDocLine(sb3.toString());

        innerClass.addJavaDocLine(" */");
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do
     * not wish to include the Javadoc tag - however, if you do not include the
     * Javadoc tag then the Java merge capability of the eclipse plugin will
     * break.
     *
     * @param javaElement the java element
     */
    @Override
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        //参数
        if (javaElement instanceof Method) {
            String description = METHOD_DESC_MAP.get(((Method) javaElement).getName());
            if (description != null) {
                javaElement.addJavaDocLine(" * ".concat(description));
            } else {
                javaElement.addJavaDocLine(" * 请补充描述");
            }
            javaElement.addJavaDocLine(" * ");

            List<Parameter> params = ((Method) javaElement).getParameters();
            params.forEach(param -> {
                String line = " * @param ".concat(param.getName());
                javaElement.addJavaDocLine(line);
            });

            //返回值
            FullyQualifiedJavaType returnType = ((Method) javaElement).getReturnType();
            if (returnType != null) {
                javaElement.addJavaDocLine(" * @return ".concat(returnType.getShortName()));
            } else {
                javaElement.addJavaDocLine(" * @return void");
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" !!! do_not_modify_during_merge !!! ");
        }
        javaElement.addJavaDocLine(sb.toString());

        String s = getDateString();
        if (s != null) {
            javaElement.addJavaDocLine(" * @since ".concat(s));
        }
    }

    /**
     * This method returns a formated date string to include in the Javadoc tag
     * and XML comments. You may return null if you do not want the date in
     * these documentation elements.
     *
     * @return a string representing the current timestamp, or null
     */
    @Override
    protected String getDateString() {
        String result = null;
        if (!suppressDate) {
            result = currentDateStr;
        }
        return result;
    }
}