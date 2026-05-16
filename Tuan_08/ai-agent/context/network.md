# Network — LAN 192.168.137.x

## Bảng IP đầy đủ

| Service | IP | Port | Member | Vai trò trong LAN |
|---|---|---|---|---|
| Redis | 192.168.137.98 | 6379 | Sang | TCP, không bind 127.0.0.1 |
| PU1 Product | 192.168.137.157 | 8081 | Huy | HTTP REST |
| PU2 Cart | 192.168.137.184 | 8082 | Hiền | HTTP REST |
| PU3 Order | 192.168.137.20 | 8083 | Thái | HTTP REST |
| PU4 Inventory | 192.168.137.98 | 8084 | Sang | HTTP REST |
| Frontend | 192.168.137.41 | 3000 | Trường | Vite dev server (`--host 0.0.0.0`) |

## Mở Windows Firewall (mỗi máy chạy 1 lần, PowerShell admin)

```powershell
# Sang (PU4 + Redis)
New-NetFirewallRule -DisplayName "FlashSale-Redis" -Direction Inbound -LocalPort 6379 -Protocol TCP -Action Allow
New-NetFirewallRule -DisplayName "FlashSale-PU4" -Direction Inbound -LocalPort 8084 -Protocol TCP -Action Allow

# Huy
New-NetFirewallRule -DisplayName "FlashSale-PU1" -Direction Inbound -LocalPort 8081 -Protocol TCP -Action Allow

# Hiền
New-NetFirewallRule -DisplayName "FlashSale-PU2" -Direction Inbound -LocalPort 8082 -Protocol TCP -Action Allow

# Thái
New-NetFirewallRule -DisplayName "FlashSale-PU3" -Direction Inbound -LocalPort 8083 -Protocol TCP -Action Allow

# Trường
New-NetFirewallRule -DisplayName "FlashSale-FE" -Direction Inbound -LocalPort 3000 -Protocol TCP -Action Allow
```

## Kiểm tra connectivity (chạy ở mỗi máy)

```powershell
# Ping Redis (Sang phải online trước)
Test-NetConnection -ComputerName 192.168.137.98 -Port 6379

# Ping PU đối tác
Test-NetConnection -ComputerName 192.168.137.157 -Port 8081  # PU1
Test-NetConnection -ComputerName 192.168.137.184 -Port 8082  # PU2
Test-NetConnection -ComputerName 192.168.137.20 -Port 8083   # PU3
Test-NetConnection -ComputerName 192.168.137.98 -Port 8084   # PU4

# Test Redis
redis-cli -h 192.168.137.98 ping   # mong đợi: PONG
```

## Redis bind

Container Redis dùng image `redis:7-alpine` mặc định bind `0.0.0.0`. Nếu chạy Redis không qua Docker, cần sửa `redis.conf`:

```
bind 0.0.0.0
protected-mode no
```

Cảnh báo: chỉ dùng `protected-mode no` trong môi trường LAN nội bộ học tập. Production phải có password.
