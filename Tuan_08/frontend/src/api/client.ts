import axios, { AxiosInstance } from "axios";

export function makeClient(baseURL: string): AxiosInstance {
  return axios.create({
    baseURL,
    timeout: 5000,
    headers: { "Content-Type": "application/json" },
  });
}

export const USER_ID = import.meta.env.VITE_USER_ID ?? "u1";
