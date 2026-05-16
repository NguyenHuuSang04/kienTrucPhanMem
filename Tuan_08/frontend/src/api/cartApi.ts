import { makeClient, USER_ID } from "./client";
import type { ICart, IAddCartReq } from "../types";

const client = makeClient(import.meta.env.VITE_PU2_URL);

export const cartApi = {
  add: (productId: string, quantity = 1) =>
    client
      .post<ICart>("/cart/add", {
        userId: USER_ID,
        productId,
        quantity,
      } satisfies IAddCartReq)
      .then((r) => r.data),
  get: () =>
    client
      .get<ICart>("/cart", { params: { userId: USER_ID } })
      .then((r) => r.data),
  clear: () =>
    client.delete<{ deleted: boolean }>(`/cart/${USER_ID}`).then((r) => r.data),
};
