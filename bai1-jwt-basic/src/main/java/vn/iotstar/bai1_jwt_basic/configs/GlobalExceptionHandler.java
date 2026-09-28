package vn.iotstar.bai1_jwt_basic.configs;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<Map<String, Object>> response(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of("timestamp", Instant.now().toString(), "status", status.value(), "error", message));
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> badCredentials(BadCredentialsException ex) { return response(HttpStatus.UNAUTHORIZED, "Email hoặc mật khẩu không đúng"); }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> accessDenied(AccessDeniedException ex) { return response(HttpStatus.FORBIDDEN, "Bạn không có quyền truy cập"); }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> expired(ExpiredJwtException ex) { return response(HttpStatus.UNAUTHORIZED, "JWT đã hết hạn"); }
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Map<String, Object>> invalidSignature(SignatureException ex) { return response(HttpStatus.UNAUTHORIZED, "Chữ ký JWT không hợp lệ"); }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> badRequest(IllegalArgumentException ex) { return response(HttpStatus.BAD_REQUEST, ex.getMessage()); }
}
