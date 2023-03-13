package com.example.demo.redshift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * adding aws dependency in pom was throwing error. Hence I added redshift jar dependency in the module settings.
 * */
public class AdnimergeRedShiftDB {

    private static final String JDBC_URL = "jdbc:redshift://redshift-cluster-1.cpup7ovbrmnr.us-east-2.redshift.amazonaws.com:5439/adnimerge";

    private static final String USERNAME = "awsuser";

    private static final String PASSWORD = "Admin1234";

    private Connection connect;

    public AdnimergeRedShiftDB() throws SQLException {
        Properties properties = getPropertiesForDriverManager();
        this.connect = DriverManager.getConnection(JDBC_URL, properties);
    }

    public JSONObject queryTable(String query) throws SQLException, ClassNotFoundException, JSONException {

        Statement statement = connect.createStatement();
        ResultSet rs = statement.executeQuery(query);
        JSONObject jb = writeResultSet(rs);
        return jb;
    }

    private JSONObject writeResultSet(ResultSet rs) throws SQLException, JSONException {

        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        ResultSetMetaData rsMetaData = rs.getMetaData();
        int count = rsMetaData.getColumnCount();
        int rows = 0;
        while (rs.next()) {
            if (rows++ == 1000) {
                break;
            }
            JSONObject record = new JSONObject();
            for (int i = 1; i <= count; i++) {
                record.put(rsMetaData.getColumnName(i), rs.getString(i));
            }
            array.put(record);
        }

        jsonObject.put("table", array);
        return jsonObject;
    }

    private Properties getPropertiesForDriverManager() {
        Properties props = new Properties();
        props.setProperty("user", USERNAME);
        props.setProperty("password", PASSWORD);
        return props;
    }
}