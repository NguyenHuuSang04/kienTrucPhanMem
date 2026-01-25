package edu.iuh.fit.jwt.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Dự án: JWT
 * @Class: DemoController
 * @Tạo vào ngày: 1/25/2026
 * @Tác giả: Nguyen Huu Sang
 */

@RestController
@RequestMapping("/api")
public class DemoController {
    @GetMapping("/public")
    public String publicEndpoint() {
        return "Dữ liệu công khai: Bất kỳ ai cũng có thể xem!";
    }

    @GetMapping("/private")
    public String privateEndpoint(Authentication authentication) {
        // authentication.getName() sẽ lấy ra 'sub' trong token
        return "Chào " + authentication.getName() + "! Bạn đã vào được vùng bảo mật bằng RSA.";
    }
}