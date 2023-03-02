package com.example.blogging.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class JwtAuthRequest {

    private String username;

    private String password;
}
