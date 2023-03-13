package com.example.demo.controller;

import com.example.demo.service.AdniMergeQueryService;
import com.example.demo.service.InstacartQueryService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class QueryController {

    @Autowired
    InstacartQueryService instacartQueryService;

    @Autowired
    AdniMergeQueryService adniMergeQueryService;

    @RequestMapping(value = "/parseQuery", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin()
    public String processQuery(
        @RequestParam("category") String category,
        @RequestParam("database") String database,
        @RequestParam("query") String query
    ) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if (database.equals("instacart")) {
            if (category.equals("mysql")) {
                System.out.println(query);
                jsonObject = instacartQueryService.processMysqlQuery(query);
            } else if (category.equals("redshift")) {
                jsonObject = instacartQueryService.processRedshiftQuery(query);
            } else {
                jsonObject = instacartQueryService.processMongoQuery(query);
            }
        } else {
            if (category.equals("mysql")) {
                System.out.println(query);
                jsonObject = adniMergeQueryService.processMysqlQuery(query);
            } else if (category.equals("redshift")) {
                jsonObject = adniMergeQueryService.processRedshiftQuery(query);
            } else {
                jsonObject = adniMergeQueryService.processMongoQuery(query);
            }
        }
        return jsonObject.toString();
    }
}