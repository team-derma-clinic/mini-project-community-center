export interface EnrollmentCreateForm {
  courseId: number;
  method: 'CARD' | 'TRANSFER';
}