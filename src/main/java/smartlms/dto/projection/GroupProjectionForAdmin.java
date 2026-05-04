package smartlms.dto.projection;

import java.util.UUID;

public interface GroupProjectionForAdmin {
     Long getStudentCount();
     Long getSubjectCount();
     UUID getGroupId();
     String getGroupName();
     Integer getGroupCourse();
     String getGroupFaculty();
}
