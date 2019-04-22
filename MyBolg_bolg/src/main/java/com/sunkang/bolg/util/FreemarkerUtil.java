package com.sunkang.bolg.util;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.sunkang.bolg.pojo.Article;

import org.springframework.boot.context.properties.ConfigurationProperties;

import freemarker.template.Configuration;
import freemarker.template.Template;
@ConfigurationProperties("freemarker.config")
public class FreemarkerUtil{

    private static String templateDiar;

    private static String templateFile;

    public  void createArticle(Article article) {
        // 1.创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2.设置模板所在的目录
        try {
            configuration.setDirectoryForTemplateLoading(new File(templateDiar));
            // 3.设置字符集
            configuration.setDefaultEncoding("utf-8");
            // 4.加载模板
            Template template = configuration.getTemplate("test.ftl");
            // 5.创建数据模型
            Map map = new HashMap();
            map.put("article", article);
            // 6.创建Writer对象
            Writer out = new FileWriter(new File(templateFile+article.getId()+".html"));
            // 7.输出
            template.process(map, out);
            // 8.关闭Writer对象
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @return the templateFile
     */
    public static String getTemplateFile() {
        return templateFile;
    }

    /**
     * @param templateFile the templateFile to set
     */
    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }

    /**
     * @return the templateDiar
     */
    public static String getTemplateDiar() {
        return templateDiar;
    }

    /**
     * @param templateDiar the templateDiar to set
     */
    public  void setTemplateDiar(String templateDiar) {
        this.templateDiar = templateDiar;
    }
  
      
 
}