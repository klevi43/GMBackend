package org.kylecodes.gm.dtos;

public class JwtDto {
    private String payload;

    public JwtDto(String payload) {
        this.payload = payload;
    }

    public JwtDto() {
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
