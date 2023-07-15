package internal.system.logdetector;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LogController {

    private final LogRepository logRepository;

    @GetMapping("/log")
    public List<Log> log() {
        return logRepository.findAll();
    }

}
