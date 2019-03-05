/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Database;

/**
 *
 * @author omandotkom
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.dotlab.software.instaautomation.Settings.ApplicationSettings;
import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.dbutils.DbUtils;

/**
 *
 * @author sqlitetutorial.net
 */
public class DBApplication {

    private static ApplicationSettings settings = new ApplicationSettings();

    /**
     * Connect to a sample database
     */
    private void createNewDatabase() throws IOException {

        String url = "jdbc:sqlite:" + settings.getConfig().getDbPath();

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                DbUtils.close(conn);
                createNewTable();
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createNewTable() throws IOException {
        // SQLite connection string
        String url = "jdbc:sqlite:" + settings.getConfig().getDbPath();

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS follow (\n"
                + "	username text PRIMARY KEY\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            stmt.close();
            DbUtils.close(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertFollow(String username) throws IOException {
        String sql = "INSERT INTO follow(username) VALUES(?)";

        String url = "jdbc:sqlite:" + settings.getConfig().getDbPath();

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            pstmt.executeUpdate();
            DbUtils.close(conn);
            DbUtils.close(pstmt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            File file = new File(settings.getConfig().getDbPath());
            if (!file.exists()) {
                //database belum ada
                 DBApplication db = new DBApplication();
                 db.createNewDatabase();
                 db.insertFollow("omandotkom.jpg");
            }
        } catch (IOException ex) {
            Logger.getLogger(DBApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
