package net.xiaosaguo.myblog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * description: 将 Markdown 文档转换为 HTML 文档的工具类
 *
 * @author xiaosaguo
 * @date 2020/05/20
 */
public class MarkdownUtils {

    /**
     * description: Markdown 格式的文本转换成 HTML 格式
     *
     * @param markdown Markdown 格式的文本
     * @return HTML 格式的文本
     * @author xiaosaguo
     * @date 2020/05/24 13:13
     */
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * description: Markdown 转 HTML，增加扩展 [标题锚点、表格生成]
     *
     * @param markdown Markdown 格式的文本
     * @return HTML格式的文本
     * @author xiaosaguo
     * @date 2020/05/24 13:14
     */
    public static String markdownToHtmlExtensions(String markdown) {
        // h 标题生成 id
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        // 转换 table 的 HTML
        List<Extension> tableExtension = Collections.singletonList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtension).build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new CustomAttributeProvider();
                    }
                })
                .build();
        return renderer.render(document);
    }


    /**
     * description: 处理标签的属性
     *
     * @author xiaosaguo
     * @date 2020/05/24 13:16
     */
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            // 改变 a 标签的 target 属性设置为 _blank
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
            if (node instanceof TableBlock) {
                attributes.put("class", "ui celled table");
            }
        }
    }


    public static void main(String[] args) {
        String table = "| hello | hi | 哈哈哈 |\n" +
                "| ----- | ---- | ----- |\n" +
                "| 斯维 | 士大夫 | 顿 |\n" +
                "| 阿什顿 | 固定杆 | 阿什 |\n";
        String a = "[百度一下](https://www.baidu.com)";
        System.out.println(markdownToHtmlExtensions(a));
        System.out.println(markdownToHtmlExtensions(table));
    }
}
