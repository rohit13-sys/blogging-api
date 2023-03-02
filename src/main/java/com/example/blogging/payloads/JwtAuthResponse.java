package com.example.blogging.payloads;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {

    private String token;
}
