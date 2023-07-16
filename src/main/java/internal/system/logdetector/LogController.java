package internal.system.logdetector;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController {
    private final LogRepository logRepository;

    // 1. 에러 로그 전체 조회
    @GetMapping
    public List<Log> getAllLog() {
        return logRepository.findAll();
    }

    // 2. 시간 순 조회 (최신순 - 내림차순)
    @GetMapping("/timeDesc")
    public List<Log> getLogByTimeDesc() {
        return logRepository.findByErrorTimeDesc();
    }

    // 3. 특정 시간 조회
    @GetMapping("/timeRng")  //timeRng?start=0&end=6 이렇게 작성해서 조회
    public List<Log> getLog(@RequestParam Integer start, @RequestParam Integer end) {
        return logRepository.findByErrorTimeHourBetween(start, end);
    }

    // 4. 고객 번호로 조회
    @GetMapping("/user")
    public List<Log> getLogByUserId(@RequestParam("id") String id) {
        return logRepository.findByUserId(id);
    }

    // 5. 가장 많이 나온 에러 코드 조회
    @GetMapping("/mostError")
    public List<Object> getLogMost() {
        return logRepository.findMostCommonErrors();
    }

    // 6. 가장 많이 나온 시간대 조회
    @GetMapping("/mostTime")
    public List<Object[]> getMostFrequentTime() {
        return logRepository.findMostFrequentTime();
    }

}
