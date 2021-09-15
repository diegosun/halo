package run.halo.app.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import run.halo.app.service.BackupService;
import run.halo.app.utils.AliOssUtil;
import run.halo.app.utils.FileUtils;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author: DiegoSun
 * @time: 2021/9/8 下午3:11
 * @description:
 */
@Slf4j
@Component
public class BackupTask {

    private final BackupService backupService;

    private final AliOssUtil aliOssUtil;

    private Integer flag;

    public BackupTask(BackupService backupService, AliOssUtil aliOssUtil) {
        this.backupService = backupService;
        this.aliOssUtil = aliOssUtil;
        this.flag = 1;
    }


    /** 全量备份：每月一次
     * 秒 分 时 日 月 周
     * ?表示任意， /表示每隔
     * 以下为每隔一周的周一凌晨1点
     * 每月的1日、15日的凌晨1点备份
     */
    // @Scheduled(cron = "0 0 11 ? * 5/2")
    // @Scheduled(cron = "0 0 1 1 * ?")
    // public synchronized void fullBackup() {
    //     try {
    //         Path path = backupService.fullBackupWorkDirAndData();
    //         this.aliOssUtil.upload(path, "friday-blog", "backup");
    //         // FileUtils.deleteFolder(path);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    /**
     * 备份：每周一次
     * 每4周一个全量
     */
    @Scheduled(cron = "0 0 1 ? * 1")
    public synchronized void increaseBackup() {
        try {
            Path path = null;
            if(this.flag%4 == 0){
                // 全量备份
                this.flag = 0;
                path = backupService.fullBackupWorkDirAndData();
            } else {
                // 增量备份
                path = backupService.increaseBackupImageAndData();
            }

            this.aliOssUtil.upload(path, "friday-blog", "backup");
            FileUtils.deleteFolder(path);
            this.flag++;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
