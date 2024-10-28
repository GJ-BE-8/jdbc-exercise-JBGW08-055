package com.nhnacademy.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;

public class DbUtils {
    public DbUtils(){
        throw new IllegalStateException("Utility class");
    }

    private static final DataSource DATASOURCE;

    static {
        BasicDataSource basicDataSource = new BasicDataSource();
        //todo#0 {ip},{databases},{username},{password}설정
        basicDataSource.setUrl("jdbc:mysql://133.186.241.167/nhn_academy_55");
        basicDataSource.setUsername("nhn_academy_55");
        basicDataSource.setPassword("h*BkDY*BIPnJb7MA");

        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxTotal(5);
        basicDataSource.setMaxIdle(5); // 커넥션풀이 반납될떄 최대 유지가능한 연결 수
        basicDataSource.setMinIdle(5); // 최소한으로 유지될 연결 수

        basicDataSource.setMaxWait(Duration.ofSeconds(2)); //
        basicDataSource.setValidationQuery("select 1");// 연결 풀이  연결을 반환하기 전에 해당풀의 연결여부 확인
        basicDataSource.setTestOnBorrow(true); //풀에서 연결ㅇ르 사용하기 위해서 유효성 검사여부
        DATASOURCE = basicDataSource;
    }

    public static DataSource getDataSource(){
        return DATASOURCE;
    }

}