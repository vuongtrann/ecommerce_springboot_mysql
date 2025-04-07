package com.ecommerce.ecommercespringbootpostgre.utils;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseConnectChecker {
    private final DataSource dataSource;

    public DatabaseConnectChecker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void checkDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("✅ Kết nối MySQL thành công!");
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi kết nối MySQL: " + e.getMessage());
        }
    }
}
