package smartlms.dto.projection;

import java.util.UUID;
public interface GroupSubjectProjectionForStudents {
    UUID getId();
    Integer getSemester();
    UUID getSubjectId();
    String getSubjectName();
    String getSubjectCode();
    UUID getTeacherId();
    String getTeacherFirstName();
    String getTeacherLastName();
    Long getNbCount();
}
