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
                if (line.contains("ERROR")) {
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
                            log.setErrorTime(content);
                        } else if (sl.contains("[ 에러메시지 ]")) {
                            log.setErrorMessage(content);
                        }
//                        int titleStartIndex = sl.indexOf("[ ");
//                        int titleEndIndex = sl.indexOf(" ]");
//                        if (titleEndIndex != -1) { // ] 가 존재하는 경우에만 처리
//                            titleEndIndex += 2;
//                            String title = sl.substring(titleStartIndex, titleEndIndex).trim(); // [고객 id]
//                            int contentStartIndex = sl.indexOf("] : ");
//                            String content = sl.substring(contentStartIndex + 4).trim(); // 5
//
//
//                            System.out.println(title + " : " + content);
//                        }
                    }
                    logRepository.save(log,Log.class);
                }
            }
        } else {
            System.out.println("로그 파일이 존재하지 않습니다.");
        }

    }

}

