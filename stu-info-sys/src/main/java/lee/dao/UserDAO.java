package lee.dao;

import lee.model.Classes;
import lee.model.Student;
import lee.model.User;
import lee.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class UserDAO {
    public static User query(User user) {
        User query = null;
        try(Connection c = DBUtil.getConnection()) {
            String sql = "select id, nickname, email, create_time from user where username=? and password=?";
            try(PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1,user.getUsername());
                ps.setString(2,user.getPassword());
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        query = user;
                        query.setId(rs.getInt("id"));
                        query.setNickname(rs.getString("nickname"));
                        query.setEmail(rs.getString("email"));
                        query.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                    }
                    return query;
                }
            }
        }catch (Exception e) {
            throw new RuntimeException("登录校验用户名密码出错", e);
        }
    }
}
