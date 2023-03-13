package com.example.demo.mongo;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utility.Utility;

public class InstaMongoDB {

    public JSONObject query(String query) throws ClassNotFoundException, SQLException, JSONException {
        {
            Statement stmt = Utility.getInstaConnection().createStatement();
            ResultSet rst = stmt.executeQuery(query);
            return writeResultSet(rst);

        }
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
}
