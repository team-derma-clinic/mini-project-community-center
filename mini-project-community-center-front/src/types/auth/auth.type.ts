export interface SignupForm {
  loginId: string;
  password: string;
  name: string;
  email: string;
  phone?: string;
}

export interface LoginFrom {
  loginId: string;
  password: string;
}

export interface PasswordResetFrom {
  newPassword: string;
  confirmPassword: string;
}