package com.cts.scb.userauthentication.util;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cts.scb.userauthentication.entity.MyUserDetails;
import com.cts.scb.userauthentication.principal.UserPrincipal;
import com.cts.scb.userauthentication.principal.UserRole;

import java.util.ArrayList;
import java.util.Collection;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private String SECRET_KEY = "secret";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
  
	
	  public String extractRoles(String token) { 
		  return extractClaim(token, Claims::getAudience);
		  }
	 
    
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Set<String> Userroles = new HashSet<>();
      //  List<String> roles = new ArrayList<String>();
        String roles = "";
      // userDetails.getAuthorities().forEach((a) -> roles.add(a.getAuthority()));
        for(GrantedAuthority role:userDetails.getAuthorities()){
         roles = role.getAuthority();
        }
      //  claims.put("Roles",Userroles.toArray());
        return createToken(claims, userDetails.getUsername(),roles);
    }

    private String createToken(Map<String, Object> claims, String subject, String role) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setAudience(role).
        		setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    public static boolean checkRole(Collection<? extends GrantedAuthority> authorities, UserRole userRole) {
		
    	for(GrantedAuthority authority: authorities) {
    		if(authority.getAuthority().contains(UserRole.ROLE_ADMIN.toString() ))
    				return true;
    	}
		
		return false;
    }
}