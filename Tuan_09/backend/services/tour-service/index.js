import {
  HttpError,
  createJsonServer,
  defineRoute,
  ok,
  startService,
} from "../../shared/http.js";

const PORT = Number(process.env.PORT || 8082);
const HOST = process.env.HOST || "0.0.0.0";

const tours = [
  {
    id: "t1",
    name: "Đà Lạt 3 ngày 2 đêm",
    location: "Đà Lạt",
    price: 2500000,
    duration: "3 ngày 2 đêm",
    availableSlots: 12,
    description: "Tham quan Lang Biang, hồ Xuân Hương, chợ đêm và các vườn hoa.",
  },
  {
    id: "t2",
    name: "Phú Quốc biển xanh",
    location: "Phú Quốc",
    price: 4200000,
    duration: "4 ngày 3 đêm",
    availableSlots: 8,
    description: "Nghỉ dưỡng biển, tham quan Nam Đảo, cáp treo Hòn Thơm.",
  },
  {
    id: "t3",
    name: "Hà Nội - Hạ Long",
    location: "Hà Nội, Quảng Ninh",
    price: 5100000,
    duration: "5 ngày 4 đêm",
    availableSlots: 15,
    description: "Khám phá phố cổ Hà Nội và du thuyền vịnh Hạ Long.",
  },
];

const routes = [
  defineRoute("GET", "/health", () => ok({ success: true, service: "tour-service" })),

  defineRoute("GET", "/tours", ({ log }) => {
    log("LIST tours", { count: tours.length });
    return ok({ success: true, tours });
  }),

  defineRoute("GET", "/tours/:id", ({ params, log }) => {
    log("GET tour detail", { tourId: params.id });
    const tour = tours.find((item) => item.id === params.id);

    if (!tour) {
      log("GET tour detail failed", { tourId: params.id });
      throw new HttpError(404, "Không tìm thấy tour");
    }

    log("GET tour detail success", {
      tourId: tour.id,
      price: tour.price,
      availableSlots: tour.availableSlots,
    });

    return ok({ success: true, tour });
  }),
];

startService(createJsonServer({ serviceName: "tour-service", routes }), {
  name: "Tour Service",
  host: HOST,
  port: PORT,
});
