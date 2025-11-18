-- 스키마 생성
DROP DATABASE IF EXISTS  `mini-project-community-center`;
CREATE DATABASE IF NOT EXISTS  `mini-project-community-center`
  CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `mini-project-community-center`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 드랍 순서(FK 역순)
DROP TABLE IF EXISTS `reviews`;
DROP TABLE IF EXISTS `attendance`;
DROP TABLE IF EXISTS `payments`;
DROP TABLE IF EXISTS `enrollments`;
DROP TABLE IF EXISTS `course_sessions`;
DROP TABLE IF EXISTS `courses`;
DROP TABLE IF EXISTS `centers`;
DROP TABLE IF EXISTS `refresh_tokens`;
DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `users`;

-- 1) 공통: 사용자/권한
CREATE TABLE `users` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  
  name VARCHAR(50) NOT NULL,
  login_id VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(30) NULL,
  
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  
  UNIQUE KEY `uk_users_login` (login_id),
  UNIQUE KEY `uk_users_email` (email)
)
	ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='사용자';
    
-- Seed: USERS (20명)
INSERT INTO `users` (id, name, login_id, password, email, phone, created_at, updated_at) VALUES
(1,'김민수','user001','pass001!','user001@example.com','010-1000-0001','2024-01-01 09:00:00','2024-01-01 09:00:00'),
(2,'이서연','user002','pass002!','user002@example.com','010-1000-0002','2024-01-02 09:00:00','2024-01-02 09:00:00'),
(3,'박지훈','user003','pass003!','user003@example.com','010-1000-0003','2024-01-03 09:00:00','2024-01-03 09:00:00'),
(4,'최유진','user004','pass004!','user004@example.com','010-1000-0004','2024-01-04 09:00:00','2024-01-04 09:00:00'),
(5,'정하준','user005','pass005!','user005@example.com','010-1000-0005','2024-01-05 09:00:00','2024-01-05 09:00:00'),
(6,'한지민','user006','pass006!','user006@example.com','010-1000-0006','2024-01-06 09:00:00','2024-01-06 09:00:00'),
(7,'장도윤','user007','pass007!','user007@example.com','010-1000-0007','2024-01-07 09:00:00','2024-01-07 09:00:00'),
(8,'윤서아','user008','pass008!','user008@example.com','010-1000-0008','2024-01-08 09:00:00','2024-01-08 09:00:00'),
(9,'오세훈','user009','pass009!','user009@example.com','010-1000-0009','2024-01-09 09:00:00','2024-01-09 09:00:00'),
(10,'서지호','user010','pass010!','user010@example.com','010-1000-0010','2024-01-10 09:00:00','2024-01-10 09:00:00'),
(11,'강민아','user011','pass011!','user011@example.com','010-1000-0011','2024-01-11 09:00:00','2024-01-11 09:00:00'),
(12,'배현우','user012','pass012!','user012@example.com','010-1000-0012','2024-01-12 09:00:00','2024-01-12 09:00:00'),
(13,'심다은','user013','pass013!','user013@example.com','010-1000-0013','2024-01-13 09:00:00','2024-01-13 09:00:00'),
(14,'문준호','user014','pass014!','user014@example.com','010-1000-0014','2024-01-14 09:00:00','2024-01-14 09:00:00'),
(15,'안예린','user015','pass015!','user015@example.com','010-1000-0015','2024-01-15 09:00:00','2024-01-15 09:00:00'),
(16,'조민재','user016','pass016!','user016@example.com','010-1000-0016','2024-01-16 09:00:00','2024-01-16 09:00:00'),
(17,'홍가은','user017','pass017!','user017@example.com','010-1000-0017','2024-01-17 09:00:00','2024-01-17 09:00:00'),
(18,'신태호','user018','pass018!','user018@example.com','010-1000-0018','2024-01-18 09:00:00','2024-01-18 09:00:00'),
(19,'곽소윤','user019','pass019!','user019@example.com','010-1000-0019','2024-01-19 09:00:00','2024-01-19 09:00:00'),
(20,'남도현','user020','pass020!','user020@example.com','010-1000-0020','2024-01-20 09:00:00','2024-01-20 09:00:00');

CREATE TABLE `roles` (
  role_name VARCHAR(30) PRIMARY KEY  -- STUDENT/INSTRUCTOR/STAFF/ADMIN
)
	ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='역할';
    
INSERT INTO roles VALUES ('ROLE_STUDENT'), ('ROLE_INSTRUCTOR'), ('ROLE_STAFF'), ('ROLE_ADMIN');

CREATE TABLE `user_roles` (
  user_id BIGINT NOT NULL,
  role_name VARCHAR(30) NOT NULL,
  
  PRIMARY KEY (user_id, role_name),
  
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (role_name) REFERENCES roles(role_name)
)
	ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='사용자-역할 매핑';
    
-- Seed: User Roles (ADMIN 1 / INSTRUCTOR 5 / STAFF 3 / STUDENT 11)
INSERT INTO `user_roles` (user_id, role_name) VALUES
(1,'ROLE_STUDENT'),
(2,'ROLE_STUDENT'),
(3,'ROLE_STUDENT'),
(4,'ROLE_STUDENT'),
(5,'ROLE_STUDENT'),
(6,'ROLE_STUDENT'),
(7,'ROLE_STUDENT'),
(8,'ROLE_STUDENT'),
(9,'ROLE_STUDENT'),
(10,'ROLE_STUDENT'),
(11,'ROLE_STUDENT'),
(12,'ROLE_INSTRUCTOR'),
(13,'ROLE_INSTRUCTOR'),
(14,'ROLE_INSTRUCTOR'),
(15,'ROLE_INSTRUCTOR'),
(16,'ROLE_INSTRUCTOR'),
(17,'ROLE_STAFF'),
(18,'ROLE_STAFF'),
(19,'ROLE_STAFF'),
(20,'ROLE_ADMIN');

-- 2) refresh token
CREATE TABLE `refresh_tokens` (
  id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    
  login_id VARCHAR(100) NOT NULL, 
    
  token VARCHAR(512) NOT NULL,
  expiry BIGINT NOT NULL,
    
  FOREIGN KEY (login_id) REFERENCES users(login_id),
    
  UNIQUE KEY `uk_refresh_login` (login_id),
  UNIQUE KEY `uk_refresh_token` (token)
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8mb4
	COLLATE = utf8mb4_unicode_ci
	COMMENT = 'JWT Refresh Token 저장 테이블';

-- Seed: Refresh Tokens (20개)
INSERT INTO `refresh_tokens` (id, login_id, token, expiry) VALUES
(1,'user001','token-user001',1893456000),
(2,'user002','token-user002',1893456000),
(3,'user003','token-user003',1893456000),
(4,'user004','token-user004',1893456000),
(5,'user005','token-user005',1893456000),
(6,'user006','token-user006',1893456000),
(7,'user007','token-user007',1893456000),
(8,'user008','token-user008',1893456000),
(9,'user009','token-user009',1893456000),
(10,'user010','token-user010',1893456000),
(11,'user011','token-user011',1893456000),
(12,'user012','token-user012',1893456000),
(13,'user013','token-user013',1893456000),
(14,'user014','token-user014',1893456000),
(15,'user015','token-user015',1893456000),
(16,'user016','token-user016',1893456000),
(17,'user017','token-user017',1893456000),
(18,'user018','token-user018',1893456000),
(19,'user019','token-user019',1893456000),
(20,'user020','token-user020',1893456000);

-- 3) 센터/강좌
CREATE TABLE `centers` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  
  name VARCHAR(120) NOT NULL,
  address VARCHAR(255) NULL,
  latitude DECIMAL(10,7) NULL,
  longitude DECIMAL(10,7) NULL,
  phone VARCHAR(30) NULL,
  
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  
  INDEX `idx_centers_geo` (latitude, longitude)
)
	ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='지점(센터)';

-- Seed: Centers (5개)
INSERT INTO `centers` (id, name, address, latitude, longitude, phone, created_at) VALUES
(1,'중앙예술센터','서울 중구 문화로 12',37.5665,126.9780,'02-100-0001','2024-02-01 09:00:00'),
(2,'강변음악홀','서울 영등포구 한강로 45',37.5201,126.9785,'02-100-0002','2024-02-02 09:00:00'),
(3,'남산피트니스허브','서울 용산구 남산로 78',37.5512,126.9880,'02-100-0003','2024-02-03 09:00:00'),
(4,'시티쿠킹랩','서울 종로구 맛골 9',37.5725,126.9821,'02-100-0004','2024-02-04 09:00:00'),
(5,'문화어학원','서울 서대문구 문화로 51',37.5658,126.9660,'02-100-0005','2024-02-05 09:00:00');

CREATE TABLE `courses` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  
  center_id BIGINT NOT NULL,
  
  title VARCHAR(200) NOT NULL,
  category VARCHAR(50) NOT NULL,             -- ART/MUSIC/COOKING/FITNESS/IT/LANGUAGE
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
  
  CHECK (category IN ('ART', 'MUSIC', 'COOKING', 'FITNESS', 'IT', 'LANGUAGE')),
  CHECK (level IN ('BEGINNER','INTERMEDIATE','ADVANCED')),
  CHECK (status IN ('OPEN','CLOSED','CANCELED')),
  
  INDEX `idx_courses_center` (center_id, status, category, level),
  INDEX `idx_courses_period` (start_date, end_date)
)
	ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='강좌(마스터)';

-- Seed: Courses (10개)
INSERT INTO `courses` (id, center_id, title, category, level, capacity, fee, status, description, start_date, end_date, created_at, updated_at) VALUES
(1,1,'수채화 기초','ART','BEGINNER',20,100000,'OPEN','수채화 기본 표현을 익힙니다.','2024-03-01','2024-04-01','2024-02-20 10:00:00','2024-02-20 10:00:00'),
(2,2,'클래식 기타 입문','MUSIC','BEGINNER',15,120000,'OPEN','기타 운지와 기본 연주를 배우는 과정.','2024-03-02','2024-04-02','2024-02-20 10:05:00','2024-02-20 10:05:00'),
(3,3,'모닝 필라테스','FITNESS','INTERMEDIATE',18,90000,'OPEN','체형 교정에 초점을 둔 필라테스.','2024-03-03','2024-04-03','2024-02-20 10:10:00','2024-02-20 10:10:00'),
(4,4,'비건 쿠킹 101','COOKING','BEGINNER',16,80000,'OPEN','간단한 비건 레시피를 실습합니다.','2024-03-04','2024-04-04','2024-02-20 10:15:00','2024-02-20 10:15:00'),
(5,5,'중국어 첫걸음','LANGUAGE','BEGINNER',25,110000,'OPEN','발음과 기본 회화를 학습.','2024-03-05','2024-04-05','2024-02-20 10:20:00','2024-02-20 10:20:00'),
(6,1,'파이썬 누구나','IT','BEGINNER',22,130000,'OPEN','프로그래밍 기초부터 간단한 프로젝트까지.','2024-03-06','2024-04-06','2024-02-20 10:25:00','2024-02-20 10:25:00'),
(7,2,'마음챙김 요가','FITNESS','BEGINNER',20,95000,'OPEN','호흡과 명상을 중심으로 한 요가.','2024-03-07','2024-04-07','2024-02-20 10:30:00','2024-02-20 10:30:00'),
(8,3,'세라믹 핸드빌딩','ART','INTERMEDIATE',14,140000,'OPEN','중급자 대상 핸드빌딩 테크닉.','2024-03-08','2024-04-08','2024-02-20 10:35:00','2024-02-20 10:35:00'),
(9,4,'라틴 댄스 나이트','FITNESS','BEGINNER',30,85000,'OPEN','라틴댄스 기본 스텝과 파트너 워크.','2024-03-09','2024-04-09','2024-02-20 10:40:00','2024-02-20 10:40:00'),
(10,5,'선라이즈 요가','FITNESS','ADVANCED',12,150000,'OPEN','고난도 아사나와 플로우 집중.','2024-03-10','2024-04-10','2024-02-20 10:45:00','2024-02-20 10:45:00');

CREATE TABLE `course_instructors` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
    
  course_id BIGINT NOT NULL,
  instructor_id BIGINT NOT NULL,
  
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    
  FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
  FOREIGN KEY (instructor_id) REFERENCES users(id),
    
  UNIQUE KEY `uk_course_instructor` (course_id, instructor_id),
    
  INDEX `idx_course_instructor_course` (course_id),
  INDEX `idx_course_instructor_instructor` (instructor_id)
)
	ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='강좌-강사 매핑';

-- Seed: Course Instructors (10개)
INSERT INTO `course_instructors` (id, course_id, instructor_id, created_at, updated_at) VALUES
(1,1,12,'2024-02-21 09:00:00','2024-02-21 09:00:00'),
(2,2,13,'2024-02-21 09:05:00','2024-02-21 09:05:00'),
(3,3,14,'2024-02-21 09:10:00','2024-02-21 09:10:00'),
(4,4,15,'2024-02-21 09:15:00','2024-02-21 09:15:00'),
(5,5,16,'2024-02-21 09:20:00','2024-02-21 09:20:00'),
(6,6,12,'2024-02-21 09:25:00','2024-02-21 09:25:00'),
(7,7,13,'2024-02-21 09:30:00','2024-02-21 09:30:00'),
(8,8,14,'2024-02-21 09:35:00','2024-02-21 09:35:00'),
(9,9,15,'2024-02-21 09:40:00','2024-02-21 09:40:00'),
(10,10,16,'2024-02-21 09:45:00','2024-02-21 09:45:00');

-- 4) 강좌 세션(회차)
CREATE TABLE `course_sessions` (
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
  
  UNIQUE KEY `uk_session_unique` (course_id, start_time, end_time),
  
  INDEX `idx_sessions_time` (start_time, end_time)
)
	ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='강좌 회차(세션)';

-- Seed: Course Sessions (10개)
INSERT INTO `course_sessions` (id, course_id, start_time, end_time, room, status, created_at, updated_at) VALUES
(1,1,'2024-03-01 09:00:00','2024-03-01 11:00:00','ART-101','SCHEDULED','2024-02-25 09:00:00','2024-02-25 09:00:00'),
(2,2,'2024-03-02 10:00:00','2024-03-02 12:00:00','MUS-201','SCHEDULED','2024-02-25 09:05:00','2024-02-25 09:05:00'),
(3,3,'2024-03-03 08:00:00','2024-03-03 09:30:00','FIT-301','SCHEDULED','2024-02-25 09:10:00','2024-02-25 09:10:00'),
(4,4,'2024-03-04 13:00:00','2024-03-04 15:00:00','CKG-101','SCHEDULED','2024-02-25 09:15:00','2024-02-25 09:15:00'),
(5,5,'2024-03-05 18:00:00','2024-03-05 20:00:00','LAN-401','SCHEDULED','2024-02-25 09:20:00','2024-02-25 09:20:00'),
(6,6,'2024-03-06 19:00:00','2024-03-06 21:00:00','IT-101','SCHEDULED','2024-02-25 09:25:00','2024-02-25 09:25:00'),
(7,7,'2024-03-07 07:00:00','2024-03-07 08:30:00','WEL-201','SCHEDULED','2024-02-25 09:30:00','2024-02-25 09:30:00'),
(8,8,'2024-03-08 14:00:00','2024-03-08 17:00:00','ART-202','SCHEDULED','2024-02-25 09:35:00','2024-02-25 09:35:00'),
(9,9,'2024-03-09 20:00:00','2024-03-09 22:00:00','DAN-101','SCHEDULED','2024-02-25 09:40:00','2024-02-25 09:40:00'),
(10,10,'2024-03-10 06:00:00','2024-03-10 08:00:00','YOG-301','SCHEDULED','2024-02-25 09:45:00','2024-02-25 09:45:00');

-- 5) 수강 등록
CREATE TABLE `enrollments` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  
  course_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  
  status VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING/CONFIRMED/CANCELED/REFUNDED
  cancel_reason VARCHAR(200) NULL,
  
  enrolled_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  
  FOREIGN KEY (course_id) REFERENCES courses(id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  
  UNIQUE KEY uk_enroll_once (course_id, user_id),  -- 1강좌당 1회 등록 원칙
  
  CHECK (status IN ('PENDING','CONFIRMED','CANCELED','REFUNDED')),
  
  INDEX `idx_enroll_course_status` (course_id, status),
  INDEX `idx_enroll_user` (user_id, status)
)
	ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='수강 등록';

-- Seed: Enrollments (10개)
INSERT INTO `enrollments` (id, course_id, user_id, status, cancel_reason, enrolled_at) VALUES
(1,1,1,'CONFIRMED',NULL,'2024-02-26 09:00:00'),
(2,2,2,'CONFIRMED',NULL,'2024-02-26 09:05:00'),
(3,3,3,'PENDING',NULL,'2024-02-26 09:10:00'),
(4,4,4,'CONFIRMED',NULL,'2024-02-26 09:15:00'),
(5,5,5,'CONFIRMED',NULL,'2024-02-26 09:20:00'),
(6,6,6,'CONFIRMED',NULL,'2024-02-26 09:25:00'),
(7,7,7,'PENDING',NULL,'2024-02-26 09:30:00'),
(8,8,8,'CONFIRMED',NULL,'2024-02-26 09:35:00'),
(9,9,9,'CONFIRMED',NULL,'2024-02-26 09:40:00'),
(10,10,10,'CONFIRMED',NULL,'2024-02-26 09:45:00');

-- 6) 결제(모의)
CREATE TABLE `payments` (
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
  
  INDEX `idx_pay_enroll` (enrollment_id, status),
  INDEX `idx_pay_paid_at` (paid_at)
)
	ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='결제 내역';

-- Seed: Payments (10개)
INSERT INTO `payments` (id, enrollment_id, amount, currency, method, status, paid_at, created_at, updated_at) VALUES
(1,1,100000,'KRW','CARD','PAID','2024-02-26 12:00:00','2024-02-26 12:00:00','2024-02-26 12:00:00'),
(2,2,120000,'KRW','CARD','PAID','2024-02-26 12:05:00','2024-02-26 12:05:00','2024-02-26 12:05:00'),
(3,3,90000,'KRW','CARD','PENDING',NULL,'2024-02-26 12:10:00','2024-02-26 12:10:00'),
(4,4,80000,'KRW','TRANSFER','PAID','2024-02-26 12:15:00','2024-02-26 12:15:00','2024-02-26 12:15:00'),
(5,5,110000,'KRW','CARD','PAID','2024-02-26 12:20:00','2024-02-26 12:20:00','2024-02-26 12:20:00'),
(6,6,130000,'KRW','CARD','PAID','2024-02-26 12:25:00','2024-02-26 12:25:00','2024-02-26 12:25:00'),
(7,7,95000,'KRW','CARD','PENDING',NULL,'2024-02-26 12:30:00','2024-02-26 12:30:00'),
(8,8,140000,'KRW','TRANSFER','PAID','2024-02-26 12:35:00','2024-02-26 12:35:00','2024-02-26 12:35:00'),
(9,9,85000,'KRW','CARD','PAID','2024-02-26 12:40:00','2024-02-26 12:40:00','2024-02-26 12:40:00'),
(10,10,150000,'KRW','CARD','PAID','2024-02-26 12:45:00','2024-02-26 12:45:00','2024-02-26 12:45:00');

-- 7) 출석
CREATE TABLE `attendance` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  
  session_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  
  status VARCHAR(15) NOT NULL DEFAULT 'ABSENT', -- PRESENT/LATE/ABSENT
  note VARCHAR(255) NULL,
  
  marked_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  
  FOREIGN KEY (session_id) REFERENCES course_sessions(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users(id),
  
  CHECK (status IN ('PRESENT','LATE','ABSENT')),
  
  UNIQUE KEY `uk_attend_once` (session_id, user_id),
  
  INDEX `idx_attend_session` (session_id, status)
)
	ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='출석 기록';

-- Seed: Attendance (10개)
INSERT INTO `attendance` (id, session_id, user_id, status, note, marked_at) VALUES
(1,1,1,'PRESENT',NULL,'2024-03-01 11:05:00'),
(2,2,2,'PRESENT',NULL,'2024-03-02 12:05:00'),
(3,3,3,'ABSENT','감기 증상','2024-03-03 09:35:00'),
(4,4,4,'PRESENT',NULL,'2024-03-04 15:05:00'),
(5,5,5,'PRESENT',NULL,'2024-03-05 20:05:00'),
(6,6,6,'PRESENT',NULL,'2024-03-06 21:05:00'),
(7,7,7,'LATE','교통 혼잡','2024-03-07 08:35:00'),
(8,8,8,'PRESENT',NULL,'2024-03-08 17:05:00'),
(9,9,9,'PRESENT',NULL,'2024-03-09 22:05:00'),
(10,10,10,'PRESENT',NULL,'2024-03-10 08:05:00');

-- 8) 리뷰
CREATE TABLE `reviews` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  
  course_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  
  rating TINYINT NOT NULL CHECK (rating BETWEEN 1 AND 5),
  content TEXT NULL,
  
  created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  
  FOREIGN KEY (course_id) REFERENCES courses(id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  
  UNIQUE KEY `uk_review_once` (course_id, user_id),
  
  INDEX `idx_review_course` (course_id, rating)
)
	ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='수강 후기';

-- Seed: Reviews (10개)
INSERT INTO `reviews` (id, course_id, user_id, rating, content, created_at) VALUES
(1,1,1,5,'수채화 수업이 즐거웠어요.','2024-03-02 09:00:00'),
(2,2,2,4,'강사님 설명이 친절했습니다.','2024-03-03 09:00:00'),
(3,3,3,3,'조금 더 난도가 있었으면 좋겠어요.','2024-03-04 09:00:00'),
(4,4,4,5,'비건 메뉴가 다양해서 좋았습니다.','2024-03-05 09:00:00'),
(5,5,5,4,'발음 연습이 많이 도움이 됐어요.','2024-03-06 09:00:00'),
(6,6,6,5,'파이썬 기초를 탄탄히 배웠습니다.','2024-03-07 09:00:00'),
(7,7,7,3,'명상 파트가 길었어요.','2024-03-08 09:00:00'),
(8,8,8,4,'공방 시설이 좋아요.','2024-03-09 09:00:00'),
(9,9,9,5,'라틴댄스가 정말 신났습니다.','2024-03-10 09:00:00'),
(10,10,10,4,'새벽 수업이 색다른 경험이었어요.','2024-03-11 09:00:00');
    
SET FOREIGN_KEY_CHECKS = 1;