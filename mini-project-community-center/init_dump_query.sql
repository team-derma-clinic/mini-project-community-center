-- 스키마 생성
DROP DATABASE IF EXISTS    `mini-project-community-center`;
CREATE DATABASE IF NOT EXISTS    `mini-project-community-center`
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

-- 0) 파일 정보
CREATE TABLE `file_infos` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
          
	original_name VARCHAR(255) NOT NULL COMMENT '원본 파일명',
	stored_name VARCHAR(255) NOT NULL COMMENT 'UUID가 저장된 파일명',
	content_type VARCHAR(255),
	file_size BIGINT,
	file_path VARCHAR(255) NOT NULL COMMENT '서버 내 실제 경로',
        
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
) 	
	ENGINE=InnoDB
	DEFAULT CHARSET = utf8mb4
	COLLATE = utf8mb4_unicode_ci
	COMMENT = '파일 정보 테이블';    

-- 1) 공통: 사용자/권한
CREATE TABLE `users` (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	
	name VARCHAR(50) NOT NULL,
	login_id VARCHAR(50) NOT NULL,
	password VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	phone VARCHAR(30) NULL,
    role VARCHAR(30) NOT NULL,
    role_status VARCHAR(30) NOT NULL,
    provider VARCHAR(20) NOT NULL DEFAULT 'LOCAL',
    provider_id VARCHAR(100) NULL,
    email_verified BOOLEAN NOT NULL DEFAULT 1,
	
	created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
	updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
	
	UNIQUE KEY `uk_users_login` (login_id),
	UNIQUE KEY `uk_users_email` (email),
    
    CHECK (role IN ('STUDENT','INSTRUCTOR','STAFF', 'ADMIN')),
    CHECK (role_status IN ('PENDING','APPROVED','REJECTED')),
    CHECK (provider IN ('LOCAL','GOOGLE','KAKAO', 'NAVER'))
)
	ENGINE=InnoDB
	DEFAULT CHARSET=utf8mb4
	COLLATE=utf8mb4_unicode_ci
	COMMENT='사용자';

-- 2) refresh token
CREATE TABLE `refresh_tokens`(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE COMMENT '사용자 ID',
    token VARCHAR(350) NOT NULL COMMENT '리프레시 토큰 값',
    expiry DATETIME(6) NOT NULL COMMENT '만료 시간',
    
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    
    INDEX `idx_refresh_token_user_id`(user_id),
    
    CONSTRAINT `fk_refresh_token_user` FOREIGN KEY (user_id) REFERENCES users(id)
) 	ENGINE=InnoDB
	DEFAULT CHARSET = utf8mb4
	COLLATE = utf8mb4_unicode_ci
    COMMENT = '리프레시 토큰 저장 테이블';
    
-- 3) 센터/강좌
CREATE TABLE `centers` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    
    name VARCHAR(120) NOT NULL,
    address VARCHAR(255) NULL,
    latitude DECIMAL(10,7) NULL,
    longitude DECIMAL(10,7) NULL,
    phone VARCHAR(30) NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE ,
    
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    
    INDEX `idx_centers_geo` (latitude, longitude)
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='지점(센터)';

CREATE TABLE `courses` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    
    center_id BIGINT NOT NULL,
    
    title VARCHAR(200) NOT NULL,
    category VARCHAR(50) NOT NULL,     -- ART/MUSIC/COOKING/FITNESS/IT/LANGUAGE
    level VARCHAR(20) NOT NULL DEFAULT 'BEGINNER', -- BEGINNER/INTERMEDIATE/ADVANCED
    capacity INT NOT NULL,    -- 강좌 정원(세션 공통)
    fee DECIMAL(10,2) NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN', -- OPEN/CLOSED/CANCELED
    description TEXT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    thumbnail_id BIGINT NULL,
    
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    
    CONSTRAINT `fk_course_center` FOREIGN KEY (center_id) REFERENCES centers(id),
    
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

CREATE TABLE `course_files` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    
    course_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL,
    display_order INT DEFAULT 0,
    
    CONSTRAINT `fk_course_files_course` FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT `fk_course_files_file_info` FOREIGN KEY (file_id) REFERENCES file_infos(id) ON DELETE CASCADE
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='강좌 파일 매핑';

CREATE TABLE `course_instructors` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    
    course_id BIGINT NOT NULL,
    instructor_id BIGINT NOT NULL,
    
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    
    CONSTRAINT `fk_course_instructor_course` FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT `fk_course_instructor_instructor` FOREIGN KEY (instructor_id) REFERENCES users(id),
    
    UNIQUE KEY `uk_course_instructor` (course_id, instructor_id),
    
    INDEX `idx_course_instructor_course` (course_id),
    INDEX `idx_course_instructor_instructor` (instructor_id)
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='강좌-강사 매핑';

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
    
    CONSTRAINT `fk_course_session_course` FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    
    CHECK (status IN ('SCHEDULED','DONE','CANCELED')),
    
    UNIQUE KEY `uk_session_unique` (course_id, start_time, end_time),
    
    INDEX `idx_sessions_time` (start_time, end_time)
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='강좌 회차(세션)';

-- 5) 수강 등록
CREATE TABLE `enrollments` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    
    course_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING/CONFIRMED/CANCELED/REFUNDED
    cancel_reason VARCHAR(200) NULL,
    
    enrolled_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    
    CONSTRAINT `fk_enrollment_course` FOREIGN KEY (course_id) REFERENCES courses(id),
    CONSTRAINT `fk_enrollment_user` FOREIGN KEY (user_id) REFERENCES users(id),
    
    UNIQUE KEY `uk_enroll_once` (course_id, user_id),    -- 1강좌당 1회 등록 원칙
    
    CHECK (status IN ('PENDING','CONFIRMED','CANCELED','REFUNDED')),
    
    INDEX `idx_enroll_course_status` (course_id, status),
    INDEX `idx_enroll_user` (user_id, status)
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='수강 등록';

-- 6) 결제(모의)
CREATE TABLE `payments` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    
    enrollment_id BIGINT NOT NULL,
    
	order_id VARCHAR(100) NOT NULL,
    payment_key VARCHAR(100) NOT NULL,
    
    amount DECIMAL(10,2) NOT NULL,
    currency CHAR(3) NOT NULL DEFAULT 'KRW',
    method VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    
	failure_code VARCHAR(50) NULL,
    failure_message VARCHAR(255) NULL,
    
    requested_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    approvded_at DATETIME(6) NULL,
    cancelled_at DATETIME(6) NULL,
    
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    
    CONSTRAINT `fk_payment_enrollment` FOREIGN KEY (enrollment_id) REFERENCES enrollments(id),
    
    UNIQUE KEY `uk_pay_enroll` (enrollment_id),
    
    INDEX `idx_pay_enroll` (enrollment_id, status),
    INDEX `idx_pay_requested_at` (requested_at)
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='결제 내역';

CREATE TABLE payment_refunds (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	payment_id BIGINT NOT NULL,
    
    amount BIGINT NOT NULL,

    status VARCHAR(30) NOT NULL,
    
    failure_code VARCHAR(50) NULL,
    failure_message VARCHAR(255) NULL,
    
    requested_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    completed_at DATETIME(6) NULL,
    
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    
    INDEX `idx_payment_refunds_payment_id` (payment_id),
    
    CONSTRAINT `fk_payment_refunds_payment` FOREIGN KEY (payment_id) REFERENCES payments(id)
)
	ENGINE=InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = '결제 내역 테이블';

-- 7) 출석
CREATE TABLE `attendance` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    
    session_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    
    status VARCHAR(15) NOT NULL DEFAULT 'ABSENT', -- PRESENT/LATE/ABSENT
    note VARCHAR(255) NULL,
    
    marked_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    
    CONSTRAINT `fk_attendance_session` FOREIGN KEY (session_id) REFERENCES course_sessions(id) ON DELETE CASCADE,
    CONSTRAINT `fk_attendance_user` FOREIGN KEY (user_id) REFERENCES users(id),
    
    CHECK (status IN ('PRESENT','LATE','ABSENT')),
    
    UNIQUE KEY `uk_attend_once` (session_id, user_id),
    
    INDEX `idx_attend_session` (session_id, status)
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='출석 기록';

-- 8) 리뷰
CREATE TABLE `reviews` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    
    course_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    
    rating TINYINT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    content TEXT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    
    CONSTRAINT `fk_review_course` FOREIGN KEY (course_id) REFERENCES courses(id),
    CONSTRAINT `fk_review_user` FOREIGN KEY (user_id) REFERENCES users(id),
    
    CHECK (status IN ('DRAFT', 'APPROVED', 'REJECTED')),
    
    UNIQUE KEY `uk_review_once` (course_id, user_id),
    
    INDEX `idx_review_course` (course_id, rating)
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='수강 후기';
    
CREATE TABLE `review_files` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    
    review_id BIGINT NOT NULL,
    file_id BIGINT NOT NULL,
    display_order INT DEFAULT 0,
    
    CONSTRAINT `fk_review_files_review` FOREIGN KEY (review_id) REFERENCES reviews(id) ON DELETE CASCADE,
    CONSTRAINT `fk_review_files_file_info` FOREIGN KEY (file_id) REFERENCES file_infos(id) ON DELETE CASCADE
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci
    COMMENT='리뷰 파일 매핑';


-- 파일 정보 (샘플 이미지/첨부 파일)
INSERT INTO file_infos (id, original_name, stored_name, content_type, file_size, file_path, created_at) VALUES
 (1, 'watercolor-thumb.jpg', 'f1-watercolor-thumb.jpg', 'image/jpeg', 240123, 'https://images.unsplash.com/photo-1473181488821-2d23949a045a?w=800', NOW()),
 (2, 'guitar-thumb.jpg', 'f2-guitar-thumb.jpg', 'image/jpeg', 201111, 'https://images.unsplash.com/photo-1464375117522-1311d6a5b81f?w=800', NOW()),
 (3, 'web-thumb.jpg', 'f3-web-thumb.jpg', 'image/jpeg', 311222, 'https://images.unsplash.com/photo-1505682634904-d7c8d95cdc50?w=800', NOW()),
 (4, 'pilates-thumb.jpg', 'f4-pilates-thumb.jpg', 'image/jpeg', 199233, 'https://images.unsplash.com/photo-1579758629938-03607ccdbaba?w=800', NOW()),
 (5, 'baking-thumb.jpg', 'f5-baking-thumb.jpg', 'image/jpeg', 255900, 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=800', NOW()),
 (6, 'english-thumb.jpg', 'f6-english-thumb.jpg', 'image/jpeg', 188822, 'https://images.unsplash.com/photo-1523580846011-d3a5bc25702b?w=800', NOW()),
 (7, 'course-guide.pdf', 'f7-course-guide.pdf', 'application/pdf', 522144, 'https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf', NOW()),
 (8, 'materials.zip', 'f8-materials.zip', 'application/zip', 1345123, 'https://github.com/github/gitignore/archive/refs/heads/main.zip', NOW()),
 (9, 'pilates-routine.pdf', 'f9-pilates-routine.pdf', 'application/pdf', 412899, 'https://www.orimi.com/pdf-test.pdf', NOW()),
 (10,'baking-recipe.pdf', 'f10-baking-recipe.pdf', 'application/pdf', 388822, 'https://file-examples.com/storage/fe5b0ccbcfe202bd5f2ff7e/2017/10/file-example_PDF_500_kB.pdf', NOW()),
 (11,'feedback-photo.jpg', 'f11-feedback-photo.jpg', 'image/jpeg', 140220, 'https://images.unsplash.com/photo-1524504388940-b1c1722653e1?w=800', NOW()),
 (12,'feedback-photo2.jpg', 'f12-feedback-photo2.jpg', 'image/jpeg', 150220, 'https://images.unsplash.com/photo-1524504388940-9d4c6acccf18?w=800', NOW());

-- 사용자 (모든 계정 비밀번호: admin123)
INSERT INTO users (id, name, login_id, password, email, phone, role, role_status, provider, provider_id, email_verified, created_at, updated_at) VALUES
 (1, '관리자', 'admin', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'admin@example.com', '010-0000-0000', 'ADMIN', 'APPROVED', 'LOCAL', NULL, 1, NOW(), NOW()),
 (2, '운영자', 'staff01', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'staff01@example.com', '010-1000-0000', 'STAFF', 'APPROVED', 'LOCAL', NULL, 1, NOW(), NOW()),
 (3, '김강사', 'inst_kim', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'inst_kim@example.com', '010-2000-0000', 'INSTRUCTOR', 'APPROVED', 'LOCAL', NULL, 1, NOW(), NOW()),
 (4, '박강사', 'inst_park', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'inst_park@example.com', '010-2111-0000', 'INSTRUCTOR', 'APPROVED', 'LOCAL', NULL, 1, NOW(), NOW()),
 (5, '최강사', 'inst_choi', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'inst_choi@example.com', '010-2222-0000', 'INSTRUCTOR', 'APPROVED', 'LOCAL', NULL, 1, NOW(), NOW()),
 (11,'대기강사', 'inst_wait', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'inst_wait@example.com', '010-2333-0000', 'INSTRUCTOR', 'PENDING', 'LOCAL', NULL, 1, NOW(), NOW()),
 (12,'거절강사', 'inst_rej', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'inst_rej@example.com', '010-2444-0000', 'INSTRUCTOR', 'REJECTED', 'LOCAL', NULL, 0, NOW(), NOW()),
 (6, '이수강', 'std_lee', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'lee@student.com', '010-3000-0000', 'STUDENT', 'APPROVED', 'LOCAL', NULL, 1, NOW(), NOW()),
 (7, '한수강', 'std_han', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'han@student.com', '010-3111-0000', 'STUDENT', 'APPROVED', 'LOCAL', NULL, 1, NOW(), NOW()),
 (8, '임수강', 'std_lim', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'lim@student.com', '010-3222-0000', 'STUDENT', 'APPROVED', 'LOCAL', NULL, 1, NOW(), NOW()),
 (9, '조수강', 'std_jo', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'jo@student.com', '010-3333-0000', 'STUDENT', 'APPROVED', 'LOCAL', NULL, 1, NOW(), NOW()),
 (10,'윤수강', 'std_yoon', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'yoon@student.com', '010-3444-0000', 'STUDENT', 'APPROVED', 'LOCAL', NULL, 1, NOW(), NOW()),
 (13,'대기학생', 'std_wait', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'wait@student.com', '010-3555-0000', 'STUDENT', 'PENDING', 'LOCAL', NULL, 0, NOW(), NOW()),
 (14,'거절학생', 'std_rej', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'rej@student.com', '010-3666-0000', 'STUDENT', 'REJECTED', 'LOCAL', NULL, 0, NOW(), NOW()),
 (15,'구글학생', 'std_google', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'google@student.com', '010-3777-0000', 'STUDENT', 'APPROVED', 'GOOGLE', 'google_12345', 1, NOW(), NOW()),
 (16,'카카오학생', 'std_kakao', '$2a$12$Z2y9kgIF.oLo77A23DurDOzJx8Oudfh0mf.rqN/7tgIjXmXy5gL6O', 'kakao@student.com', '010-3888-0000', 'STUDENT', 'APPROVED', 'KAKAO', 'kakao_67890', 1, NOW(), NOW());

-- 센터
INSERT INTO centers (id, name, address, latitude, longitude, phone, is_active, created_at, updated_at) VALUES
 (1, '서울 아트 센터', '서울시 종로구 세종로 1', 37.5759, 126.9768, '02-100-0001', TRUE, NOW(), NOW()),
 (2, '부산 IT 센터', '부산시 해운대구 센텀중앙로 20', 35.1699, 129.1323, '051-200-0002', TRUE, NOW(), NOW()),
 (3, '인천 피트니스 센터', '인천시 연수구 송도과학로 50', 37.3822, 126.6431, '032-300-0003', TRUE, NOW(), NOW()),
 (4, '대구 쿠킹 스튜디오', '대구시 수성구 달구벌대로 2500', 35.8580, 128.6281, '053-400-0004', TRUE, NOW(), NOW()),
 (5, '광주 음악 센터', '광주시 동구 중앙로 196', 35.1466, 126.9200, '062-500-0005', FALSE, NOW(), NOW());

-- 강좌
INSERT INTO courses (id, center_id, title, category, level, capacity, fee, status, description, start_date, end_date, thumbnail_id, created_at, updated_at) VALUES
 (1, 1, '수채화 기초 클래스', 'ART', 'BEGINNER', 15, 120000, 'OPEN', '붓질과 색 번짐부터 배우는 기초 수채화 과정', '2025-01-10', '2025-02-28', 1, NOW(), NOW()),
 (2, 1, '어쿠스틱 기타 마스터', 'MUSIC', 'INTERMEDIATE', 12, 150000, 'OPEN', '코드 전환과 핑거스타일을 익히는 중급 기타', '2025-02-05', '2025-03-30', 2, NOW(), NOW()),
 (3, 2, '웹 개발 부트캠프', 'IT', 'BEGINNER', 20, 280000, 'OPEN', 'HTML/CSS/JS와 React 기초를 빠르게 익히는 부트캠프', '2025-01-15', '2025-03-31', 3, NOW(), NOW()),
 (4, 3, '필라테스 코어 집중', 'FITNESS', 'INTERMEDIATE', 18, 180000, 'OPEN', '매트와 소도구를 활용한 코어 강화 프로그램', '2025-01-20', '2025-03-10', 4, NOW(), NOW()),
 (5, 4, '홈베이킹 입문', 'COOKING', 'BEGINNER', 14, 160000, 'OPEN', '기본 반죽과 오븐 활용을 배우는 베이킹 첫걸음', '2025-02-01', '2025-03-20', 5, NOW(), NOW()),
 (6, 2, '영어 회화 트레이닝', 'LANGUAGE', 'BEGINNER', 16, 140000, 'OPEN', '상황별 표현과 롤플레이로 실전 회화 감각을 키우는 과정', '2025-01-25', '2025-03-25', 6, NOW(), NOW()),
 (7, 1, '유화 고급반', 'ART', 'ADVANCED', 10, 200000, 'CLOSED', '유화 기법과 색채 이론을 다루는 고급 과정', '2025-02-15', '2025-04-15', 1, NOW(), NOW()),
 (8, 2, '파이썬 데이터 분석', 'IT', 'INTERMEDIATE', 18, 250000, 'CLOSED', '판다스와 넘파이를 활용한 데이터 분석', '2025-01-20', '2025-03-20', 3, NOW(), NOW()),
 (9, 3, '요가 명상', 'FITNESS', 'BEGINNER', 20, 130000, 'CANCELED', '요가와 명상을 결합한 힐링 프로그램', '2025-02-10', '2025-04-10', 4, NOW(), NOW()),
 (10, 4, '파스타 마스터클래스', 'COOKING', 'ADVANCED', 8, 220000, 'OPEN', '이탈리아 전통 파스타 레시피와 기법', '2025-02-20', '2025-04-20', 5, NOW(), NOW()),
 (11, 1, '드럼 레슨', 'MUSIC', 'BEGINNER', 12, 170000, 'OPEN', '기초 리듬과 드럼 테크닉', '2025-03-01', '2025-04-30', 2, NOW(), NOW()),
 (12, 2, '일본어 회화', 'LANGUAGE', 'INTERMEDIATE', 14, 150000, 'CLOSED', '일상 회화와 비즈니스 일본어', '2025-01-30', '2025-03-30', 6, NOW(), NOW());

-- 강좌 파일 매핑
INSERT INTO course_files (course_id, file_id, display_order) VALUES
 (1, 7, 1),
 (1, 8, 2),
 (3, 8, 1),
 (4, 9, 1),
 (5, 10, 1);

-- 강사 매핑
INSERT INTO course_instructors (course_id, instructor_id, created_at, updated_at) VALUES
 (1, 3, NOW(), NOW()),
 (2, 3, NOW(), NOW()),
 (3, 4, NOW(), NOW()),
 (4, 5, NOW(), NOW()),
 (5, 5, NOW(), NOW()),
 (6, 4, NOW(), NOW()),
 (7, 3, NOW(), NOW()),
 (8, 4, NOW(), NOW()),
 (10, 5, NOW(), NOW()),
 (11, 3, NOW(), NOW()),
 (12, 4, NOW(), NOW());

-- 세션
INSERT INTO course_sessions (course_id, start_time, end_time, room, status, created_at, updated_at) VALUES
 (1, '2025-01-10 10:00:00', '2025-01-10 12:00:00', 'A-101', 'DONE', NOW(), NOW()),
 (1, '2025-01-24 10:00:00', '2025-01-24 12:00:00', 'A-101', 'DONE', NOW(), NOW()),
 (1, '2025-02-07 10:00:00', '2025-02-07 12:00:00', 'A-101', 'SCHEDULED', NOW(), NOW()),
 (2, '2025-02-05 19:00:00', '2025-02-05 21:00:00', 'M-201', 'DONE', NOW(), NOW()),
 (2, '2025-02-19 19:00:00', '2025-02-19 21:00:00', 'M-201', 'SCHEDULED', NOW(), NOW()),
 (2, '2025-03-05 19:00:00', '2025-03-05 21:00:00', 'M-201', 'SCHEDULED', NOW(), NOW()),
 (3, '2025-01-15 09:00:00', '2025-01-15 12:00:00', 'IT-301', 'DONE', NOW(), NOW()),
 (3, '2025-02-05 09:00:00', '2025-02-05 12:00:00', 'IT-301', 'DONE', NOW(), NOW()),
 (3, '2025-02-26 09:00:00', '2025-02-26 12:00:00', 'IT-301', 'SCHEDULED', NOW(), NOW()),
 (3, '2025-03-19 09:00:00', '2025-03-19 12:00:00', 'IT-301', 'SCHEDULED', NOW(), NOW()),
 (4, '2025-01-20 18:30:00', '2025-01-20 20:00:00', 'P-101', 'DONE', NOW(), NOW()),
 (4, '2025-02-03 18:30:00', '2025-02-03 20:00:00', 'P-101', 'DONE', NOW(), NOW()),
 (4, '2025-02-17 18:30:00', '2025-02-17 20:00:00', 'P-101', 'SCHEDULED', NOW(), NOW()),
 (5, '2025-02-01 14:00:00', '2025-02-01 16:00:00', 'C-102', 'DONE', NOW(), NOW()),
 (5, '2025-02-22 14:00:00', '2025-02-22 16:00:00', 'C-102', 'SCHEDULED', NOW(), NOW()),
 (5, '2025-03-15 14:00:00', '2025-03-15 16:00:00', 'C-102', 'SCHEDULED', NOW(), NOW()),
 (6, '2025-01-25 11:00:00', '2025-01-25 12:30:00', 'L-201', 'DONE', NOW(), NOW()),
 (6, '2025-02-15 11:00:00', '2025-02-15 12:30:00', 'L-201', 'SCHEDULED', NOW(), NOW()),
 (6, '2025-03-08 11:00:00', '2025-03-08 12:30:00', 'L-201', 'SCHEDULED', NOW(), NOW()),
 (7, '2025-02-15 14:00:00', '2025-02-15 17:00:00', 'A-201', 'CANCELED', NOW(), NOW()),
 (8, '2025-01-20 10:00:00', '2025-01-20 13:00:00', 'IT-302', 'DONE', NOW(), NOW()),
 (8, '2025-02-10 10:00:00', '2025-02-10 13:00:00', 'IT-302', 'DONE', NOW(), NOW()),
 (9, '2025-02-10 19:00:00', '2025-02-10 21:00:00', 'P-102', 'CANCELED', NOW(), NOW()),
 (10, '2025-02-20 15:00:00', '2025-02-20 18:00:00', 'C-201', 'SCHEDULED', NOW(), NOW()),
 (11, '2025-03-01 16:00:00', '2025-03-01 18:00:00', 'M-301', 'SCHEDULED', NOW(), NOW()),
 (12, '2025-01-30 13:00:00', '2025-01-30 15:00:00', 'L-301', 'DONE', NOW(), NOW());

-- 수강 등록
INSERT INTO enrollments (id, course_id, user_id, status, cancel_reason, enrolled_at) VALUES
 (1, 1, 6, 'CONFIRMED', NULL, NOW()),
 (2, 1, 7, 'CONFIRMED', NULL, NOW()),
 (3, 1, 8, 'PENDING', NULL, NOW()),
 (4, 1, 9, 'CANCELED', '일정 변경', NOW()),
 (5, 1, 10, 'REFUNDED', '개인 사정', NOW()),
 (6, 2, 7, 'CONFIRMED', NULL, NOW()),
 (7, 2, 8, 'CONFIRMED', NULL, NOW()),
 (8, 2, 9, 'PENDING', NULL, NOW()),
 (9, 2, 10, 'CANCELED', '다른 강좌 등록', NOW()),
 (10, 3, 6, 'CONFIRMED', NULL, NOW()),
 (11, 3, 9, 'CONFIRMED', NULL, NOW()),
 (12, 3, 10, 'PENDING', NULL, NOW()),
 (13, 3, 15, 'CONFIRMED', NULL, NOW()),
 (14, 3, 16, 'CANCELED', '비용 부담', NOW()),
 (15, 4, 6, 'CONFIRMED', NULL, NOW()),
 (16, 4, 8, 'CANCELED', '개인 사정', NOW()),
 (17, 4, 9, 'PENDING', NULL, NOW()),
 (18, 5, 9, 'CONFIRMED', NULL, NOW()),
 (19, 5, 10, 'PENDING', NULL, NOW()),
 (20, 5, 15, 'CONFIRMED', NULL, NOW()),
 (21, 6, 6, 'CONFIRMED', NULL, NOW()),
 (22, 6, 7, 'CONFIRMED', NULL, NOW()),
 (23, 6, 9, 'REFUNDED', '일정 변경', NOW()),
 (24, 7, 8, 'CONFIRMED', NULL, NOW()),
 (25, 7, 10, 'PENDING', NULL, NOW()),
 (26, 8, 6, 'CONFIRMED', NULL, NOW()),
 (27, 8, 7, 'CANCELED', '강의 내용 불만', NOW()),
 (28, 10, 9, 'CONFIRMED', NULL, NOW()),
 (29, 10, 10, 'PENDING', NULL, NOW()),
 (30, 11, 6, 'CONFIRMED', NULL, NOW()),
 (31, 11, 8, 'PENDING', NULL, NOW()),
 (32, 12, 7, 'CONFIRMED', NULL, NOW()),
 (33, 12, 9, 'REFUNDED', '취소 요청', NOW());

-- 결제
INSERT INTO payments (id, enrollment_id, order_id, payment_key, amount, currency, method, status, failure_code, failure_message, requested_at, approvded_at, cancelled_at, created_at, updated_at) VALUES
 (1, 1, 'ORD-202501-0001', 'PAY-KEY-0001', 120000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (2, 2, 'ORD-202501-0002', 'PAY-KEY-0002', 120000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (3, 3, 'ORD-202501-0003', 'PAY-KEY-0003', 120000, 'KRW', 'CARD', 'PENDING', NULL, NULL, NOW(), NULL, NULL, NOW(), NOW()),
 (4, 6, 'ORD-202502-0004', 'PAY-KEY-0004', 150000, 'KRW', 'TRANSFER', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (5, 7, 'ORD-202502-0005', 'PAY-KEY-0005', 150000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (6, 8, 'ORD-202502-0006', 'PAY-KEY-0006', 150000, 'KRW', 'CARD', 'PENDING', NULL, NULL, NOW(), NULL, NULL, NOW(), NOW()),
 (7, 9, 'ORD-202502-0007', 'PAY-KEY-0007', 150000, 'KRW', 'CARD', 'CANCELED', NULL, NULL, NOW(), NULL, NOW(), NOW(), NOW()),
 (8, 10, 'ORD-202501-0008', 'PAY-KEY-0008', 280000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (9, 11, 'ORD-202501-0009', 'PAY-KEY-0009', 280000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (10, 12, 'ORD-202501-0010', 'PAY-KEY-0010', 280000, 'KRW', 'CARD', 'PENDING', NULL, NULL, NOW(), NULL, NULL, NOW(), NOW()),
 (11, 13, 'ORD-202501-0011', 'PAY-KEY-0011', 280000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (12, 14, 'ORD-202501-0012', 'PAY-KEY-0012', 280000, 'KRW', 'CARD', 'FAILED', 'INSUFFICIENT_FUNDS', '잔액 부족', NOW(), NULL, NULL, NOW(), NOW()),
 (13, 15, 'ORD-202501-0013', 'PAY-KEY-0013', 180000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (14, 17, 'ORD-202501-0014', 'PAY-KEY-0014', 180000, 'KRW', 'CARD', 'PENDING', NULL, NULL, NOW(), NULL, NULL, NOW(), NOW()),
 (15, 18, 'ORD-202502-0015', 'PAY-KEY-0015', 160000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (16, 19, 'ORD-202502-0016', 'PAY-KEY-0016', 160000, 'KRW', 'CARD', 'PENDING', NULL, NULL, NOW(), NULL, NULL, NOW(), NOW()),
 (17, 20, 'ORD-202502-0017', 'PAY-KEY-0017', 160000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (18, 21, 'ORD-202501-0018', 'PAY-KEY-0018', 140000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (19, 22, 'ORD-202501-0019', 'PAY-KEY-0019', 140000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (20, 23, 'ORD-202501-0020', 'PAY-KEY-0020', 140000, 'KRW', 'CARD', 'CANCELED', NULL, NULL, NOW(), NULL, NOW(), NOW(), NOW()),
 (21, 24, 'ORD-202502-0021', 'PAY-KEY-0021', 200000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (22, 25, 'ORD-202502-0022', 'PAY-KEY-0022', 200000, 'KRW', 'CARD', 'PENDING', NULL, NULL, NOW(), NULL, NULL, NOW(), NOW()),
 (23, 26, 'ORD-202501-0023', 'PAY-KEY-0023', 250000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (24, 28, 'ORD-202502-0024', 'PAY-KEY-0024', 220000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (25, 29, 'ORD-202502-0025', 'PAY-KEY-0025', 220000, 'KRW', 'CARD', 'PENDING', NULL, NULL, NOW(), NULL, NULL, NOW(), NOW()),
 (26, 30, 'ORD-202503-0026', 'PAY-KEY-0026', 170000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (27, 31, 'ORD-202503-0027', 'PAY-KEY-0027', 170000, 'KRW', 'CARD', 'FAILED', 'CARD_EXPIRED', '카드 만료', NOW(), NULL, NULL, NOW(), NOW()),
 (28, 32, 'ORD-202501-0028', 'PAY-KEY-0028', 150000, 'KRW', 'CARD', 'DONE', NULL, NULL, NOW(), NOW(), NULL, NOW(), NOW()),
 (29, 33, 'ORD-202501-0029', 'PAY-KEY-0029', 150000, 'KRW', 'CARD', 'CANCELED', NULL, NULL, NOW(), NULL, NOW(), NOW(), NOW());

-- 환불
INSERT INTO payment_refunds (payment_id, amount, status, failure_code, failure_message, requested_at, completed_at, created_at, updated_at) VALUES
 (7, 150000, 'COMPLETED', NULL, NULL, NOW(), NOW(), NOW(), NOW()),
 (20, 140000, 'COMPLETED', NULL, NULL, NOW(), NOW(), NOW(), NOW()),
 (23, 250000, 'PENDING', NULL, NULL, NOW(), NULL, NOW(), NOW()),
 (29, 150000, 'COMPLETED', NULL, NULL, NOW(), NOW(), NOW(), NOW()),
 (12, 280000, 'FAILED', 'REFUND_ERROR', '환불 처리 오류', NOW(), NULL, NOW(), NOW());

-- 출석석
INSERT INTO attendance (session_id, user_id, status, note, marked_at) VALUES
 (1, 6, 'PRESENT', NULL, NOW()),
 (1, 7, 'LATE', '10분 지각', NOW()),
 (2, 6, 'PRESENT', NULL, NOW()),
 (2, 7, 'PRESENT', NULL, NOW()),
 (4, 7, 'PRESENT', NULL, NOW()),
 (4, 8, 'ABSENT', '출장', NOW()),
 (7, 6, 'PRESENT', NULL, NOW()),
 (7, 9, 'PRESENT', NULL, NOW()),
 (7, 13, 'LATE', '교통 지연', NOW()),
 (8, 6, 'PRESENT', NULL, NOW()),
 (8, 9, 'LATE', '장소 착각', NOW()),
 (8, 13, 'PRESENT', NULL, NOW()),
 (10, 6, 'ABSENT', '개인 사정', NOW()),
 (10, 9, 'PRESENT', NULL, NOW()),
 (10, 13, 'PRESENT', NULL, NOW()),
 (11, 6, 'PRESENT', NULL, NOW()),
 (11, 9, 'LATE', '5분 지각', NOW()),
 (13, 6, 'PRESENT', NULL, NOW()),
 (13, 8, 'ABSENT', '몸살', NOW()),
 (13, 15, 'PRESENT', NULL, NOW()),
 (16, 6, 'PRESENT', NULL, NOW()),
 (16, 7, 'PRESENT', NULL, NOW()),
 (16, 9, 'ABSENT', '환불 처리', NOW()),
 (19, 6, 'PRESENT', NULL, NOW()),
 (19, 9, 'PRESENT', NULL, NOW()),
 (20, 6, 'PRESENT', NULL, NOW()),
 (20, 9, 'LATE', '15분 지각', NOW()),
 (21, 6, 'PRESENT', NULL, NOW()),
 (21, 9, 'PRESENT', NULL, NOW()),
 (24, 9, 'PRESENT', NULL, NOW()),
 (24, 10, 'ABSENT', '불참', NOW()),
 (26, 6, 'PRESENT', NULL, NOW()),
 (26, 7, 'PRESENT', NULL, NOW()),
 (27, 7, 'PRESENT', NULL, NOW()),
 (27, 9, 'ABSENT', '개인 사정', NOW());

-- 리뷰
INSERT INTO reviews (id, course_id, user_id, rating, content, status, created_at) VALUES
 (1, 1, 6, 5, '기초부터 차근차근 배울 수 있어요. 추천합니다!', 'APPROVED', NOW()),
 (2, 1, 7, 4, '수업이 알찼고 과제 피드백이 좋았어요.', 'APPROVED', NOW()),
 (3, 2, 7, 5, '강사님이 친절하고 실습 위주라 재미있었습니다.', 'APPROVED', NOW()),
 (4, 2, 8, 3, '난이도가 생각보다 높았어요.', 'APPROVED', NOW()),
 (5, 3, 6, 4, '내용이 많아서 힘들었지만 성장했습니다.', 'APPROVED', NOW()),
 (6, 3, 9, 5, '실무에 바로 적용 가능한 내용이었습니다.', 'APPROVED', NOW()),
 (7, 3, 13, 2, '강의 속도가 너무 빨랐어요.', 'REJECTED', NOW()),
 (8, 4, 6, 5, '코어 강화에 정말 도움이 되었어요!', 'APPROVED', NOW()),
 (9, 5, 9, 5, '집에서도 바로 따라할 수 있는 레시피가 유용했어요.', 'APPROVED', NOW()),
 (10, 5, 20, 4, '베이킹 기초를 탄탄히 다질 수 있었습니다.', 'DRAFT', NOW()),
 (11, 6, 6, 3, '회화 연습 시간이 더 많았으면 좋겠습니다.', 'APPROVED', NOW()),
 (12, 6, 7, 4, '실용적인 표현을 많이 배웠어요.', 'APPROVED', NOW()),
 (13, 8, 6, 5, '데이터 분석 실력이 크게 향상되었습니다.', 'APPROVED', NOW()),
 (14, 10, 9, 4, '파스타 만드는 법을 제대로 배웠어요.', 'APPROVED', NOW()),
 (15, 12, 7, 1, '강의 내용이 기대에 못 미쳤습니다.', 'REJECTED', NOW());

-- 리뷰 파일
INSERT INTO review_files (review_id, file_id, display_order) VALUES
 (1, 11, 1),
 (2, 12, 1),
 (5, 11, 1);
    
SET FOREIGN_KEY_CHECKS = 1;