package lee.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class Classes extends DictionaryTag{
    private Integer id;
    private String classesName;
    private String classesGraduateYear;
    private String classesMajor;
    private String classesDesc;
    private Date createTime;
}
