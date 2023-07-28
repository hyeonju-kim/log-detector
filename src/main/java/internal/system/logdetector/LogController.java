package internal.system.logdetector;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController {
    private final LogRepository logRepository;

    // 1. 에러 로그 전체 조회
    @GetMapping
    public String getAllLog(Model model) {
        List<Log> foundAllLog = logRepository.findAll();
        model.addAttribute("foundAllLog", foundAllLog);
        return "basic/dashboard";
    }

    // 2. 시간 순 조회 (최신순 - 내림차순)
    @GetMapping("/timeDesc")
    public String getLogByTimeDesc(Model model) {
        List<Log> foundLogByTimeDesc = logRepository.findByErrorTimeDesc();
        model.addAttribute("foundLogByTimeDesc", foundLogByTimeDesc);
        return "basic/timeDesc";
    }

    // 3. 특정 시간 조회
    @GetMapping("/timeRng")  //timeRng?start=0&end=6 이렇게 작성해서 조회
    public String getLog(@RequestParam String start, @RequestParam String end, Model model) {
        int startTime = Integer.parseInt(start);
        int endTime = Integer.parseInt(end);
        List<Log> foundLogByTime = logRepository.findByErrorTimeHourBetween(startTime, endTime);
        model.addAttribute("foundLogByTime", foundLogByTime);
        return "basic/timeRng";
    }

    // 4. 고객 번호로 조회
    @GetMapping("/userId")
    public String getLogByUserId(@RequestParam("userId") String userId, Model model) {
        List<Log> foundLogByUserId = logRepository.findByUserId(userId);
        model.addAttribute("foundLogByUserId", foundLogByUserId);
        return "basic/userId";

    }

    // 5. 가장 많이 나온 에러 코드 조회
    @GetMapping("/mostError")
    public String getLogMost(Model model) {
        List<Object> foundMostLog = logRepository.findMostCommonErrors();
        model.addAttribute("foundMostLog", foundMostLog);
        return "basic/mostError";

    }

    // 6. 가장 많이 나온 시간대 조회
    @GetMapping("/mostFrequentTime")
    public String getMostFrequentTime(Model model) {
        List<Object[]> mostFrequentTime = logRepository.findMostFrequentTime();
        model.addAttribute("mostFrequentTime", mostFrequentTime);
        return "basic/mostFrequentTime";
    }

}
