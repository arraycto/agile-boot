package com.huijiewei.agile.core.user.request;

import com.huijiewei.agile.core.constraint.FieldMatch;
import com.huijiewei.agile.core.constraint.Phone;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@Data
@FieldMatch(field = "password", fieldMatch = "passwordConfirm", message = "密码与密码确认必须相同")
public class UserRequest {
    @NotBlank(message = "手机号码不能为空")
    @Phone(message = "无效的手机号码")
    private String phone;

    @NotBlank(message = "电子邮箱不能为空")
    @Email(message = "无效的电子邮箱")
    private String email;

    @NotBlank(message = "密码不能为空", groups = UserRequest.Create.class)
    private String password;

    @NotBlank(message = "密码确认不能为空", groups = UserRequest.Create.class)
    private String passwordConfirm;

    @NotNull
    private String name;

    @NotNull
    private String avatar;

    public interface Create extends Default {
    }

    public interface Edit extends Default {
    }
}
