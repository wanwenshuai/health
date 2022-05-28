package com.home.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.sql.DataSource;

/**
 * @date 2022/4/18 17:32
 */
@SpringBootTest
public class Test {
    @Autowired
    DataSource dataSource;
    public static void main(String[] args) {

    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
