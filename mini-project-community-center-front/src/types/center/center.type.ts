export interface CenterCreateForm {
  name: string;
  address?: string;
  latitude?: number;
  longitude?: number;
  phone?: string;
}

export interface CenterUpdateForm {
  name?: string;
  address?: string;
  latitude?: number;
  longitude?: number;
  phone?: string;
}