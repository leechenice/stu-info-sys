package lee.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/stu_info?useSSL=true&characterEncoding=UTF-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "92727";

    private volatile static DataSource DS;
    private DBUtil(){}
    private static DataSource getDataSource() {
        if(DS == null) {
            synchronized(DBUtil.class) {
                if (DS == null) {
                    DS = new MysqlDataSource();
                    ((MysqlDataSource)DS).setURL(URL);
                    ((MysqlDataSource)DS).setUser(USERNAME);
                    ((MysqlDataSource)DS).setPassword(PASSWORD);
                }
            }
        }
        return DS;
    }
    public static Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("获取数据库连接失败",e);
        }
    }
    public static void close(Statement s, Connection c) {
        close(s,c,null);
    }
    public static void close(Statement s, Connection c, ResultSet r) {
        try {
            if (s != null) s.close();
            if (c != null) c.close();
            if (r != null) r.close();
        }catch (SQLException e) {
            throw new RuntimeException("释放数据库资源失败",e);
        }
    }

}
