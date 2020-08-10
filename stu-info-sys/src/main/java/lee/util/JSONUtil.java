package lee.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class JSONUtil {
    private static final ObjectMapper M = new ObjectMapper();
    static {
        M.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    public static <T> T read(InputStream is, Class<T> tClass) {
        try {
            return M.readValue(is,tClass);
        } catch (IOException e) {
            throw new RuntimeException("json反序列化失败，传入的数据格式和Class类型不匹配",e);
        }
    }

    public static String write(Object o) {
        try {
            return M.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json序列化失败",e);
        }
    }
}
