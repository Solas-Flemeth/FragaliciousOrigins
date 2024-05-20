package org.originsreborn.fragaliciousorigins.jdbc;

import org.originsreborn.fragaliciousorigins.origins.Origin;
import org.originsreborn.fragaliciousorigins.configs.MainConfig;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginState;
import org.originsreborn.fragaliciousorigins.origins.enums.OriginType;

import java.sql.*;
import java.util.Collection;
import java.util.UUID;

import static org.originsreborn.fragaliciousorigins.jdbc.DataSourceManager.getDataSource;


public class OriginsDAO {
    private static final String TABLEPREFIX = MainConfig.getTablePrefix();
    private static final String TABLENAME = TABLEPREFIX + "Origins";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLENAME + " (" +
            "uuid VARCHAR(36) NOT NULL," +
            "originState VARCHAR(255) NOT NULL," +
            "originType VARCHAR(255)," +
            "originData TEXT," +
            "PRIMARY KEY (uuid, originState)" +
            ")";


    /**
     * Creates a table called TABLEPREFIX_Origins with 2 PKs(UUID, OriginState) and 2 other data columns (OriginType, CustomData).
     * These should all be varchars. Custom Data is a serialized json of variables.
     * The custom data is an extra long compared to the other 3 values.
     */
    public static void createTable() {
        try (Connection connection = getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_QUERY);
        } catch (Exception e) {
            //do something. lol
        }
    }

    /**
     * Gets the player's origin object.
     * If no field for the UUID, returns human with state normal and NORMAL.
     *
     * @param uuid
     * @return
     */
    public static Origin getOrigin(UUID uuid) {
        String query = "SELECT * FROM " + TABLENAME + " WHERE uuid = ? ORDER BY FIELD(originState, 'EVENT', 'TEMPORARY', 'SHAPESHIFTER', 'NORMAL')";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, uuid.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new SerializedOrigin(
                            resultSet.getString("uuid"),
                            resultSet.getString("originType"),
                            resultSet.getString("originState"),
                            resultSet.getString("originData")
                    ).deserializeOrigin();
                }
            }
        } catch (SQLException e) {}
        //Failed to get the origin, so returning default human
        return OriginType.HUMAN.generateOrigin(uuid.toString(), OriginState.NORMAL.name(), "");
    }

    /**
     * Updates the origin data if it exists, if it does not, it adds it to the database as a new table.
     *
     * @param origin
     */
    public static void saveOrigin(SerializedOrigin origin) {
        String query = "INSERT INTO " + TABLENAME + " (uuid, originState, originType, originData) VALUES (?, ?, ?, ?)" +
                " ON DUPLICATE KEY UPDATE originType = VALUES(originType), originData = VALUES(originData)";
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, origin.getUuid());
            statement.setString(2, origin.getOriginState());
            statement.setString(3, origin.getOriginType());
            statement.setString(4, origin.getOriginData());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the origin data if it exists, if it does not, it adds it to the database as a new table.
     *
     * @param origins
     */
    public static void saveOrigin(Collection<SerializedOrigin> origins) {
        String query = "INSERT INTO " + TABLENAME + " (uuid, originState, originType, originData) VALUES (?, ?, ?, ?)" +
                " ON DUPLICATE KEY UPDATE originType = VALUES(originType), originData = VALUES(originData)";
        try (Connection connection = getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (SerializedOrigin origin : origins) {
                    statement.setString(1, origin.getUuid());
                    statement.setString(2, origin.getOriginState());
                    statement.setString(3, origin.getOriginType());
                    statement.setString(4, origin.getOriginData());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Removes the origin from the table
     *
     * @param origin
     */
    public static void deleteOrigin(SerializedOrigin origin) {
        String query = "DELETE FROM " + TABLENAME + " WHERE uuid = ? AND originState = ?";
        try (PreparedStatement statement = getDataSource().getConnection().prepareStatement(query)) {
            statement.setString(1, origin.getUuid());
            statement.setString(2, origin.getOriginState());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
