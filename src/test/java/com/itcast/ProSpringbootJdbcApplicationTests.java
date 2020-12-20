package com.itcast;

import com.itcast.service.TestAspectServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAspectJAutoProxy
public class ProSpringbootJdbcApplicationTests {

    @Autowired
    TestAspectServiceImpl testAspectServiceImpl;

    @Test
    public void contextLoads() throws SQLException, InterruptedException {
        testAspectServiceImpl.test();
    }



}
