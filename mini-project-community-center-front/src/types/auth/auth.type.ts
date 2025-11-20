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

export interface PasswordChangeFrom {
  password: string;
  newPassword: string;
}