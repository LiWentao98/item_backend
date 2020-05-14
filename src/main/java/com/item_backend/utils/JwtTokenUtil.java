package com.item_backend.utils;

import com.item_backend.config.JwtConfig;
import com.item_backend.model.dto.UserDto;
import com.item_backend.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

@Component
public class JwtTokenUtil implements Serializable {


    private static final String CLAIM_KEY_UID = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_ROLES = "roles";
    private static final long serialVersionUID = 3001653071458011111L;

    @Autowired
    private JwtConfig jwtConfig;


    /**
     * 从request中获取用户id
     *
     * @param request
     * @return
     */
    public Integer getUIDFromRequest(HttpServletRequest request) {
        String token = request.getHeader(jwtConfig.getHeader());
        token = token.substring(jwtConfig.getPrefix().length());

        return token == null ? null : getUIDFromToken(token);
    }

    /**
     * 从token中获取用户id
     *
     * @param token
     * @return
     */
    public Integer getUIDFromToken(String token) {
        Integer uId = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            // key为“sub”
            String str = claims.getSubject();

            if (claims.getSubject() != null) {
                uId = Integer.valueOf(str);
            }

        } catch (Exception e) {
            uId = null;
        }
        return uId;
    }


    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * token是否过期
     * true 过期 false 未过期
     *
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        boolean isExpired = expiration.before(new Date());
        return isExpired;
    }

    /**
     * 从token中获取过期时间
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * @Description: 解密获取用户信息
     * @Author: Mt.Li
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成过期时间 单位[ms]
     *
     * @return
     */
    private Date generateExpirationDate() {
        // 当前毫秒级时间 + yml中的time * 1000
        return new Date(System.currentTimeMillis() + jwtConfig.getTime() * 1000);
    }


    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }


    /**
     * 根据提供的用户详细信息生成token
     *
     * @param userDto
     * @return
     */
    public String generateToken(UserDto userDto) {
        Map<String, Object> claims = new HashMap<>(3);
        claims.put(CLAIM_KEY_UID, userDto.getUser().getU_id()); // 放入用户名
        claims.put(CLAIM_KEY_CREATED, new Date()); // 放入token生成时间
        claims.put(CLAIM_KEY_ROLES, userDto.getUserType().getU_type_name()); // 放入用户类型名

        return generateToken(claims);
    }

    /**
     * 生成token（JWT令牌）
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
    }

    /**
     * @Description: 判断是否要刷新token
     * @Author: Mt.Li
     */
    public Boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    /**
     * @Description: 刷新token，代表当前用户活跃，重新赋予30分钟
     * @Author: Mt.Li
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 校验token
     *
     * @param token
     * @param user
     * @return
     */
    public Boolean validateToken(String token, User user) {
        final Integer uId = getUIDFromToken(token);  //从token中取出用户名
        return ((uId == user.getU_id())
                &&
                !isTokenExpired(token) //校验是否过期

        );
    }

    /**
     * 从token中获取用户角色
     *
     * @param authToken
     * @return
     */
    public String getRolesFromToken(String authToken) {
        String roles;
        try {
            final Claims claims = getClaimsFromToken(authToken);
            roles = (String) claims.get(CLAIM_KEY_ROLES);
        } catch (Exception e) {
            roles = null;
        }
        return roles;
    }
}

