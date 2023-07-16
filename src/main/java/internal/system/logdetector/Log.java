package internal.system.logdetector;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Log {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ERROR_CODE")
    private String errorCode;

    @Column(name = "ERROR_TIME")
    private LocalDateTime errorTime;

    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;
}
