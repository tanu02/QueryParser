package com.example.demo.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AdniMergeMySQLDB {
    private Connection connect;

//    String myUrl = "jdbc:mysql://instacart2.ceeptitalbiu.us-east-1.rds.amazonaws.com:3306/sys";
//    String username = "admin";
//    String pass = "admin1234";

    String myUrl = "jdbc:mysql://aws-dbds-database.cvlkkwfb5kw8.us-east-2.rds.amazonaws.com:3306/adnimergeVer2";

    String username = "admin";

    String pass = "1234567890";

    public AdniMergeMySQLDB() throws SQLException {
        connect = DriverManager.getConnection(myUrl, username, pass);
    }

    public JSONObject readDataBase(String query) throws Exception {

        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        JSONObject jsonObject = writeResultSet(resultSet);

        return jsonObject;
    }


    private JSONObject writeResultSet(ResultSet rs) throws SQLException, JSONException {

        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        ResultSetMetaData rsMetaData = rs.getMetaData();
        int count = rsMetaData.getColumnCount();
        int rows = 0;
        while (rs.next()) {
            if (rows++ == 100) {
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


    private void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (Exception e) {

        }
    }

}