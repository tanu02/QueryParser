package com.example.demo.service;

import com.example.demo.mongo.InstaMongoDB;
import com.example.demo.mysql.InstacartMySQLDB;
import com.example.demo.redshift.InstacartRedShiftDB;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class InstacartQueryService implements IQueryService{

    InstacartMySQLDB instacartMySQLAccess;

    InstacartRedShiftDB instacartRedShiftDB;

    InstaMongoDB instacartMongoDB;

    public InstacartQueryService() throws SQLException {
        instacartMySQLAccess = new InstacartMySQLDB();
        instacartRedShiftDB = new InstacartRedShiftDB();
        instacartMongoDB = new InstaMongoDB();
    }

    public JSONObject processMysqlQuery(String query) throws Exception {

        try {
            JSONObject obj = instacartMySQLAccess.readDataBase(query);
            return obj;
        } catch (Exception e) {
            System.out.println("Bad request");
            throw new Exception("Bad Request");
        }
    }

    public JSONObject processRedshiftQuery(String query) throws Exception {

        try {
            JSONObject obj = instacartRedShiftDB.queryTable(query);
            return obj;
        } catch (Exception e) {
            System.out.println("Bad request");
            throw new Exception("Bad Request");
        }
    }

    public JSONObject processMongoQuery(String query) throws SQLException, ClassNotFoundException, JSONException {
        return instacartMongoDB.query(query);
    }


}