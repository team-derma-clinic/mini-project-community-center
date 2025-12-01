export interface SystemHealthResponse {
  status: string;
  timestamp: string;
  version: string;
  database: DatabaseHealthResponse;
}

export interface DatabaseHealthResponse {
  status: string;
  responseTime: number;
}

export interface SystemInfoResponse {
  version: string;
  name: string;
  description: string;
  environment: string;
  buildTime: string;
  uptime: number;
}
