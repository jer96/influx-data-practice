package com.example.demo.interfaces;
import com.example.demo.model.TestStats;
import java.util.List;

public interface IInfluxService {

    List<TestStats> selectQuery(String query);
    void insertQuery(TestStats stats);
}
