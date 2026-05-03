package smartlms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "grades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
    public class GradeEntity extends BaseEntity {

        @ManyToOne
        @JoinColumn(name = "assignment_id", nullable = false)
        private AssignmentEntity assignment;

        @ManyToOne
        @JoinColumn(name = "student_id", nullable = false)
        private StudentProfileEntity student;

        @Column(nullable = false)
        private Double score;
}
