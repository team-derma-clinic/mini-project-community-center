export interface SystemHealthResponse {
  status: "UP" | "DOWN";
  timestamp?: string;
  version?: string;
  database?: {
    status: "UP" | "DOWN";
    responseTime?: number;
  };
}

export interface SystemInfoResponse {
  version: string;
  name: string;
  description?: string;
  environment: "development" | "staging" | "production";
  buildTime?: string;
  uptime?: number;
}
