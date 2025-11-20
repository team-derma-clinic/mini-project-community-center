export interface UserCreateForm {
  name: string;
  loginId: string;
  password: string;
  email: string;
  phone?: string;
}

export interface UserUpdateForm {
  email?: string;
  phone?: string;
}