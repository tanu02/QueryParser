package com.example.demo.service;

import org.json.JSONException;
import org.json.JSONObject;

public interface IQueryService {
    JSONObject processRedshiftQuery(String query) throws Exception;

    JSONObject processMysqlQuery(String query) throws Exception;

    JSONObject processMongoQuery(String query) throws Exception;

}
