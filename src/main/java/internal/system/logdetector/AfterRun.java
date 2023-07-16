package internal.system.logdetector;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AfterRun {
    private final ResourceLoader resourceLoader;
    private final LogRepository logRepository;

    @Scheduled(fixedRate = 10000) // 10초
    public void logDetector() throws Exception{
//        Resource resource = resourceLoader.getResource("C:/java/hyunju_project/spring.log");
        String logFilePath = "C:/java/hyunju_project/spring.log";
        Resource resource = new FileSystemResource(logFilePath);

        if (resource.exists()) {
            Path path = Paths.get(resource.getFile().toURI());
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (line.contains("ERROR") && line.contains("|")) {
                    String[] splitLine = line.split("\\|");
                    Log log = new Log();

                    for (String sl : splitLine) {
                        int contentStartIndex = sl.indexOf("] : ");
                        String content = sl.substring(contentStartIndex + 4).trim();
                        if(sl.contains("[ 고객 id ]")){
                             log.setUserId(content);
                        }else if(sl.contains("[ 에러 유형 ]")){
                            log.setErrorCode(content);
                        } else if (sl.contains("[ 에러 시간 ]")) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime time = LocalDateTime.parse(content, formatter);
                            log.setErrorTime(time);
                        } else if (sl.contains("[ 에러메시지 ]")) {
                            log.setErrorMessage(content);
                        }
                    }
                    logRepository.save(log);
                }
            }
        } else {
            System.out.println("로그 파일이 존재하지 않습니다.");
        }

    }

}

