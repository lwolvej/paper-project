package org.duohuo.paper.config.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author lwolvej
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 4933049886483111394L;

    private String token;

    JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
