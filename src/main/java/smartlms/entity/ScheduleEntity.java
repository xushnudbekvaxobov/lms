package smartlms.entity;

import jakarta.persistence.*;
import lombok.*;
import smartlms.entity.enums.LessonType;
import smartlms.entity.enums.WeekDay;

import java.time.LocalTime;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleEntity extends BaseEntity {

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "group_subject_id", nullable = false)
        private GroupSubjectEntity groupSubject;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private WeekDay weekDay; // MONDAY, TUESDAY...

        @Column(nullable = false)
        private LocalTime startTime;

        @Column(nullable = false)
        private LocalTime endTime;

        @Column(length = 50)
        private String room;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private LessonType lessonType; // LECTURE, PRACTICE, LAB
}
