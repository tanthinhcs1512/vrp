package webserviceapi.model.response;

import webserviceapi.shared.dto.UserRespDto;

public class Response {
    private UserRespDto user;

    public UserRespDto getUser() {
        return user;
    }

    public void setUser(UserRespDto user) {
        this.user = user;
    }
}
