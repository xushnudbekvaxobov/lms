package smartlms.dto.projection;

import java.util.UUID;

public interface GroupSubjectProjectionForTeachers {
    UUID getGroupId();
    String getGroupName();
    Integer getGroupCourse();
    String getGroupFaculty();
    UUID getSubjectId();
    String getSubjectName();
    String getSubjectCode();
    Long getStudentCount();
}
