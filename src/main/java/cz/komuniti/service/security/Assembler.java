package cz.komuniti.service.security;

import cz.komuniti.model.entity.UserEntity;
import cz.komuniti.model.entity.UserRoleEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author novakst6
 */
@Service("assembler")
public class Assembler {
    
    @Transactional(readOnly = true)
    public User buildUserFromUserEntity(UserEntity userEntity){
    
    String username = userEntity.getEmail();
    String password = userEntity.getPassword();
        System.out.println("SECURITY> Username ["+username+"]");
        System.out.println("SECURITY> Password ["+password+"]");
        
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean accoountNonLocked = true;
    
    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    Set<UserRoleEntity> roles = userEntity.getRoles();
    try{
        
    }catch(Exception e){
        
    }
    for(UserRoleEntity role: roles){
        authorities.add(new SimpleGrantedAuthority(role.getName()));
    }

    User user = new User(username, password, enabled, accountNonExpired, accountNonExpired, accoountNonLocked, authorities);
 
    
    return user;
    }
}
