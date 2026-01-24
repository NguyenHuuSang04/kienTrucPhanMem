const express = require("express");
const amqp = require("amqplib");

const app = express();
app.use(express.json());

const RABBITMQ_URL = "amqp://user:password@rabbitmq:5672";
const NORMAL_QUEUE = "order_queue_normal";
const VIP_QUEUE = "order_queue_vip";
const STATUS_QUEUE = "order_status_queue";
const RETRY_QUEUE = "order_retry_queue";
const DLQ = "order_queue.dlq";

let channel;

async function connectRabbitMQ() {
  while (true) {
    try {
      const conn = await amqp.connect(RABBITMQ_URL);
      channel = await conn.createChannel();
      
      // Normal Queue với priority support
      await channel.assertQueue(NORMAL_QUEUE, {
        durable: true,
        deadLetterExchange: "",
        deadLetterRoutingKey: DLQ,
        arguments: {
          "x-max-priority": 10 // Hỗ trợ priority từ 0-10
        }
      });

      // VIP Queue - xử lý nhanh hơn
      await channel.assertQueue(VIP_QUEUE, {
        durable: true,
        deadLetterExchange: "",
        deadLetterRoutingKey: DLQ
      });

      // Status Queue - theo dõi trạng thái
      await channel.assertQueue(STATUS_QUEUE, {
        durable: true
      });

      // Retry Queue - thử lại sau 10 giây
      await channel.assertQueue(RETRY_QUEUE, {
        durable: true,
        deadLetterExchange: "",
        deadLetterRoutingKey: NORMAL_QUEUE,
        arguments: {
          "x-message-ttl": 10000 // 10 giây
        }
      });

      // Dead Letter Queue
      await channel.assertQueue(DLQ, {
        durable: true
      });

      console.log("✅ Producer connected to RabbitMQ with Advanced Queues");
      break;
    } catch (err) {
      console.log("⏳ Waiting for RabbitMQ...", err.message);
      await new Promise((r) => setTimeout(r, 3000));
    }
  }
}

// POST /order/create - Tạo đơn hàng mới
app.post("/order/create", async (req, res) => {
  const { orderId, customerName, items, totalAmount, isVIP } = req.body;

  // Validation
  if (!orderId || !customerName || !items || !totalAmount) {
    return res.status(400).json({ 
      error: "Missing required fields: orderId, customerName, items, totalAmount" 
    });
  }

  const orderData = {
    orderId,
    customerName,
    items,
    totalAmount,
    isVIP: isVIP || false,
    status: "pending",
    createdAt: new Date().toISOString(),
    retryCount: 0
  };

  // Chọn queue dựa trên isVIP
  const targetQueue = isVIP ? VIP_QUEUE : NORMAL_QUEUE;
  
  // Priority: VIP luôn có priority cao nhất (10)
  const priority = isVIP ? 10 : (totalAmount > 1000 ? 5 : 1);

  channel.sendToQueue(
    targetQueue,
    Buffer.from(JSON.stringify(orderData)),
    {
      persistent: true,
      priority: priority // Đơn hàng có giá trị cao được ưu tiên
    }
  );

  console.log(`📦 Order sent to ${isVIP ? 'VIP' : 'NORMAL'} queue:`, orderId);

  res.json({
    status: "Order received",
    queue: targetQueue,
    priority: priority,
    orderData
  });
});

// POST /order/cancel - Hủy đơn hàng
app.post("/order/cancel", async (req, res) => {
  const { orderId, reason } = req.body;

  if (!orderId) {
    return res.status(400).json({ error: "orderId is required" });
  }

  const cancelData = {
    orderId,
    action: "cancel",
    reason: reason || "Customer request",
    timestamp: new Date().toISOString()
  };

  channel.sendToQueue(
    STATUS_QUEUE,
    Buffer.from(JSON.stringify(cancelData)),
    { persistent: true }
  );

  console.log("❌ Cancel request sent:", orderId);

  res.json({
    status: "Cancel request sent",
    data: cancelData
  });
});

// POST /order/status - Cập nhật trạng thái đơn hàng
app.post("/order/status", async (req, res) => {
  const { orderId, status, note } = req.body;

  if (!orderId || !status) {
    return res.status(400).json({ error: "orderId and status are required" });
  }

  const statusData = {
    orderId,
    status, // processing, completed, failed
    note: note || "",
    timestamp: new Date().toISOString()
  };

  channel.sendToQueue(
    STATUS_QUEUE,
    Buffer.from(JSON.stringify(statusData)),
    { persistent: true }
  );

  console.log("📊 Status update sent:", orderId, status);

  res.json({
    status: "Status update sent",
    data: statusData
  });
});

// POST /order/bulk - Gửi nhiều đơn hàng cùng lúc
app.post("/order/bulk", async (req, res) => {
  const { orders } = req.body;

  if (!orders || !Array.isArray(orders) || orders.length === 0) {
    return res.status(400).json({ error: "orders array is required" });
  }

  const results = [];

  for (const order of orders) {
    const orderData = {
      ...order,
      status: "pending",
      createdAt: new Date().toISOString(),
      retryCount: 0
    };

    const targetQueue = order.isVIP ? VIP_QUEUE : NORMAL_QUEUE;
    const priority = order.isVIP ? 10 : (order.totalAmount > 1000 ? 5 : 1);

    channel.sendToQueue(
      targetQueue,
      Buffer.from(JSON.stringify(orderData)),
      { persistent: true, priority: priority }
    );

    results.push({
      orderId: order.orderId,
      queue: targetQueue,
      priority: priority
    });
  }

  console.log(`📦 Bulk orders sent: ${orders.length} orders`);

  res.json({
    status: "Bulk orders sent",
    count: orders.length,
    results
  });
});

// GET /health - Health check
app.get("/health", (req, res) => {
  res.json({
    status: "healthy",
    rabbitmq: channel ? "connected" : "disconnected",
    timestamp: new Date().toISOString()
  });
});

connectRabbitMQ();

app.listen(3000, () => {
  console.log("🚀 Advanced Producer API listening on port 3000");
  console.log("📍 Endpoints:");
  console.log("   POST /order/create - Create new order");
  console.log("   POST /order/cancel - Cancel order");
  console.log("   POST /order/status - Update order status");
  console.log("   POST /order/bulk - Create multiple orders");
  console.log("   GET /health - Health check");
});
