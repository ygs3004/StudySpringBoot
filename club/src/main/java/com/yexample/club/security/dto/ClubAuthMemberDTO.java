package com.yexample.club.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User implements OAuth2User {

    private String email;
    private String name;
    private boolean fromSocial;
    private Map<String, Object> attr;

    public ClubAuthMemberDTO(
            String username,
            String password,
            boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;
    }

    public ClubAuthMemberDTO(
            String username,
            String password,
            boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> attr) {
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    @Override
    public void eraseCredentials() {
        // User 클래스 eraseCredentials 에서 password 정보를 지워버리므로 오버라이드 하여 password를 유지한다.
    }

}
