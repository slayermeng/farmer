package org.farmer.permission;

import org.farmer.utils.MD5Tool;

/**
 * authorize
 */
public class Authentication {
    private String user;

    /**
     * MD5
     */
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = MD5Tool.encode(password);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
