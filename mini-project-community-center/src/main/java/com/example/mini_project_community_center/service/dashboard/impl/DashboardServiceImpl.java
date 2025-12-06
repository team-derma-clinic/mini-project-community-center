//package com.example.mini_project_community_center.service.dashboard.impl;
//
//import com.example.mini_project_community_center.dto.ResponseDto;
//import com.example.mini_project_community_center.dto.dashboard.request.SearchInstructorDashboardRequest;
//import com.example.mini_project_community_center.dto.dashboard.request.SearchStaffDashboardRequest;
//import com.example.mini_project_community_center.dto.dashboard.response.InstructorDashboardResponse;
//import com.example.mini_project_community_center.dto.dashboard.response.ReportSummary;
//import com.example.mini_project_community_center.dto.dashboard.response.StaffDashboardResponse;
//import com.example.mini_project_community_center.dto.report.request.CourseReportRequest;
//import com.example.mini_project_community_center.dto.report.response.CourseReportResponse;
//import com.example.mini_project_community_center.entity.course.Course;
//import com.example.mini_project_community_center.repository.attendance.AttendanceRepository;
//import com.example.mini_project_community_center.repository.center.CenterRepository;
//import com.example.mini_project_community_center.repository.course.course.CourseRepository;
//import com.example.mini_project_community_center.repository.enrollment.EnrollmentRepository;
//import com.example.mini_project_community_center.repository.payment.PaymentRepository;
//import com.example.mini_project_community_center.security.user.UserPrincipal;
//import com.example.mini_project_community_center.service.dashboard.DashboardService;
//import com.example.mini_project_community_center.service.report.ReportService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class DashboardServiceImpl implements DashboardService {
//    private final CourseRepository courseRepository;
//    private final AttendanceRepository attendanceRepository;
//    private final CenterRepository centerRepository;
//    private final EnrollmentRepository enrollmentRepository;
//    private final PaymentRepository paymentRepository;
//    private final ReportService reportService;
//
//    @Override
//    public ResponseDto<InstructorDashboardResponse> getInstructorDashboard(UserPrincipal userPrincipal, SearchInstructorDashboardRequest req) {
//        Long instructorId = userPrincipal.getId();
//
//        List<Course> instructorCourses = courseRepository.findByInstructorId(instructorId);
//
//        long totalCourses = instructorCourses.size();
//
//        long totalSessions = instructorCourses.stream()
//                .mapToLong(course -> course.getSessions().size())
//                .sum();
//
//        List<Long> instructorCourseIds = instructorCourses.stream()
//                .map(Course::getId)
//                .toList();
//
//        long totalAttendance = attendanceRepository.findAll().stream()
//                .filter(attendance -> {
//                    Long courseId = attendance.getSession().getCourse().getId();
//                    return instructorCourseIds.contains(courseId);
//                })
//                .filter(attendance -> attendance.getStatus().name().equals("PRESENT"))
//                .count();
//
//        double attendanceRate = totalSessions > 0 ? (double) totalAttendance / totalSessions * 100 : 0.0;
//
//        InstructorDashboardResponse data = new InstructorDashboardResponse(
//                (int) totalCourses,
//                (int) totalSessions,
//                (int) totalAttendance,
//                attendanceRate
//        );
//        return ResponseDto.success(data);
//    }
//
//    @Override
//    public ResponseDto<StaffDashboardResponse> getStaffDashboard(UserPrincipal userPrincipal, SearchStaffDashboardRequest req) {
//        int totalCenters = (int) centerRepository.count();
//        int totalCourses = (int) courseRepository.count();
//        int totalEnrollments = (int) enrollmentRepository.count();
//        int totalRefunds = countRefundedPayments();
//        ReportSummary reportSummary = calculateReportSummary(req);
//
//        StaffDashboardResponse data = new StaffDashboardResponse(
//                totalCenters,
//                totalCourses,
//                totalEnrollments,
//                totalRefunds,
//                reportSummary
//        );
//
//        return ResponseDto.success(data);
//    }
//
//    private int countRefundedPayments() {
//        return (int) paymentRepository.findAll().stream()
//                .filter(payment -> payment.getStatus().name().equals("REFUNDED"))
//                .count();
//    }
//
//    private ReportSummary calculateReportSummary(SearchStaffDashboardRequest req) {
//        List<CourseReportResponse> courseReports = getCourseReports(req.centerId(), req.from(), req.to());
//
//        return new ReportSummary(
//                calculateCourseReportSummary(courseReports)
//        );
//    }
//
//    private List<CourseReportResponse> getCourseReports(Long centerId, String from, String to) {
//        CourseReportRequest req = new CourseReportRequest(centerId, from, to, null, null);
//        ResponseDto<List<CourseReportResponse>> response = reportService.getCourseReport(req);
//        return response.getData() != null ? response.getData() : List.of();
//    }
//
//    private ReportSummary.CourseReportSummary calculateCourseReportSummary(List<CourseReportResponse> reports) {
//        if (reports.isEmpty()) return null;
//
//        double avgEnrollmentRate = reports.stream().mapToDouble(CourseReportResponse::enrollmentRate).average().orElse(0.0);
//        CourseReportResponse topCourse = reports.stream()
//                .max((a, b) -> Double.compare(a.enrollmentRate(), b.enrollmentRate()))
//                .orElse(reports.get(0));
//
//        return new ReportSummary.CourseReportSummary(
//                avgEnrollmentRate,
//                new ReportSummary.TopCourse(topCourse.courseName(), topCourse.enrollmentRate(), null)
//        );
//    }
//}
