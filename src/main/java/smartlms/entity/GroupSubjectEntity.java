package smartlms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "group_subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupSubjectEntity extends BaseEntity {

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "group_id", nullable = false)
        private GroupEntity group;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "subject_id", nullable = false)
        private SubjectEntity subject;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "teacher_id", nullable = false)
        private TeacherProfileEntity teacher;

        @OneToMany(mappedBy = "groupSubject")
        List<AssignmentEntity> assignmentEntityList;

        @Column(nullable = false)
        private Integer semester;

        @Column(nullable = false)
        private String academicYear;

        @Column(nullable = false)
         private Double finalScore;
}
