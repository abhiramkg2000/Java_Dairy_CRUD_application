package com.training.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void dbSuccessfulConnect() {
        Connection con=DatabaseConnection.dbConnect("cruddb","abhi","abhiram.g@qburst.com");
        assertNotNull(con);
    }
    @Test
    void dbUnsuccessfulConnect() {
        Connection con=DatabaseConnection.dbConnect("crdb","abhi","abhiram.g@qburst.com");
        assertNull(con);
    }
}