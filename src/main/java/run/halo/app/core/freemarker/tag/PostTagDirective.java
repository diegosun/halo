package run.halo.app.core.freemarker.tag;

import com.google.common.collect.Sets;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;
import run.halo.app.model.entity.Post;
import run.halo.app.model.enums.PostStatus;
import run.halo.app.model.support.HaloConst;
import run.halo.app.service.PostCategoryService;
import run.halo.app.service.PostService;
import run.halo.app.service.PostTagService;
import run.halo.app.utils.AuthUtil;

/**
 * Freemarker custom tag of post.
 *
 * @author ryanwang
 * @date 2018-04-26
 */
@Component
public class PostTagDirective implements TemplateDirectiveModel {

    private final PostService postService;

    private final PostTagService postTagService;

    private final PostCategoryService postCategoryService;

    public PostTagDirective(Configuration configuration,
        PostService postService,
        PostTagService postTagService,
        PostCategoryService postCategoryService) {
        this.postService = postService;
        this.postTagService = postTagService;
        this.postCategoryService = postCategoryService;
        configuration.setSharedVariable("postTag", this);
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
        TemplateDirectiveBody body) throws TemplateException, IOException {
        final DefaultObjectWrapperBuilder builder =
            new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
        if (params.containsKey(HaloConst.METHOD_KEY)) {
            String method = params.get(HaloConst.METHOD_KEY).toString();
            switch (method) {
                case "latest":
                    int top = Integer.parseInt(params.get("top").toString());
                    env.setVariable("posts", builder.build()
                        .wrap(postService.convertToListVo(postService.listLatest(top))));
                    break;
                case "count": {
                    Boolean auth = AuthUtil.getAuth();
                    Set<PostStatus> statusSet = PostStatus.getByAuth(auth);
                    env.setVariable("count",
                        builder.build().wrap(postService.countByStatusSet(statusSet)));
                }
                    break;
                case "archiveYear":
                    env.setVariable("archives",
                        builder.build().wrap(postService.listYearArchives()));
                    break;
                case "archiveMonth":
                    env.setVariable("archives",
                        builder.build().wrap(postService.listMonthArchives()));
                    break;
                case "archive":
                    String type = params.get("type").toString();
                    env.setVariable("archives", builder.build().wrap(
                        "year".equals(type) ? postService.listYearArchives() :
                            postService.listMonthArchives()));
                    break;
                case "listByCategoryId":
                    Integer categoryId = Integer.parseInt(params.get("categoryId").toString());
                    env.setVariable("posts", builder.build().wrap(postService.convertToListVo(
                        postCategoryService.listPostBy(categoryId,
                            Sets.immutableEnumSet(PostStatus.PUBLISHED, PostStatus.INTIMATE)))));
                    break;
                case "listByCategorySlug":
                    String categorySlug = params.get("categorySlug").toString();
                    List<Post> posts =
                        postCategoryService.listPostBy(categorySlug,
                            Sets.immutableEnumSet(PostStatus.PUBLISHED, PostStatus.INTIMATE));
                    env.setVariable("posts",
                        builder.build().wrap(postService.convertToListVo(posts)));
                    break;
                case "listByTagId":
                    Integer tagId = Integer.parseInt(params.get("tagId").toString());
                    env.setVariable("posts", builder.build().wrap(postService
                        .convertToListVo(postTagService.listPostsBy(tagId, PostStatus.PUBLISHED))));
                    break;
                case "listByTagSlug":
                    String tagSlug = params.get("tagSlug").toString();
                    env.setVariable("posts", builder.build().wrap(postService.convertToListVo(
                        postTagService.listPostsBy(tagSlug, PostStatus.PUBLISHED))));
                    break;
                default:
                    break;
            }
        }
        body.render(env.getOut());
    }

}
