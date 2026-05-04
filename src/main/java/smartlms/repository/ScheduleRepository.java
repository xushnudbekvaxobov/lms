package smartlms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smartlms.entity.ScheduleEntity;
import smartlms.entity.enums.WeekDay;
import java.time.LocalTime;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, UUID> {

    @Query("""
            SELECT COUNT(s) > 0 FROM ScheduleEntity s
            WHERE s.room = :room 
            AND s.weekDay = :weekDay 
            AND s.startTime < :endTime 
            AND s.endTime > :startTime
            """)
    boolean existsConflict(@Param("room") String room,
                           @Param("weekDay") WeekDay weekDay,
                           @Param("startTime") LocalTime startTime,
                           @Param("endTime") LocalTime endTime);
}
