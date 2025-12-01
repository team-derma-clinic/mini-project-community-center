export interface SearchInstructorDashboardRequest {
  from?: string;
  to?: string;
}

export interface SearchStaffDashboardRequest {
  centerId?: number;
  from?: string;
  to?: string;
}

export interface InstructorDashboardResponse {
  totalCourses: number;
  totalSession?: number;
  totalAttendance: number;
  attendanceRate: number;
}

export interface StaffDashboardResponse {
  totalCenters: number;
  totalCourses: number;
  totalEnrollments: number;
  totalRefunds: number;
  totalRevenue: number;
}

export interface ReportSummary {
  courseReport: CourseReportSummary;
  attendanceReport: AttendanceReportSummary;
  categoryReport: CategoryReportSummary;
  instructorReport: InstructorReportSummary;
}

export interface CourseReportSummary {
  averageEnrollmentRate: number;
  topCourse: TopCourse;
}

export interface AttendanceReportSummary {
  averageAttendance: number;
  topCourse: TopCourse;
}

export interface CategoryReportSummary {
  averageRating: number;
  topCategory: TopCategory;
}

export interface InstructorReportSummary {
  averageRating: number;
  topInstructor: TopInstructor;
}

export interface TopCourse {
  title: string;
  enrollmentRate: number;
  attendanceRate: number;
}

export interface TopCategory {
  category: string;
  enrolledCount: number;
  averageRating: number;
}

export interface TopInstructor {
  name: string;
  rating: number;
  courseCount: number;
}