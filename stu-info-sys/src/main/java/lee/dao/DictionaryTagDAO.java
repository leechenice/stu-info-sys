package lee.dao;

import lee.model.DictionaryTag;
import lee.util.DBUtil;
import org.omg.SendingContext.RunTime;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DictionaryTagDAO {
    public static List<DictionaryTag> query(String key) {
        List<DictionaryTag> tags = new ArrayList<>();
        try(Connection c = DBUtil.getConnection()) {
            String sql = "select concat(d.dictionary_key, dt.dictionary_tag_key) dictionary_tag_key," +
                    "       dt.dictionary_tag_value" +
                    " from dictionary d" +
                    "         join dictionary_tag dt on d.id = dt.dictionary_id" +
                    " where d.dictionary_key = ?";
            try(PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1,key);
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        DictionaryTag tag = new DictionaryTag();
                        tag.setDictionaryTagKey(rs.getString("dictionary_tag_key"));
                        tag.setDictionaryTagValue(rs.getString("dictionary_tag_value"));
                        tags.add(tag);
                    }
                    return tags;
                }
            }
        }catch (Exception e) {
            throw new RuntimeException("查询数据字典标签出错", e);
        }
    }

}
