package com.example.demo.model;

import lombok.Data;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.dto.Point;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Data
@Measurement(name="test")
public class TestStats {
    @Column(name = "time")
    private Instant time;
    @Column(name = "name", tag = true)
    private String name;
    @Column(name = "project", tag = true)
    private String project;
    @Column(name = "passed")
    private Double passed;
    @Column(name = "failed")
    private Double failed;
    @Column(name = "errors")
    private Double errors;
    @Column(name = "skipped")
    private Double skipped;
    @Column(name = "duration")
    private Double duration;


    public String toString(){
        return "name: " + this.name + " project: " + this.project + " passed: " + this.passed + " failed: " + this.failed + " errors: " + this.errors + " duration: " + this.duration;
    }

    public Point toPoint(){

        return Point.measurement("test")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("name", this.name)
                .tag("project", this.project)
                .addField("passed", this.passed)
                .addField("failed", this.failed)
                .addField("skipped", this.skipped)
                .addField("errors", this.errors)
                .addField("duration", this.duration)
                .build();
    }
}
