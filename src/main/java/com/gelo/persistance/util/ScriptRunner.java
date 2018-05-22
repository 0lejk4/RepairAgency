package com.gelo.persistance.util;

import com.gelo.persistance.ConnectionManager;
import com.gelo.persistance.exception.DatabaseRuntimeException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The type Script runner.
 * This class simply executes any script from file using connection manager
 */
public class ScriptRunner {

    private static final Logger LOGGER = LogManager.getLogger(ScriptRunner.class);

    private ConnectionManager connectionManager;

    /**
     * Instantiates a new Script runner.
     *
     * @param connectionManager the connection manager
     */
    public ScriptRunner(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Execute script.
     *
     * @param file the file
     */
    public void executeScript(File file) {
        int line = 0;
        try {
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                List<String> queries = Arrays.stream(content.split(";"))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());

                Connection conn = connectionManager.getConnection();

                try {
                    for (String query : queries) {
                        try (Statement stmt = conn.createStatement()) {
                            line++;
                            stmt.execute(query.trim());
                        }
                    }
                } finally {
                    tryClose(conn);
                }
            }
        } catch (IOException e) {
            LOGGER.warn("SQL script file not found");
            throw new IllegalArgumentException(e);
        }catch (SQLException e) {
            LOGGER.trace("Errors while executing SQL script at #" + line +
                    " in file " + file.getAbsolutePath(), e);
            throw new DatabaseRuntimeException(e);
        }
    }

    private void tryClose(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Cannot close jdbc connection", e);
            throw new DatabaseRuntimeException(e);
        }
    }
}
