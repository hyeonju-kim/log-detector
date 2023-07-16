package internal.system.logdetector;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public interface LogRepository extends JpaRepository<Log, Long> {

    // 2. 시간 순 조회
    @Query("SELECT l FROM Log l ORDER BY l.errorTime DESC ")
    List<Log> findByErrorTimeDesc();

    // 3. 특정 시간 목록 조회
    @Query("SELECT l FROM Log l WHERE HOUR(l.errorTime) BETWEEN :startTime AND :endTime")
    List<Log> findByErrorTimeHourBetween(@Param("startTime") int startTime, @Param("endTime") int endTime);

    // 4. 고객 번호로 조회
    List<Log> findByUserId(String id);

    // 5. 가장 많이 나온 에러 조회 (에러코드, 메시지)
    @Query("SELECT l.errorCode FROM Log l GROUP BY l.errorCode ORDER BY COUNT(l.errorCode) DESC ")
    List<Object> findMostCommonErrors();

    // 6. 가장 많이 나온 시간대 조회
    @Query("SELECT HOUR(l.errorTime) as hour, COUNT(l) as count FROM Log l GROUP BY HOUR(l.errorTime) ORDER BY COUNT(l) DESC")
    List<Object[]> findMostFrequentTime();


}
