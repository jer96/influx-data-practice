package com.example.demo.service;

import com.example.demo.interfaces.IInfluxService;
import com.example.demo.model.Response;
import com.example.demo.model.TestStats;
import com.example.demo.util.Const;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping
public class RestService {

    @Autowired
    private IInfluxService influxService;


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> healthcheck(){


        Response response = Response.builder()
                .status(Const.HEALTH_CHECK_STATUS)
                .build();

        log.info("health check endpoint hit");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> queryStats(){

        List<TestStats> testStats = influxService.selectQuery("select * from test");

        Response response = Response.builder()
                .status(Const.DB_SELECT_STATUS)
                .testStats(testStats)
                .build();
        log.info("stats endpoint hit");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> uploadStats(@RequestBody TestStats stats){
        influxService.insertQuery(stats);

        Response response = Response.builder()
                .status(Const.DB_INSERT_STATUS)
                .build();
        log.info("upload endpoint hit");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
