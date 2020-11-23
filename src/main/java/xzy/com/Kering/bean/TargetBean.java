package xzy.com.Kering.bean;

import java.util.Date;

import xzy.com.Kering.annotations.CopyField;

/**
 * @author xzy.ajiu
 * Created by on @date 2020/11/23 10:28
 */
public class TargetBean {


    private  String name ;
    private String age;
    private int id;
    @CopyField(targetName = "birthDay",targetType="String")
    private Date birth;

    @Override
    public String toString() {
        return "TargetBean{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", id=" + id +
                ", birth=" + birth +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}
