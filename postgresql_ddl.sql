-- BaseEntity (MappedSuperclass - will be inherited)
-- id: UUID
-- created_at: TIMESTAMP
-- updated_at: TIMESTAMP

-- Users table
CREATE TABLE users (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL CHECK (role IN ('STUDENT', 'TEACHER', 'ADMIN')),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE', 'BLOCKED')),
    enabled BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Student Profiles table
CREATE TABLE student_profiles (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE REFERENCES users(uuid) ON DELETE CASCADE,
    student_id VARCHAR(50) NOT NULL UNIQUE,
    birth_date DATE,
    address TEXT,
    gender VARCHAR(10) CHECK (gender IN ('MALE', 'FEMALE')),
    parent_name VARCHAR(200),
    parent_phone VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Teacher Profiles table
CREATE TABLE teacher_profiles (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE REFERENCES users(uuid) ON DELETE CASCADE,
    teacher_id VARCHAR(50) NOT NULL UNIQUE,
    specialization VARCHAR(200),
    degree VARCHAR(100),
    experience_years INTEGER,
    gender VARCHAR(10) CHECK (gender IN ('MALE', 'FEMALE')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Groups table
CREATE TABLE groups (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    teacher_id UUID REFERENCES teacher_profiles(uuid),
    max_students INTEGER,
    current_students INTEGER DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Subjects table
CREATE TABLE subjects (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(200) NOT NULL,
    code VARCHAR(50) UNIQUE,
    description TEXT,
    credits INTEGER,
    total_hours INTEGER,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Group Subjects table (many-to-many)
CREATE TABLE group_subjects (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    group_id UUID NOT NULL REFERENCES groups(uuid) ON DELETE CASCADE,
    subject_id UUID NOT NULL REFERENCES subjects(uuid) ON DELETE CASCADE,
    teacher_id UUID REFERENCES teacher_profiles(uuid),
    semester INTEGER,
    hours_per_week INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Schedules table
CREATE TABLE schedules (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    group_id UUID NOT NULL REFERENCES groups(uuid) ON DELETE CASCADE,
    subject_id UUID NOT NULL REFERENCES subjects(uuid) ON DELETE CASCADE,
    teacher_id UUID REFERENCES teacher_profiles(uuid),
    day_of_week VARCHAR(20) NOT NULL CHECK (day_of_week IN ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY')),
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    room VARCHAR(50),
    lesson_type VARCHAR(20) CHECK (lesson_type IN ('LECTURE', 'PRACTICE', 'LAB', 'SEMINAR'))
);

-- Assignments table
CREATE TABLE assignments (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(300) NOT NULL,
    description TEXT,
    subject_id UUID NOT NULL REFERENCES subjects(uuid) ON DELETE CASCADE,
    group_id UUID REFERENCES groups(uuid) ON DELETE SET NULL,
    teacher_id UUID REFERENCES teacher_profiles(uuid),
    due_date DATE,
    max_score INTEGER,
    assignment_type VARCHAR(30) CHECK (assignment_type IN ('PRACTICE', 'LECTURE', 'INDEPENDENT_WORK', 'MIDTERM', 'FINAL')),
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Grades table
CREATE TABLE grades (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    student_id UUID NOT NULL REFERENCES student_profiles(uuid) ON DELETE CASCADE,
    subject_id UUID NOT NULL REFERENCES subjects(uuid) ON DELETE CASCADE,
    assignment_id UUID REFERENCES assignments(uuid) ON DELETE SET NULL,
    score DECIMAL(5,2) NOT NULL,
    max_score DECIMAL(5,2),
    comment TEXT,
    teacher_id UUID REFERENCES teacher_profiles(uuid),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Attendances table
CREATE TABLE attendances (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    student_id UUID NOT NULL REFERENCES student_profiles(uuid) ON DELETE CASCADE,
    subject_id UUID NOT NULL REFERENCES subjects(uuid) ON DELETE CASCADE,
    date DATE NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PRESENT', 'ABSENT', 'LATE')),
    reason TEXT,
    teacher_id UUID REFERENCES teacher_profiles(uuid),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Notifications table
CREATE TABLE notifications (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(uuid) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_student_profiles_user_id ON student_profiles(user_id);
CREATE INDEX idx_teacher_profiles_user_id ON teacher_profiles(user_id);
CREATE INDEX idx_groups_teacher_id ON groups(teacher_id);
CREATE INDEX idx_group_subjects_group_id ON group_subjects(group_id);
CREATE INDEX idx_group_subjects_subject_id ON group_subjects(subject_id);
CREATE INDEX idx_schedules_group_id ON schedules(group_id);
CREATE INDEX idx_schedules_day_of_week ON schedules(day_of_week);
CREATE INDEX idx_assignments_subject_id ON assignments(subject_id);
CREATE INDEX idx_grades_student_id ON grades(student_id);
CREATE INDEX idx_grades_subject_id ON grades(subject_id);
CREATE INDEX idx_attendances_student_id ON attendances(student_id);
CREATE INDEX idx_attendances_subject_id ON attendances(subject_id);
CREATE INDEX idx_attendances_date ON attendances(date);
CREATE INDEX idx_notifications_user_id ON notifications(user_id);
