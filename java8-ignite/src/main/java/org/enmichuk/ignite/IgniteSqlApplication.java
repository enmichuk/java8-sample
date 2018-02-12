package org.enmichuk.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteLogger;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

public class IgniteSqlApplication {
    public static void main(String[] args) throws Exception {
        Ignition.setClientMode(true);
        dropSchema();
        createSchema();
        insertRecords();
        requestRecords();
    }

    public static void createSchema() throws Exception {
        // Register JDBC driver.
        Class.forName("org.apache.ignite.IgniteJdbcThinDriver");

        // Open JDBC connection.
        Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");

        // Create database tables.
        try (Statement stmt = conn.createStatement()) {

            // Create table based on REPLICATED template.
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS City (" +
                    " id LONG PRIMARY KEY, name VARCHAR) " +
                    " WITH \"template=replicated\"");

            // Create table based on PARTITIONED template with one backup.
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Person (" +
                    " id LONG, name VARCHAR, city_id LONG, " +
                    " PRIMARY KEY (id, city_id)) " +
                    " WITH \"backups=1, affinityKey=city_id\"");

            // Create an index on the City table.
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_city_name ON City (name)");

            // Create an index on the Person table.
            stmt.executeUpdate("CREATE INDEX IF NOT EXISTS idx_person_name ON Person (name)");
        }

        conn.close();
    }

    public static void insertRecords() {
        IgniteConfiguration cfg = new IgniteConfiguration();

        IgniteLogger log = new Slf4jLogger();

        cfg.setGridLogger(log);

        // Connecting to the cluster.
        try (Ignite ignite = Ignition.start(cfg)) {

            // Getting a reference to an underlying cache created for City table above.
            IgniteCache cityCache = ignite.cache("SQL_PUBLIC_CITY");

            // Getting a reference to an underlying cache created for Person table above.
            IgniteCache personCache = ignite.cache("SQL_PUBLIC_PERSON");

            // Inserting entries into City.
            SqlFieldsQuery query = new SqlFieldsQuery(
                    "INSERT INTO City (id, name) VALUES (?, ?)");

            cityCache.query(query.setArgs(1, "Forest Hill")).getAll();
            cityCache.query(query.setArgs(2, "Denver")).getAll();
            cityCache.query(query.setArgs(3, "St. Petersburg")).getAll();

            // Inserting entries into Person.
            query = new SqlFieldsQuery(
                    "INSERT INTO Person (id, name, city_id) VALUES (?, ?, ?)");

            personCache.query(query.setArgs(1, "John Doe", 3)).getAll();
            personCache.query(query.setArgs(2, "Jane Roe", 2)).getAll();
            personCache.query(query.setArgs(3, "Mary Major", 1)).getAll();
            personCache.query(query.setArgs(4, "Richard Miles", 2)).getAll();
        }
    }

    public static void requestRecords() {
        IgniteConfiguration cfg = new IgniteConfiguration();

        IgniteLogger log = new Slf4jLogger();

        cfg.setGridLogger(log);

        // Connecting to the cluster.
        try(Ignite ignite = Ignition.start(cfg)) {

            // Getting a reference to an underlying cache created for City table above.
            IgniteCache cityCache = ignite.cache("SQL_PUBLIC_CITY");

            // Querying data from the cluster using a distributed JOIN.
            SqlFieldsQuery query = new SqlFieldsQuery("SELECT p.name, c.name " +
                    " FROM Person p, City c WHERE p.city_id = c.id");

            FieldsQueryCursor<List<?>> cursor = cityCache.query(query);

            Iterator<List<?>> iterator = cursor.iterator();


            while (iterator.hasNext()) {
                List<?> row = iterator.next();

                System.out.println(row.get(0) + ", " + row.get(1));
            }
        }
    }

    public static void dropSchema() throws Exception {
        // Register JDBC driver.
        Class.forName("org.apache.ignite.IgniteJdbcThinDriver");

        // Open JDBC connection.
        Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");

        // Create database tables.
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP INDEX IF EXISTS idx_person_name");

            stmt.executeUpdate("DROP INDEX IF EXISTS idx_city_name");

            stmt.executeUpdate("DROP TABLE IF EXISTS Person");

            stmt.executeUpdate("DROP TABLE IF EXISTS City");
        }

        conn.close();
    }
}
