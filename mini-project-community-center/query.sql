-- 스키마 생성
DROP DATABASE IF EXISTS  `mini-project-community-center`;
CREATE DATABASE IF NOT EXISTS  `mini-project-community-center`
  CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `mini-project-community-center`;

-- 드랍 순서(FK 역순)
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS attendance;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS course_sessions;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS centers;
DROP TABLE IF EXISTS refresh_tokens;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

-- 1) 공통: 사용자/권한
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  login_id VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(30) NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  UNIQUE KEY uk_users_login (login_id),
  UNIQUE KEY uk_users_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='사용자';

CREATE TABLE roles (
  role_name VARCHAR(30) PRIMARY KEY  -- STUDENT/INSTRUCTOR/STAFF/ADMIN
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='역할';

CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role_name VARCHAR(30) NOT NULL,
  PRIMARY KEY (user_id, role_name),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (role_name) REFERENCES roles(role_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='사용자-역할 매핑';

-- 2) refresh token
CREATE TABLE refresh_tokens (
	id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    login_id VARCHAR(100) NOT NULL, 
    token VARCHAR(512) NOT NULL,
    expiry BIGINT NOT NULL,
    FOREIGN KEY (login_id) REFERENCES users(login_id),
    UNIQUE KEY uk_refresh_login (login_id),
    UNIQUE KEY uk_refresh_token (token)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT = 'JWT Refresh Token 저장 테이블';
  
-- 3) 센터/강좌
CREATE TABLE centers (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(120) NOT NULL,
  address VARCHAR(255) NULL,
  latitude DECIMAL(10,7) NULL,
  longitude DECIMAL(10,7) NULL,
  phone VARCHAR(30) NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  INDEX idx_centers_geo (latitude, longitude)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='지점(센터)';

CREATE TABLE courses (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  center_id BIGINT NOT NULL,
  title VARCHAR(200) NOT NULL,
  category VARCHAR(50) NOT NULL,             -- 예: ART/MUSIC/COOKING/FITNESS/IT 등
  level VARCHAR(20) NOT NULL DEFAULT 'BEGINNER', -- BEGINNER/INTERMEDIATE/ADVANCED
  capacity INT NOT NULL,                      -- 강좌 정원(세션 공통)
  fee DECIMAL(10,2) NOT NULL DEFAULT 0,
  status VARCHAR(20) NOT NULL DEFAULT 'OPEN', -- OPEN/CLOSED/CANCELED
  description TEXT NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  FOREIGN KEY (center_id) REFERENCES centers(id),
  CHECK (level IN ('BEGINNER','INTERMEDIATE','ADVANCED')),
  CHECK (status IN ('OPEN','CLOSED','CANCELED')),
  INDEX idx_courses_center (center_id, status, category, level),
  INDEX idx_courses_period (start_date, end_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='강좌(마스터)';

CREATE TABLE course_instructors (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    instructor_id BIGINT NOT NULL,
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
	FOREIGN KEY (instructor_id) REFERENCES users(id),
    UNIQUE KEY uk_course_instructor (course_id, instructor_id),
    INDEX idx_course_instructor_course (course_id),
    INDEX idx_course_instructor_instructor (instructor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='강좌-강사 매핑';

-- 4) 강좌 세션(회차)
CREATE TABLE course_sessions (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  course_id BIGINT NOT NULL,
  start_time DATETIME(6) NOT NULL,
  end_time DATETIME(6) NOT NULL,
  room VARCHAR(60) NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED', -- SCHEDULED/DONE/CANCELED
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
  CHECK (status IN ('SCHEDULED','DONE','CANCELED')),
  UNIQUE KEY uk_session_unique (course_id, start_time, end_time),
  INDEX idx_sessions_time (start_time, end_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='강좌 회차(세션)';

-- 5) 수강 등록
CREATE TABLE enrollments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  course_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  status VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING/CONFIRMED/CANCELED/REFUNDED
  enrolled_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  cancel_reason VARCHAR(200) NULL,
  FOREIGN KEY (course_id) REFERENCES courses(id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  UNIQUE KEY uk_enroll_once (course_id, user_id),  -- 1강좌당 1회 등록 원칙
  CHECK (status IN ('PENDING','CONFIRMED','CANCELED','REFUNDED')),
  INDEX idx_enroll_course_status (course_id, status),
  INDEX idx_enroll_user (user_id, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='수강 등록';

-- 6) 결제(모의)
CREATE TABLE payments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  enrollment_id BIGINT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  currency CHAR(3) NOT NULL DEFAULT 'KRW',
  method VARCHAR(20) NOT NULL,                -- CARD/TRANSFER 등
  status VARCHAR(20) NOT NULL DEFAULT 'PAID', -- PAID/PENDING/FAILED/REFUNDED
  paid_at DATETIME(6) NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  FOREIGN KEY (enrollment_id) REFERENCES enrollments(id),
  CHECK (status IN ('PAID','PENDING','FAILED','REFUNDED')),
  INDEX idx_pay_enroll (enrollment_id, status),
  INDEX idx_pay_paid_at (paid_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='결제 내역';

-- 7) 출석
CREATE TABLE attendance (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  session_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  status VARCHAR(15) NOT NULL DEFAULT 'ABSENT', -- PRESENT/LATE/ABSENT
  marked_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  note VARCHAR(255) NULL,
  FOREIGN KEY (session_id) REFERENCES course_sessions(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(id),
  CHECK (status IN ('PRESENT','LATE','ABSENT')),
  UNIQUE KEY uk_attend_once (session_id, user_id),
  INDEX idx_attend_session (session_id, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='출석 기록';

-- 8) 리뷰
CREATE TABLE reviews (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  course_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  rating TINYINT NOT NULL CHECK (rating BETWEEN 1 AND 5),
  content TEXT NULL,
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  FOREIGN KEY (course_id) REFERENCES courses(id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  UNIQUE KEY uk_review_once (course_id, user_id),
  INDEX idx_review_course (course_id, rating)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='수강 후기';