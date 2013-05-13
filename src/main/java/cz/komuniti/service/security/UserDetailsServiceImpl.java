
package cz.komuniti.service.security;


import cz.komuniti.model.entity.UserEntity;
import cz.komuniti.service.info.InfoMessages;
import cz.komuniti.service.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author novakst6
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private @Autowired UserManager userManager; 
    
    private @Autowired Assembler assembler; 
    
    private @Autowired InfoMessages infoMessages;
    
    @Override @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException, DataAccessException {
                    
            System.out.println("SECURITY> Searching user [ "+string+" ]");
            
            UserEntity user = userManager.findByEmail(string);
            System.out.println("SECURITY> USER "+user);
            try{
                System.out.println("SECURITY> Found user [ "+user.getEmail()+" ]");
            } catch(Exception e){
                System.out.println("SECURITY> Can't found user [ "+string+" ]");
            }
            if(user == null){
                throw new UsernameNotFoundException("SECURITY> Can't find user [ "+string+" ]");
            }
            if(!user.getActive()){
                infoMessages.setWarnMessage("Uživatelský účet není ještě aktivovaný, počkejte až bude váš účet aktivovaný.");
                throw new UsernameNotFoundException("Neaktivovany");
            }
            if(user.getDeleted()){
                infoMessages.setWarnMessage("Uživatelský účet byl smazán.");
                throw new UsernameNotFoundException("Smazaný");
            }
            
            return assembler.buildUserFromUserEntity(user);

    }
    

    public void setAssembler(Assembler assembler) {
        this.assembler = assembler;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setInfoMessages(InfoMessages infoMessages) {
        this.infoMessages = infoMessages;
    }

}
