package com.example.demo.service;

import com.example.demo.mongo.AdniMongoDB;
import com.example.demo.mysql.AdniMergeMySQLDB;
import com.example.demo.redshift.AdnimergeRedShiftDB;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class AdniMergeQueryService implements IQueryService {

    AdniMergeMySQLDB adniMergeMySQLDB;

    AdnimergeRedShiftDB adnimergeRedShiftDB;

    AdniMongoDB adniMongoDB;

    public AdniMergeQueryService() throws SQLException {
        adniMergeMySQLDB = new AdniMergeMySQLDB();
        adnimergeRedShiftDB = new AdnimergeRedShiftDB();
        adniMongoDB = new AdniMongoDB();
    }

    public JSONObject processMysqlQuery(String query) throws Exception {

        try {
            JSONObject obj = adniMergeMySQLDB.readDataBase(query);
            return obj;
        } catch (Exception e) {
            System.out.println("Bad request");
            throw new Exception("Bad Request");
        }
    }

    public JSONObject processRedshiftQuery(String query) throws Exception {

        try {
            JSONObject obj = adnimergeRedShiftDB.queryTable(query);
            return obj;
        } catch (Exception e) {
            System.out.println("Bad request");
            throw new Exception("Bad Request");
        }
    }

    @Override
    public JSONObject processMongoQuery(String query) throws JSONException, SQLException, ClassNotFoundException {
        return  adniMongoDB.query(query);
    }

}