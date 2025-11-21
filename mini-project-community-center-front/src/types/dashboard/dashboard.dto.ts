export interface SearchInstructorDashboard {
  from?: string;
  to?: string;
}

export interface SearchStaffDashboard {
  centerId?: number;
  from?: string;
  to?: string;
}

export interface InstructorDashboardResponse {
  totalCourses?: number;
  totalSessions?: number;
  totalAttendance?: number;
  attendanceRate?: number;
}

export interface StaffDashboardResponse {
  totalCenters?: number;
  totalCourses?: number;
  totalEnrollments?: number;
  totalRefunds?: number;
  totalRevenue?: number;
}
