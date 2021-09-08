package run.halo.app.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import run.halo.app.service.BackupService;
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

    @Autowired
    BackupService backupService;

    @Scheduled(cron = "1 0 * * */2 ?")
    public synchronized void run() {
        try {
            Path path = backupService.backupWorkDirAndData();
            FileUtils.deleteFolder(path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
