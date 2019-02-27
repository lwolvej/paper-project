package org.duohuo.paper.model.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author lwolvej
 */
public class UserDto implements Serializable {

    private static final long serialVersionUID = 6355376861747174962L;

    private String userName;

    private String password;

    private String uuid;

    private String code;

    public UserDto() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(userName, userDto.userName) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(uuid, userDto.uuid) &&
                Objects.equals(code, userDto.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, uuid, code);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", uuid='" + uuid + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
