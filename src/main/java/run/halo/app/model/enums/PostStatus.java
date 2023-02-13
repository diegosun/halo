package run.halo.app.model.enums;

import java.util.Set;

/**
 * Post status.
 *
 * @author johnniang
 */
public enum PostStatus implements ValueEnum<Integer> {

    /**
     * Published status.
     */
    PUBLISHED(0),

    /**
     * Draft status.
     */
    DRAFT(1),

    /**
     * Recycle status.
     */
    RECYCLE(2),

    /**
     * Intimate status
     */
    INTIMATE(3);

    private final int value;

    PostStatus(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public static Set<PostStatus> getByAuth(Boolean auth){
        return auth ? Set.of(PUBLISHED, INTIMATE) : Set.of(PUBLISHED);
    }
}
