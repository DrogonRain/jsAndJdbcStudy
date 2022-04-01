package cn.zhangbin.jdbc;

import cn.zhangbin.jdbc.util.DBUtils;
import cn.zhangbin.jdbc.vo.Teacher;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class JdbcTest {

    @Test
    public void test1() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
        //1.数据库连接的4个基本要素：
        String url = "jdbc:mysql://localhost:3306/studytest?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
        String user = "root";
        String password = "q1127965";
        //8.0之后名字改了  com.mysql.cj.jdbc.Driver
        String driverName = "com.mysql.jdbc.Driver";

        //2.实例化Driver
        Class clazz = Class.forName(driverName);
        Driver driver = (Driver) clazz.newInstance();
        //3.注册驱动
        DriverManager.registerDriver(driver);
        //4.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    @Test
    public void test8(){
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("张三",80);
        map.put("李四",60);
        map.put("王五",70);
        map.put("赵六",90);
        Map<String, Integer> change = change(map, 60);
        System.out.println("change.toString() = " + change.toString());
    }

    public static <K> Map<K,Integer> change(Map<K,Integer> map,int condition){
        Map<K,Integer> map1 = new HashMap<K, Integer>();
        for (Map.Entry<K,Integer> emap : map.entrySet()){
            if (emap.getValue() > condition){
                map1.put(emap.getKey(),emap.getValue());
            }
        }
        return map1;
    }

    @Test
    public void test2() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
        //1.数据库连接的4个基本要素：
        String url = "jdbc:mysql://localhost:3306/studytest?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
        String user = "root";
        String password = "q1127965";
        //8.0之后名字改了  com.mysql.cj.jdbc.Driver
        String driverName = "com.mysql.jdbc.Driver";

        //3.注册驱动
        Class.forName(driverName);
        //4.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    @Test
    public void test3(){
//        String sql = "INSERT INTO teacher(id,name) VALUES(6,'老孙')";
//        String sql = "DELETE FROM teacher WHERE id = 6";
        String sql = "SELECT * FROM teacher";
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            //4.获取连接
            conn = DBUtils.getConnection();
            // 5. 获取执行
            statement = conn.createStatement();
            // 6. 执行sql
//        int i = statement.executeUpdate(sql);
//        if (i == 1){
//            System.out.println("SUCCESS");
//        }
            // 7. 查询
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                System.out.print(resultSet.getInt("id")+"、"+resultSet.getString("name")+"\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtils.closeAll(conn,statement,resultSet);
        }
    }

        @Test
        public void test4() {
        String sql = "INSERT INTO teacher(id,name) VALUES(?,?)";
        update(sql,6,"张三");
    }

    public void update(String sql,Object... args){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1.获取连接
            conn = DBUtils.getConnection();
            //2.获取PreparedStatement的实例
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }

            //4.执行sql语句
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeAll(conn,ps);
        }
    }

    @Test
    public void test5(){
        try {
            // 根据全限定名获取类
            Class<?> clazz = Class.forName("cn.zhangbin.jdbc.vo.Teacher");
            String sql = "SELECT * FROM teacher WHERE id = ?";
            Object instance = getInstance(clazz, sql, 1);
            if (instance instanceof Teacher){
                Teacher teacher = (Teacher) instance;
                System.out.println(teacher);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public <T> T getInstance(Class<T> clazz,String sql,Object... args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            // 1. 获取数据库链接
            conn = DBUtils.getConnection();

            // 2.预编译sql语句得到PreparedStatement对象
            ps = conn.prepareStatement(sql);

            // 3.填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }

            // 4. 执行executeQuery(),得到结果集 ResultSet
            rs = ps.executeQuery();

            // 5. 得到结果集的元数据: ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();

            // 6.1 通过rsmd得到 列总数和列标 通过ResultSet得到列值
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){
                // 创建一个指定类
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) { // 遍历每一列
                    // 获取列值
                    Object columnVal = rs.getObject(i + 1);
                    // 获取列的别名
                    String columnName = rsmd.getColumnLabel(i + 1);
                    // 6.2 使用反射给对象的相应属性赋值
                    Field field = clazz.getDeclaredField(columnName);
                    // 暴力注入
                    field.setAccessible(true);
                    field.set(t,columnVal);
                }
                return t;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtils.closeAll(conn,ps,rs);
        }
        return null;
    }

    @Test
    public void test6(){
        Connection conn = null;
        try{
            // 1. 建立连接
            conn = DBUtils.getConnection();
            // 2. 开启事务
            conn.setAutoCommit(false);
            // 3. 进行数据库操作
            String sql = "UPDATE course SET name = ? WHERE  id = ?";
            update(sql,"javaScript",5);

            String sql2 = "UPDATE course SET name = ? WHERE id = ?";
            update(sql2,"math",1);
            // 4. 若没有异常则提交事务
            conn.commit();
        }catch (Exception e){
            e.printStackTrace();
            // 5.若有异常,则回滚事务
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }finally {
            try{
                // 6.回复每次操作的自动提交功能
                conn.setAutoCommit(true);
            }catch (SQLException e){
                e.printStackTrace();
            }
            // 7.关闭链接
            DBUtils.closeAll(conn,null,null);
        }
    }
}
