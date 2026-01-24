const amqp = require("amqplib");

const RABBITMQ_URL = "amqp://user:password@rabbitmq:5672";
const NORMAL_QUEUE = "order_queue_normal";
const VIP_QUEUE = "order_queue_vip";
const STATUS_QUEUE = "order_status_queue";
const RETRY_QUEUE = "order_retry_queue";
const DLQ = "order_queue.dlq";

let channel;

// Simulate database để lưu order status
const orderDatabase = new Map();

async function connectWithRetry() {
  try {
    console.log("🔄 Consumer connecting...");
    const conn = await amqp.connect(RABBITMQ_URL);
    channel = await conn.createChannel();

    // Assert all queues
    await channel.assertQueue(NORMAL_QUEUE, {
      durable: true,
      deadLetterExchange: "",
      deadLetterRoutingKey: DLQ,
      arguments: { "x-max-priority": 10 }
    });

    await channel.assertQueue(VIP_QUEUE, {
      durable: true,
      deadLetterExchange: "",
      deadLetterRoutingKey: DLQ
    });

    await channel.assertQueue(STATUS_QUEUE, { durable: true });
    
    await channel.assertQueue(RETRY_QUEUE, {
      durable: true,
      deadLetterExchange: "",
      deadLetterRoutingKey: NORMAL_QUEUE,
      arguments: { "x-message-ttl": 10000 }
    });

    await channel.assertQueue(DLQ, { durable: true });

    console.log("✅ Consumer connected to RabbitMQ");

    // Process VIP Queue (highest priority)
    processVIPQueue();
    
    // Process Normal Queue (with priority)
    processNormalQueue();
    
    // Process Status Queue
    processStatusQueue();

    // Monitor DLQ
    monitorDLQ();

  } catch (err) {
    console.log("❌ Consumer failed, retry in 3s...", err.message);
    setTimeout(connectWithRetry, 3000);
  }
}

// Xử lý VIP Queue - luôn ưu tiên cao nhất
function processVIPQueue() {
  console.log("👑 VIP Queue consumer started");
  
  channel.consume(
    VIP_QUEUE,
    async (msg) => {
      if (!msg) return;

      const body = msg.content.toString();
      console.log("\n🌟 === VIP ORDER PROCESSING ===");
      console.log("📦 Order:", body);

      try {
        const order = JSON.parse(body);

        // Validation
        if (!order.orderId || !order.customerName) {
          throw new Error("Invalid order data");
        }

        // Simulate processing - VIP xử lý nhanh hơn (2s)
        console.log(`⚡ Processing VIP order ${order.orderId} for ${order.customerName}...`);
        await new Promise(resolve => setTimeout(resolve, 2000));

        // Random failure để test retry (20% fail rate)
        if (Math.random() < 0.2) {
          throw new Error("Payment gateway timeout");
        }

        // Success
        orderDatabase.set(order.orderId, {
          ...order,
          status: "completed",
          processedAt: new Date().toISOString()
        });

        console.log(`✅ VIP Order ${order.orderId} completed successfully!`);
        console.log(`💰 Total: $${order.totalAmount}`);
        channel.ack(msg);

      } catch (err) {
        console.log(`❌ VIP Order processing failed: ${err.message}`);
        
        const order = JSON.parse(body);
        order.retryCount = (order.retryCount || 0) + 1;

        // Retry tối đa 3 lần
        if (order.retryCount < 3) {
          console.log(`🔄 Retry attempt ${order.retryCount}/3 - Sending to retry queue`);
          
          channel.sendToQueue(
            RETRY_QUEUE,
            Buffer.from(JSON.stringify(order)),
            { persistent: true }
          );
          
          channel.ack(msg); // Ack message gốc
        } else {
          console.log(`💀 Max retries reached - Sending to DLQ`);
          channel.nack(msg, false, false); // Send to DLQ
        }
      }
    },
    { noAck: false }
  );
}

// Xử lý Normal Queue - có priority
function processNormalQueue() {
  console.log("📦 Normal Queue consumer started");
  
  channel.prefetch(1); // Process 1 message at a time
  
  channel.consume(
    NORMAL_QUEUE,
    async (msg) => {
      if (!msg) return;

      const body = msg.content.toString();
      console.log("\n📦 === NORMAL ORDER PROCESSING ===");
      console.log("📄 Order:", body);

      try {
        const order = JSON.parse(body);

        // Validation
        if (!order.orderId || !order.customerName) {
          throw new Error("Invalid order data");
        }

        // Simulate processing - Normal xử lý chậm hơn (5s)
        const processingTime = order.totalAmount > 1000 ? 3000 : 5000;
        console.log(`⏳ Processing order ${order.orderId} for ${order.customerName}...`);
        console.log(`   Priority: ${msg.properties.priority || 0}`);
        await new Promise(resolve => setTimeout(resolve, processingTime));

        // Random failure để test retry (30% fail rate)
        if (Math.random() < 0.3) {
          throw new Error("Inventory check failed");
        }

        // Success
        orderDatabase.set(order.orderId, {
          ...order,
          status: "completed",
          processedAt: new Date().toISOString()
        });

        console.log(`✅ Order ${order.orderId} completed successfully!`);
        console.log(`💰 Total: $${order.totalAmount}`);
        channel.ack(msg);

      } catch (err) {
        console.log(`❌ Order processing failed: ${err.message}`);
        
        const order = JSON.parse(body);
        order.retryCount = (order.retryCount || 0) + 1;

        // Retry tối đa 3 lần
        if (order.retryCount < 3) {
          console.log(`🔄 Retry attempt ${order.retryCount}/3 - Will retry in 10s`);
          
          channel.sendToQueue(
            RETRY_QUEUE,
            Buffer.from(JSON.stringify(order)),
            { persistent: true }
          );
          
          channel.ack(msg);
        } else {
          console.log(`💀 Max retries reached - Sending to DLQ`);
          channel.nack(msg, false, false);
        }
      }
    },
    { noAck: false }
  );
}

// Xử lý Status Queue
function processStatusQueue() {
  console.log("📊 Status Queue consumer started");
  
  channel.consume(
    STATUS_QUEUE,
    (msg) => {
      if (!msg) return;

      const body = msg.content.toString();
      console.log("\n📊 === STATUS UPDATE ===");
      
      try {
        const statusUpdate = JSON.parse(body);
        
        if (statusUpdate.action === "cancel") {
          console.log(`❌ Order ${statusUpdate.orderId} cancelled`);
          console.log(`   Reason: ${statusUpdate.reason}`);
          
          orderDatabase.set(statusUpdate.orderId, {
            orderId: statusUpdate.orderId,
            status: "cancelled",
            reason: statusUpdate.reason,
            timestamp: statusUpdate.timestamp
          });
        } else {
          console.log(`📝 Order ${statusUpdate.orderId} status: ${statusUpdate.status}`);
          if (statusUpdate.note) {
            console.log(`   Note: ${statusUpdate.note}`);
          }
          
          const existing = orderDatabase.get(statusUpdate.orderId) || {};
          orderDatabase.set(statusUpdate.orderId, {
            ...existing,
            status: statusUpdate.status,
            lastUpdate: statusUpdate.timestamp
          });
        }
        
        channel.ack(msg);
      } catch (err) {
        console.log(`❌ Status update failed: ${err.message}`);
        channel.ack(msg); // Ack anyway to prevent loop
      }
    },
    { noAck: false }
  );
}

// Monitor Dead Letter Queue
function monitorDLQ() {
  console.log("💀 DLQ Monitor started");
  
  channel.consume(
    DLQ,
    (msg) => {
      if (!msg) return;

      console.log("\n💀 === DEAD LETTER QUEUE ===");
      console.log("❌ Failed order:", msg.content.toString());
      console.log("   This order needs manual intervention!");
      
      // Có thể gửi alert, email, hoặc lưu vào database
      
      channel.ack(msg);
    },
    { noAck: false }
  );
}

// Hiển thị database mỗi 15 giây
setInterval(() => {
  if (orderDatabase.size > 0) {
    console.log("\n" + "=".repeat(50));
    console.log("📊 ORDER DATABASE STATUS");
    console.log("=".repeat(50));
    orderDatabase.forEach((order, orderId) => {
      console.log(`${orderId}: ${order.status} - $${order.totalAmount || 0}`);
    });
    console.log("=".repeat(50) + "\n");
  }
}, 15000);

connectWithRetry();
