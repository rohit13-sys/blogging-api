package com.example.blogging.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Data
public class JwtAuthRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
