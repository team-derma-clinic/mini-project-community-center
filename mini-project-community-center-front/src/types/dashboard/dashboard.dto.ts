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
  summary: {
    totalCourses?: number;
    totalSessions?: number;
    totalAttendance?: number;
    attendanceRate?: number;
  };
  upcomingSessions?: Array<{
    sessionId: number;
    courseName: string;
    startTime: string;
    endTime: string;
  }>;
  recentAttendance?: Array<{
    sessionId: number;
    courseName: string;
    attendanceCount: number;
    totalCount: number;
    attendanceRate: number;
  }>
}

export interface StaffDashboardResponse {
  summary: {
    totalCenters?: number;
    totalCourses?: number;
    totalEnrollments?: number;
    totalRefunds?: number;
    totalRevenue?: number;
  };
  centerStats?: Array<{
    centerId: number;
    courseName: string;
    courseCount: number;
    enrollmentCount: number;
  }>;
  recentEnrollments?: Array<{
    enrollmentId: number;
    courseName: string;
    userName: string;
    createdAt: string;
  }>
}
