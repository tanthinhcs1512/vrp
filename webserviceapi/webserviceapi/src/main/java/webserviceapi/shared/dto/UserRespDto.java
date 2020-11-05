package webserviceapi.shared.dto;

import java.io.Serializable;

public class UserRespDto  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;
    private String userId;
    private String email;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userID) {
        this.userId = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
