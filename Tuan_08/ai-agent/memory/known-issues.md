# Known Issues

Cập nhật khi gặp lỗi infra/setup phổ biến để member sau tránh lặp.

## Windows Firewall block port

**Triệu chứng**: máy khác `Test-NetConnection` fail (TcpTestSucceeded: False) dù service đang chạy.

**Fix**: chạy lệnh `New-NetFirewallRule` ở `context/network.md` với port tương ứng. PowerShell phải Run as Administrator.

## Docker Desktop chưa start

**Triệu chứng**: `docker compose up` báo `error during connect: ... open //./pipe/dockerDesktopLinuxEngine`.

**Fix**: mở Docker Desktop từ Start Menu, chờ icon ở khay chuyển xanh.

## Redis "protected mode" reject

**Triệu chứng**: `redis-cli -h 192.168.137.98 ping` báo `DENIED Redis is running in protected mode`.

**Fix**: trong `pu4-inventory/docker-compose.yml`, thêm command override:

```yaml
redis:
  image: redis:7-alpine
  command: redis-server --protected-mode no --bind 0.0.0.0
```

## CORS preflight fail

**Triệu chứng**: browser console báo `CORS policy: Response to preflight request doesn't pass`.

**Fix**: kiểm tra `CorsConfig.java` của PU đó có thêm origin `http://192.168.137.41:3000` chưa. Restart PU sau khi sửa.

## Maven Wrapper không chạy được trên Windows

**Triệu chứng**: `./mvnw` báo `'./mvnw' is not recognized`.

**Fix**: PowerShell dùng `.\mvnw.cmd` thay vì `./mvnw`.

## Java version sai

**Triệu chứng**: `mvnw spring-boot:run` báo `class file has wrong version`.

**Fix**: cài JDK 21 (Eclipse Temurin) và set `JAVA_HOME`. Verify bằng `java -version`.

## Port đã được dùng

**Triệu chứng**: `Web server failed to start. Port 8081 was already in use`.

**Fix**: tìm và kill process:
```powershell
Get-NetTCPConnection -LocalPort 8081 | Select-Object -ExpandProperty OwningProcess | ForEach-Object { Stop-Process -Id $_ -Force }
```
