import {
  createJsonServer,
  created,
  defineRoute,
  ok,
  requireFields,
  startService,
} from "../../shared/http.js";
import { createId } from "../../shared/ids.js";

const PORT = Number(process.env.PORT || 8084);
const HOST = process.env.HOST || "0.0.0.0";

const payments = [];

const routes = [
  defineRoute("GET", "/health", () => ok({ success: true, service: "payment-service" })),

  defineRoute("GET", "/payments", ({ log }) => {
    log("LIST payments", { count: payments.length });
    return ok({ success: true, payments });
  }),

  defineRoute("POST", "/payments", ({ body, log }) => {
    requireFields(body, ["bookingId", "amount"]);
    log("PROCESS payment requested", {
      bookingId: body.bookingId,
      amount: body.amount,
      method: body.method || "CASH",
    });

    const isSuccess = Math.random() >= 0.35;
    const payment = {
      id: createId("PAY"),
      bookingId: body.bookingId,
      amount: Number(body.amount),
      method: body.method || "CASH",
      status: isSuccess ? "SUCCESS" : "FAIL",
      message: isSuccess ? "Thanh toán thành công" : "Thanh toán thất bại theo kết quả random",
      createdAt: new Date().toISOString(),
    };

    payments.push(payment);
    log("PROCESS payment completed", {
      paymentId: payment.id,
      bookingId: payment.bookingId,
      status: payment.status,
      amount: payment.amount,
    });

    return created({
      success: true,
      payment,
    });
  }),
];

startService(createJsonServer({ serviceName: "payment-service", routes }), {
  name: "Payment Service",
  host: HOST,
  port: PORT,
});
