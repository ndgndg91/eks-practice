export interface AuthenticationResponse {
  jwt: string;
  id: number;
  email: string;
  fullName: string;
  phone: string;
  lastLoginAt: string;
  updatedAt: string;
  createdAt: string
}
