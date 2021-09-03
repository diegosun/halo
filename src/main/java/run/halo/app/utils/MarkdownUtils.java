package run.halo.app.utils;

import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.emoji.EmojiImageType;
import com.vladsch.flexmark.ext.emoji.EmojiShortcutType;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacterExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gitlab.GitLabExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.media.tags.MediaTagsExtension;
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.AbstractYamlFrontMatterVisitor;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import run.halo.app.model.support.HaloConst;
import run.halo.app.utils.footnotes.FootnoteExtension;

/**
 * Markdown utils.
 *
 * @author ryanwang
 * @date 2019-06-27
 */
public class MarkdownUtils {

    private static final DataHolder OPTIONS =
        new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(AttributesExtension.create(),
            AutolinkExtension.create(),
            EmojiExtension.create(),
            EscapedCharacterExtension.create(),
            StrikethroughExtension.create(),
            TaskListExtension.create(),
            InsExtension.create(),
            MediaTagsExtension.create(),
            TablesExtension.create(),
            TocExtension.create(),
            SuperscriptExtension.create(),
            YamlFrontMatterExtension.create(),
            FootnoteExtension.create(),
            GitLabExtension.create()))
            .set(TocExtension.LEVELS, 255)
            .set(TablesExtension.WITH_CAPTION, false)
            .set(TablesExtension.COLUMN_SPANS, false)
            .set(TablesExtension.MIN_SEPARATOR_DASHES, 1)
            .set(TablesExtension.MIN_HEADER_ROWS, 1)
            .set(TablesExtension.MAX_HEADER_ROWS, 1)
            .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
            .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
            .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)
            .set(EmojiExtension.USE_SHORTCUT_TYPE, EmojiShortcutType.EMOJI_CHEAT_SHEET)
            .set(EmojiExtension.USE_IMAGE_TYPE, EmojiImageType.UNICODE_ONLY)
            .set(HtmlRenderer.SOFT_BREAK, "<br />\n")
            .set(FootnoteExtension.FOOTNOTE_BACK_REF_STRING, "↩︎");

    private static final Parser PARSER = Parser.builder(OPTIONS).build();

    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();
    private static final Pattern FRONT_MATTER = Pattern.compile("^---[\\s\\S]*?---");

    private static final Pattern MARKDOWN_IMAGE_PATTERN = Pattern.compile("!\\[[\\S]*\\]\\(([\\S]*)\\)");
    // private static final Pattern HTML_IMAGE_PATTERN = Pattern.compile("<img[\\s\\S]*src=\"(\\S+)\"[\\s\\S]*/>");
    private static final Pattern HTML_IMAGE_PATTERN = Pattern.compile("<img.*src=\"(\\S+)\".*?>");

    //    /**
    //     * Render html document to markdown document.
    //     *
    //     * @param html html document
    //     * @return markdown document
    //     */
    //    public static String renderMarkdown(String html) {
    //        return FlexmarkHtmlParser.parse(html);
    //    }

    /**
     * Render Markdown content
     *
     * @param markdown content
     * @return String
     */
    public static String renderHtml(String markdown) {
        if (StringUtils.isBlank(markdown)) {
            return StringUtils.EMPTY;
        }

        // Render netease music short url.
        if (markdown.contains(HaloConst.NETEASE_MUSIC_PREFIX)) {
            markdown = markdown
                .replaceAll(HaloConst.NETEASE_MUSIC_REG_PATTERN, HaloConst.NETEASE_MUSIC_IFRAME);
        }

        // Render bilibili video short url.
        if (markdown.contains(HaloConst.BILIBILI_VIDEO_PREFIX)) {
            markdown = markdown
                .replaceAll(HaloConst.BILIBILI_VIDEO_REG_PATTERN, HaloConst.BILIBILI_VIDEO_IFRAME);
        }

        // Render youtube video short url.
        if (markdown.contains(HaloConst.YOUTUBE_VIDEO_PREFIX)) {
            markdown = markdown
                .replaceAll(HaloConst.YOUTUBE_VIDEO_REG_PATTERN, HaloConst.YOUTUBE_VIDEO_IFRAME);
        }

        Node document = PARSER.parse(markdown);

        return RENDERER.render(document);
    }

    /**
     * Get front-matter
     *
     * @param markdown markdown
     * @return Map
     */
    public static Map<String, List<String>> getFrontMatter(String markdown) {
        markdown = markdown.trim();
        Matcher matcher = FRONT_MATTER.matcher(markdown);
        if (matcher.find()) {
            markdown = matcher.group();
        }
        markdown = Arrays.stream(markdown.split("\\r?\\n")).map(row -> {
            if (row.startsWith("- ")) {
                return " " + row;
            } else {
                return row;
            }
        }).collect(Collectors.joining("\n"));
        AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
        Node document = PARSER.parse(markdown);
        visitor.visit(document);
        return visitor.getData();
    }

    /**
     * remove front matter
     *
     * @param markdown markdown
     * @return markdown
     */
    public static String removeFrontMatter(String markdown) {
        markdown = markdown.trim();
        Matcher matcher = FRONT_MATTER.matcher(markdown);
        if (matcher.find()) {
            return markdown.replace(matcher.group(), "");
        }
        return markdown;
    }

    public static List<String> getImageSrc(String markdown){
        List<String> srcList = new ArrayList<>();

        Matcher matcher = MARKDOWN_IMAGE_PATTERN.matcher(markdown);
        while (matcher.find()){
            String src = matcher.group(1);
            srcList.add(src);
        }

        Matcher matcher2 = HTML_IMAGE_PATTERN.matcher(markdown);
        while (matcher2.find()){
            String src = matcher2.group(1);
            srcList.add(src);
        }

        return srcList;
    }

    // test
    public static void main(String[] args) {
        String strMd = "xxxx![zipkin示意图](zipkin示意图.png)xxxx";
        String strHtml = "\n" +
            "\n" +
            "本文是学习ali云的云原生课程的记录，目的是对基础概念的理解\n" +
            "[原课程](2019-12-11k8s基础/https://edu.aliyun.com/roadmap/cloudnative)  \n" +
            "\n" +
            "<!--more-->\n" +
            "\n" +
            "# K8S核心概念  \n" +
            "\n" +
            "## 架构\n" +
            "\n" +
            "- K8S的架构是CS架构：  \n" +
            "  <img src=\"2019-12-11k8s基础/架构-1.png\" alt=\"架构-1.png\" style=\"zoom:67%;\" />  \n" +
            "\n" +
            "- master的结构如下：  \n" +
            "  <img src=\"2019-12-11k8s基础/master结构.png\" alt=\"master结构.png\" style=\"zoom:67%;\"" +
            " />  \n" +
            "\n" +
            "  - Api Server不仅是外部访问k8s的入口，也是k8s各组件内部的枢纽  \n" +
            "  - Scheduler用于调度deployment的pod在哪个node节点运行  \n" +
            "  - Controller用于控制状态，做一些检测，确定集群运行正常，当有节点宕机，将其上的pod运行到其他节点上。  \n" +
            "  - etcd是一个分布式数据库  \n" +
            "\n" +
            "  ps: \n" +
            "  \n" +
            "  **2021-03-09**:  API Server的这个方式比较适合该场景。与互联网不同，访问量不会特别高，不同的功能单独做了封装。**API " +
            "server是控制，etcd是存储，Controller与Scheduler是逻辑**。\n" +
            "\n" +
            "\n" +
            "\n" +
            "- node的结构如下：  \n" +
            " \n" +
            "\n" +
            "  - pod翻译是豆荚，是k8s对容器的一种封装  \n" +
            "  - kubelet，是node下的控制，接收自master的命令，运行pod  \n" +
            "  - Container Runtime，容器运行环境，是容器实际运行的地方  \n" +
            "  - storage plugin，存储插件，是云计算厂商实现的存储接口  \n" +
            "  - network plugin，网络插件，同样也是云计算厂商实现  \n" +
            "  - **kube-proxy**，是位置k8s内部service集群的代理  \n" +
            "\n" +
            "- 示例  \n" +
            "  <img src=\"2019-12-11k8s基础/一个例子.png\" alt=\"一个例子.png\" style=\"zoom:67%;\" />  \n" +
            "  <img src=\"2019-12-11k8s基础/node.png\" alt=\"master结构.png\" style=\"zoom:67%;\" /> " +
            "\n" +
            "  这个例子是有一个pod要运行，先与api server进行交互，api " +
            "server进行存储，然后交给scheduler进行调度，scheduler根据目前集群的状态，计算该pod运行的位置，并经过api " +
            "server进行存储，下发给node上的kubelet，kubelet再在container Runtime下运行起相应的pod（容器）";
        String strContent = strMd+strHtml + strHtml + strHtml;

        Matcher m = MARKDOWN_IMAGE_PATTERN.matcher(strMd);
        if(m.find()){
            System.out.println(m.group(1));
        }

        m = HTML_IMAGE_PATTERN.matcher(strHtml);
        if(m.find()){
            System.out.println(m.group(1));
        }

        List<String> list = MarkdownUtils.getImageSrc(strContent);
        System.out.println(list);
    }
}
