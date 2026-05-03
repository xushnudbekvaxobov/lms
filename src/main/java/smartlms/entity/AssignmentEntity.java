package smartlms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentEntity extends BaseEntity {

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "group_subject_id", nullable = false)
        private GroupSubjectEntity groupSubject;

        @Column(nullable = false, length = 200)
        private String title;

        @Column(nullable = false)
        private LocalDateTime dueDate;

        @Column(nullable = false)
        private Double maxScore;
}
