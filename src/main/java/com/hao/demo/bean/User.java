package com.hao.demo.bean;




import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(description="All details about the user. ")
public class User {
    private long id;

    @NotNull
    @Size(min=2, message="Name should have at least 2 characters")
    @ApiModelProperty(notes="Name should have at least 2 characters")
    private String firstName;
    @NotNull
    private String lastName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}