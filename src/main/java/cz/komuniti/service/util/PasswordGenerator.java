/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.util;

import java.security.SecureRandom;
import java.util.Random;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */
@Service("PasswordGenerator")
public class PasswordGenerator {
    public String getPassword(){
        Random rand = new SecureRandom();
        String password = "";
        for (int i = 0; i < 6; i++) {
            double d = rand.nextDouble();
            if (d < 0.3 && d >= 0.0) {
                int number = rand.nextInt(10);
                password += number;
            } else if (d >= 0.3 && d < 0.6) {
                int r = rand.nextInt(26);
                String upperCase = String.valueOf((char) (r + 65));
                password += upperCase;
            } else {
                int r = rand.nextInt(26);
                String lowerCase = String.valueOf((char) (r + 97));
                password += lowerCase;
            }
        }
            return password;
    }
}
