const express = require("express");
const mongoose = require("mongoose");

const app = express();
const port = process.env.PORT || 3000;
const mongoUri = process.env.MONGO_URI || "mongodb://mongo:27017/myapp";

let isMongoConnected = false;

mongoose
    .connect(mongoUri)
    .then(() => {
        isMongoConnected = true;
        console.log("Connected to MongoDB");
    })
    .catch((error) => {
        console.error("MongoDB connection error:", error.message);
    });

mongoose.connection.on("disconnected", () => {
    isMongoConnected = false;
});

mongoose.connection.on("connected", () => {
    isMongoConnected = true;
});

app.get("/", (_req, res) => {
    res.json({
        message: "Node.js API is running",
        mongoConnected: isMongoConnected,
    });
});

app.get("/health", (_req, res) => {
    if (isMongoConnected) {
        return res.status(200).json({ status: "ok", db: "up" });
    }

    return res.status(503).json({ status: "degraded", db: "down" });
});

app.listen(port, () => {
    console.log(`Server listening on port ${port}`);
});