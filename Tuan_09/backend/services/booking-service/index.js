import {
  HttpError,
  createJsonServer,
  created,
  defineRoute,
  ok,
  requireFields,
  startService,
} from "../../shared/http.js";
import { createId } from "../../shared/ids.js";

const PORT = Number(process.env.PORT || 8083);
const HOST = process.env.HOST || "0.0.0.0";

const bookings = [];

const routes = [
  defineRoute("GET", "/health", () => ok({ success: true, service: "booking-service" })),

  defineRoute("GET", "/bookings", ({ log }) => {
    log("LIST bookings", { count: bookings.length });
    return ok({ success: true, bookings });
  }),

  defineRoute("POST", "/bookings", ({ body, log }) => {
    requireFields(body, ["userId", "tourId", "quantity", "totalAmount"]);
    log("CREATE booking requested", {
      userId: body.userId,
      tourId: body.tourId,
      quantity: body.quantity,
      totalAmount: body.totalAmount,
    });

    if (Number(body.quantity) <= 0) {
      log("CREATE booking failed", { reason: "INVALID_QUANTITY", quantity: body.quantity });
      throw new HttpError(400, "Số lượng phải lớn hơn 0");
    }

    const booking = {
      id: createId("BK"),
      userId: body.userId,
      tourId: body.tourId,
      quantity: Number(body.quantity),
      totalAmount: Number(body.totalAmount),
      status: "PENDING_PAYMENT",
      createdAt: new Date().toISOString(),
    };

    bookings.push(booking);
    log("CREATE booking success", {
      bookingId: booking.id,
      status: booking.status,
      totalAmount: booking.totalAmount,
    });

    return created({
      success: true,
      booking,
    });
  }),

  defineRoute("PATCH", "/bookings/:id/status", ({ params, body, log }) => {
    requireFields(body, ["status"]);
    log("UPDATE booking status requested", { bookingId: params.id, status: body.status });

    const booking = bookings.find((item) => item.id === params.id);

    if (!booking) {
      log("UPDATE booking status failed", { bookingId: params.id });
      throw new HttpError(404, "Không tìm thấy booking");
    }

    booking.status = body.status;
    booking.updatedAt = new Date().toISOString();
    log("UPDATE booking status success", { bookingId: booking.id, status: booking.status });

    return ok({
      success: true,
      booking,
    });
  }),
];

startService(createJsonServer({ serviceName: "booking-service", routes }), {
  name: "Booking Service",
  host: HOST,
  port: PORT,
});
