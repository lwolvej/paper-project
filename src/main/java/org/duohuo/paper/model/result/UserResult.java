package org.duohuo.paper.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author lwolvej
 */
@ApiModel("用户登录结果")
public class UserResult implements Serializable {

    private static final long serialVersionUID = 1765706175749200214L;

    @ApiModelProperty("jwt代码")
    private String jwtCode;

    @ApiModelProperty("存活时间")
    private Integer lifeTime;

    public UserResult() {
    }

    public UserResult(String jwtCode, Integer lifeTime) {
        this.jwtCode = jwtCode;
        this.lifeTime = lifeTime;
    }

    public String getJwtCode() {
        return jwtCode;
    }

    public void setJwtCode(String jwtCode) {
        this.jwtCode = jwtCode;
    }

    public Integer getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(Integer lifeTime) {
        this.lifeTime = lifeTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResult that = (UserResult) o;
        return Objects.equals(jwtCode, that.jwtCode) &&
                Objects.equals(lifeTime, that.lifeTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jwtCode, lifeTime);
    }

    @Override
    public String toString() {
        return "UserResult{" +
                "jwtCode='" + jwtCode + '\'' +
                ", lifeTime=" + lifeTime +
                '}';
    }
}
