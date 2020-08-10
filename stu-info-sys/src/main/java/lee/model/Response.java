package lee.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Response {

    private boolean success;//业务是否处理成功
    private Integer total;
    private String code;//错误码
    private String message;//错误字段
    private Object data;//业务字段
    private String stackTrace;//出现异常时，堆栈信息
}
