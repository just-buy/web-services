package com.hackathon.ultimate.hackers;

import lombok.*;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Getter
@Setter
public class Credentials {
    private final String username;
    private final String password;

    public Credentials() {
        this.username = "";
        this.password = "";
    }
}
