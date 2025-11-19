export interface SystemHealthResponse {
  satus: "UP" | "DOWN";
  timestamp?: string;
  version?: string;
  database?: {
    satus: "UP" | "DOWN";
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
