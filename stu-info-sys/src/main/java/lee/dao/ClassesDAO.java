package lee.dao;

import lee.model.Classes;
import lee.model.DictionaryTag;
import lee.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClassesDAO {
    public static List<Classes> queryAsDict() {
        List<Classes> list = new ArrayList<>();
        try(Connection c = DBUtil.getConnection()) {
            String sql = "select id, classes_name, classes_graduate_year, classes_major from classes";
            try(PreparedStatement ps = c.prepareStatement(sql)) {
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                       Classes cl = new Classes();
                       cl.setDictionaryTagKey(String.valueOf(rs.getInt("id")));
                       cl.setDictionaryTagValue(rs.getString("classes_name"));
                       cl.setClassesGraduateYear(rs.getString("classes_graduate_year"));
                       cl.setClassesMajor(rs.getString("classes_major"));
                       list.add(cl);
                    }
                    return list;
                }
            }
        }catch (Exception e) {
            throw new RuntimeException("查询班级数据字典出错", e);
        }
    }
}
