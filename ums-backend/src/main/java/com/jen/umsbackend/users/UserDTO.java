package com.jen.umsbackend.users;

public class UserDTO {

    private long id;
    private String fullName;
    private String email;

    public UserDTO() {}

    public UserDTO(long id, String fullName, String email) {
        super();
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public UserDTO(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
