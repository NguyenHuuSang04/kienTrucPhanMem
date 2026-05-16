import { makeClient, USER_ID } from "./client";
import type { IOrder } from "../types";

const client = makeClient(import.meta.env.VITE_PU3_URL);

export const orderApi = {
  checkout: () =>
    client.post<IOrder>("/checkout", { userId: USER_ID }).then((r) => r.data),
  listMine: () =>
    client.get<IOrder[]>(`/orders/${USER_ID}`).then((r) => r.data),
};
