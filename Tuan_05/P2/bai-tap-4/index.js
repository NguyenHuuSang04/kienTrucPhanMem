const express = require('express');
const app = express();
const port = 3000;

app.get('/', (req, res) => {
    res.send('<h1>Hello từ Node.js trong Docker!</h1>');
});

app.listen(port, () => {
    console.log(`App đang chạy tại http://localhost:${port}`);
});