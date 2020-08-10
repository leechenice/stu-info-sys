package lee.dao;

import lee.model.Classes;
import lee.model.Page;
import lee.model.Student;
import lee.util.DBUtil;
import lee.util.ThreadLocalHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDAO {
    public static List<Student> query(Page p) {
        List<Student> list = new ArrayList<>();
        try(Connection c = DBUtil.getConnection()) {
            StringBuilder sql = new StringBuilder("select s.id," +
                    "       s.student_name," +
                    "       s.student_no," +
                    "       s.id_card," +
                    "       s.student_email," +
                    "       s.classes_id," +
                    "       s.create_time," +
                    "       c.id cid," +
                    "       c.classes_name," +
                    "       c.classes_graduate_year," +
                    "       c.classes_major," +
                    "       c.classes_desc" +
                    "   from student s" +
                    "         join classes c on s.classes_id = c.id");
            if(p.getSearchText() != null && p.getSearchText().trim().length() > 0) {
                sql.append("   where s.student_name like ?");
            }
            if(p.getSortOrder() != null && p.getSortOrder().trim().length() > 0) {
                sql.append("   order by s.create_time ").append(p.getSortOrder());
            }
            StringBuilder countSql = new StringBuilder();
            countSql.append("select count(0) count from (");
            countSql.append(sql);
            countSql.append(")tmp");


            try(PreparedStatement ps1 = c.prepareStatement(countSql.toString())) {
                if(p.getSearchText() != null && p.getSearchText().trim().length() > 0) {
                    ps1.setString(1,"%"+p.getSearchText()+"%");
                }

                try(final ResultSet resultSet = ps1.executeQuery()) {
                    while (resultSet.next()) {
                        int count = resultSet.getInt("count");
                        ThreadLocalHolder.getTOTAL().set(count);
                    }
                }
            }

            sql.append("   limit ?,?");

            try(PreparedStatement ps2 = c.prepareStatement(sql.toString())) {
                int index = 1;
                if(p.getSearchText() != null && p.getSearchText().trim().length() > 0) {
                    ps2.setString(index++,"%"+p.getSearchText()+"%");
                }
                ps2.setInt(index++,(p.getPageNumber()-1)*p.getPageSize());
                ps2.setInt(index,p.getPageSize());
                try(ResultSet rs = ps2.executeQuery()) {
                    while(rs.next()) {
                        Student st = new Student();
                        st.setId(rs.getInt("id"));
                        st.setStudentName(rs.getString("student_name"));
                        st.setStudentNo(rs.getString("student_no"));
                        st.setIdCard(rs.getString("id_card"));
                        st.setStudentEmail(rs.getString("student_email"));
                        st.setClassesId(rs.getInt("classes_id"));
                        st.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                        Classes cl = new Classes();
                        cl.setId(rs.getInt("cid"));
                        cl.setClassesName(rs.getString("classes_name"));
                        cl.setClassesGraduateYear(rs.getString("classes_graduate_year"));
                        cl.setClassesMajor(rs.getString("classes_major"));
                        cl.setClassesDesc(rs.getString("classes_desc"));
                        st.setClasses(cl);

                        list.add(st);
                    }
                    return list;
                }
            }
        }catch (Exception e) {
            throw new RuntimeException("查询学生列表出错", e);
        }
    }

    public static Student queryById(int id) {
        Student st = new Student();
        try(Connection c = DBUtil.getConnection()) {
            String sql = "select s.id," +
                    "       s.student_name," +
                    "       s.student_no," +
                    "       s.id_card," +
                    "       s.student_email," +
                    "       s.classes_id," +
                    "       s.create_time," +
                    "       c.id cid," +
                    "       c.classes_name," +
                    "       c.classes_graduate_year," +
                    "       c.classes_major," +
                    "       c.classes_desc" +
                    "   from student s" +
                    "         join classes c on s.classes_id = c.id" +
                    "   where s.id=?";
            try(PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1,id);
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        st.setId(rs.getInt("id"));
                        st.setStudentName(rs.getString("student_name"));
                        st.setStudentNo(rs.getString("student_no"));
                        st.setIdCard(rs.getString("id_card"));
                        st.setStudentEmail(rs.getString("student_email"));
                        st.setClassesId(rs.getInt("classes_id"));
                        st.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                        Classes cl = new Classes();
                        cl.setId(rs.getInt("cid"));
                        cl.setClassesName(rs.getString("classes_name"));
                        cl.setClassesGraduateYear(rs.getString("classes_graduate_year"));
                        cl.setClassesMajor(rs.getString("classes_major"));
                        cl.setClassesDesc(rs.getString("classes_desc"));
                        st.setClasses(cl);
                    }
                    return st;
                }
            }
        }catch (Exception e) {
            throw new RuntimeException("查询学生详情出错", e);
        }
    }

    public static void insert(Student s) {
        try(Connection c = DBUtil.getConnection()) {
            String sql = "insert into student(student_name, student_no, id_card, student_email, classes_id) values (?,?,?,?,?)";
            try(PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1,s.getStudentName());
                ps.setString(2,s.getStudentNo());
                ps.setString(3,s.getIdCard());
                ps.setString(4,s.getStudentEmail());
                ps.setInt(5,s.getClassesId());
                int num = ps.executeUpdate();
            }
        }catch (Exception e) {
            throw new RuntimeException("新增学生信息出错", e);
        }
    }

    public static void update(Student s) {
        try(Connection c = DBUtil.getConnection()) {
            String sql = "update student set student_name=?, student_no=?, id_card=?, student_email=?, classes_id=? where id=?";
            try(PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1,s.getStudentName());
                ps.setString(2,s.getStudentNo());
                ps.setString(3,s.getIdCard());
                ps.setString(4,s.getStudentEmail());
                ps.setInt(5,s.getClassesId());
                ps.setInt(6,s.getId());
                int num = ps.executeUpdate();
            }
        }catch (Exception e) {
            throw new RuntimeException("修改学生信息出错", e);
        }
    }

    public static void delete(String[] ids) {
        try(Connection c = DBUtil.getConnection()) {
            StringBuilder sql = new StringBuilder("delete from student where id in(");
            for(int i = 0; i < ids.length; i++) {
                if(i != 0) sql.append(",");
                sql.append("?");
            }
            sql.append(")");
            try(PreparedStatement ps = c.prepareStatement(sql.toString())) {
                for(int i = 0; i < ids.length; i++) {
                    ps.setInt(i+1,Integer.parseInt(ids[i]));
                }
                int num = ps.executeUpdate();
            }
        }catch (Exception e) {
            throw new RuntimeException("删除学生信息出错", e);
        }
    }
}
