package com.example.demo.service;

import com.example.demo.interfaces.IInfluxService;
import com.example.demo.model.TestStats;
import com.example.demo.util.Const;
import lombok.extern.log4j.Log4j2;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Log4j2
public class InfluxDBService implements IInfluxService {
    InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086");

    @Override
    public List<TestStats> selectQuery(String queryString) {
        Query query = new Query(queryString, Const.DEMO_DB);
        QueryResult queryResult = influxDB.query(query);
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        return resultMapper.toPOJO(queryResult, TestStats.class);
    }

    @Override
    public void insertQuery(TestStats stats) {
        try{
            influxDB.setDatabase(Const.DEMO_DB);
            influxDB.write(stats.toPoint());
        }
        catch (Exception e){
            log.error(e.getMessage());
        }

    }

}
