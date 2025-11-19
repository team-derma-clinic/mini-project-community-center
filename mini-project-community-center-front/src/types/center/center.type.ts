export interface CreateCenterForm {
  name: string;
  address?: string;
  latitude?: number;
  longitude?: number;
  phone?: string;
}

export interface UpdateCenterForm {
  name?: string;
  address?: string;
  latitude?: number;
  longitude?: number;
  phone?: string;
}