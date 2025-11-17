export const ROOT = {
  CENTERS: '/centers',
  COURSES: '/courses',
  SESSIONS: '/sessions',

  ENROLLMENTS: '/enrollments'
}


export const API_ROUTES = {
  CENTERS: {
    BY_CENTER_ID: (centerId: number) => `${ROOT.CENTERS}/${centerId}`
  },
  COURSES: {
    BY_COURSE_ID: (courseId: number) => `${ROOT.COURSES}/${courseId}`,
    STATUS: (courseId: number) => `${ROOT.COURSES}/${courseId}/status`
  },
  SESSIONS: {
    BY_COURSE_ID: (courseId: number) => `${ROOT.COURSES}/${courseId}/sessions`,
    BY_SESSION_ID: (sessionId: number) => `${ROOT.SESSIONS}/${sessionId}`,
    STATUS: (sessionId: number) => `${ROOT.SESSIONS}/${sessionId}/status`
  },

  ENROLLMENTS: {
    ME: ROOT.ENROLLMENTS + '/me',
  },
  PAYMENTS: {

  },

}
