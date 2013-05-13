/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.security;

import cz.komuniti.model.entity.UserEntity;
import cz.komuniti.service.manager.UserManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */
@Service("registredGoogleAuthenticationHandler")
public class RegistredGoogleAuthenticationHandler extends SimpleUrlAuthenticationFailureHandler {

    private @Autowired
    UserManager userManager;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof UsernameNotFoundException && exception.getAuthentication() instanceof OpenIDAuthenticationToken
                && ((OpenIDAuthenticationToken) exception.getAuthentication()).getStatus().equals(OpenIDAuthenticationStatus.SUCCESS)) {
            DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            request.getSession(true).setAttribute("USER_OPENID_CREDENTIAL", ((UsernameNotFoundException) exception).getExtraInformation());
            // redirect to create account page
            String email = ((OpenIDAuthenticationToken) exception.getAuthentication()).getAttributes().get(0).getValues().get(0);
            String fn = "BEZ";
            String ln = "JMÉNA";
            try {
                fn = ((OpenIDAuthenticationToken) exception.getAuthentication()).getAttributes().get(1).getValues().get(0);
                ln = ((OpenIDAuthenticationToken) exception.getAuthentication()).getAttributes().get(2).getValues().get(0);
            } catch (Exception e) {
                System.out.println("ERROR REG GOOGLE AUTH> "+e.getMessage());
                redirectStrategy.sendRedirect(request, response, "/index.htm");
            }
            request.getSession().setAttribute("email", email);
            request.getSession().setAttribute("firstName", fn);
            request.getSession().setAttribute("lastName", ln);
            UserEntity user = userManager.findByEmail(email);
            if (user == null) {
                redirectStrategy.sendRedirect(request, response, "/registrationOID.htm");
            } else {
                redirectStrategy.sendRedirect(request, response, "/index.htm");
            }

        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}
