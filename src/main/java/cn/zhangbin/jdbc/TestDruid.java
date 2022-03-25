package cn.zhangbin.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class TestDruid {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(TestDruid.class.getClassLoader().getResourceAsStream("druid.properties"));
        DataSource ds = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = ds.getConnection();
        System.out.println(connection);
    }
}
