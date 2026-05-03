package smartlms.entity;

import jakarta.persistence.*;
import lombok.*;
import smartlms.entity.enums.AttendanceStatus;

import java.time.LocalDate;

@Entity
@Table(name = "attendances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceEntity extends BaseEntity {

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "schedule_id", nullable = false)
        private ScheduleEntity schedule;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "student_id", nullable = false)
        private StudentProfileEntity student;

        @Column(nullable = false)
        private LocalDate lessonDate;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private AttendanceStatus status; // PRESENT, ABSENT, LATE
}
