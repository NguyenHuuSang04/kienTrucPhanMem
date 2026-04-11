const express = require('express');
const mysql = require('mysql2');
const app = express();

// Kết nối dùng tên service 'db-mysql' thay vì localhost
const db = mysql.createConnection({
    host: 'db-mysql',
    user: 'user',
    password: 'password',
    database: 'mydb'
});

app.get('/', (req, res) => {
    db.query('SELECT "Kết nối MySQL thành công!" AS message', (err, results) => {
        if (err) {
            res.status(500).send('Lỗi kết nối: ' + err.stack);
            return;
        }
        res.send(`<h1>${results[0].message}</h1>`);
    });
});

app.listen(3000, () => console.log('Server running on port 3000'));