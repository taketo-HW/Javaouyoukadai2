package com.example.dockerapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Map;

@RestController
public class HelloController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/api/hello")
    public String sayHello() {
        return "Hello, Docker World!";
    }

    @GetMapping("/api/hoge")
    public String sayHoge() {
        return "hogehogehoge";
    }

    @GetMapping("/api/check-db")
    public String checkDbConnection() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class); // MySQLへの接続確認
            return "Database connection is successful!";
        } catch (Exception e) {
            return "Database connection failed!";
        }
    }
    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getUserById(@PathVariable Long user_id) {
        try {
            String sql = "SELECT id, name, email FROM demo.users WHERE id = ?";
            Map<String, Object> user = jdbcTemplate.queryForMap(sql, user_id);
            return ResponseEntity.ok(user);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
