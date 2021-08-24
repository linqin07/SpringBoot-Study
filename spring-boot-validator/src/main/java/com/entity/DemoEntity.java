package com.entity;

import com.validator.FlagValidator;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * Description:
 * author: LinQin
 * date: 2018/07/12
 */
public class DemoEntity implements Serializable {
    @NotBlank(message = "gggggggggggggg")
    @Length(min = 2, max = 10)
    private String name;

    @FlagValidator(values = "1,2,3")
    @Min(value = 1)
    private int age;

    @NotBlank
    @Email
    private String mail;

    // @FlagValidator(values = "1,2,3")
    // private String flag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


}