package com.laundry.report;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final JdbcTemplate jdbcTemplate;

    public ReportController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/summary")
    public List<Map<String, Object>> summaryByStatus() {
        String sql = "SELECT status, COUNT(*) AS total_orders, "
                + "COALESCE(SUM(total_amount), 0) AS total_amount "
                + "FROM laundry_order GROUP BY status";
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/customers")
    public List<Map<String, Object>> spendPerCustomer() {
        String sql = "SELECT c.id AS customer_id, c.name, COUNT(o.id) AS total_orders, "
                + "COALESCE(SUM(o.total_amount), 0) AS total_spent "
                + "FROM customer c LEFT JOIN laundry_order o ON o.customer_id = c.id "
                + "GROUP BY c.id, c.name ORDER BY total_spent DESC";
        return jdbcTemplate.queryForList(sql);
    }
}
