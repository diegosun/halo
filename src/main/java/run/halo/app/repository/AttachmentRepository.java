package run.halo.app.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import run.halo.app.model.entity.Attachment;
import run.halo.app.model.enums.AttachmentType;
import run.halo.app.repository.base.BaseRepository;

/**
 * Attachment repository
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-04-03
 */
public interface AttachmentRepository
    extends BaseRepository<Attachment, Integer>, JpaSpecificationExecutor<Attachment> {

    /**
     * Find all attachment media type.
     *
     * @return list of media type.
     */
    @Query(value = "select distinct a.mediaType from Attachment a")
    List<String> findAllMediaType();

    /**
     * Find all attachment type.
     *
     * @return list of type.
     */
    @Query(value = "select distinct a.type from Attachment a")
    List<AttachmentType> findAllType();

    /**
     * Counts by attachment path.
     *
     * @param path attachment path must not be blank
     * @return count of the given path
     */
    long countByPath(@NonNull String path);

    /**
     * 根据名字获取附件
     * @param name
     * @return
     */
    @Query(value = "select a from Attachment a where a.name=?1 order by a.createTime desc")
    List<Attachment> findByName(String name);

    /**
     * 获取某个时间点之后的附件
     * @param time
     * @return
     */
    List<Attachment> findByCreateTimeAfter(Date time);
}
