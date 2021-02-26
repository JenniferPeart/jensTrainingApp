package com.jen.umsbackend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.jen.umsbackend.users.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// DECORATOR PATTERN 
// -- allows behavior to be added to an individual object,
// dynamically, without affecting the behavior of other objects from the same class.
// -- often useful for adhering to the Single Responsibility Principle, as it allows
// functionality to be divided between classes with unique areas of concern.
// -- can be more efficient than subclassing, because an object's behavior can
// be augmented without instantiating an entirely new object
// -- nearly identical to the CHAIN-OF-RESPONSIBILITY pattern, except all classes
// handle the request, while for the chain of responsibility,
// exactly one of the classes in the chain handles the request

// UserDetails - provides core user information that can be used for authentication
// GrantedAuthority - represents an authority granted to an Authentication object
public class UserPrincipal implements UserDetails {
       
    private User user;

    // A kind of version control - update when changes to the class will break
    // the compatibility with the existing serialised class
    private static final long serialVersionUID = 1L;

    public UserPrincipal(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Extract roles (ROLE_name) from user and add to list of authority objects
        this.user.getRoleList().forEach(role -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
            authorities.add(authority);
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
