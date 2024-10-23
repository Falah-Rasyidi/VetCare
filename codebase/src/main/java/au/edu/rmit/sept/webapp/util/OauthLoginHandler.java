package au.edu.rmit.sept.webapp.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.models.UserClass;
import au.edu.rmit.sept.webapp.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

import java.io.IOException;


@Controller
public class OauthLoginHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private UserService service;
    private smtpSenderService senderService;
    @Autowired
    OauthLoginHandler(UserService service, smtpSenderService smtpSenderService) {
        this.service = service;
        this.senderService = smtpSenderService;
    }

    OauthLoginHandler(){}


    // Add validators to here to check if user already exists in system
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws ServletException, IOException {
        DefaultOidcUser authUser = (DefaultOidcUser) auth.getPrincipal();
        // If user exists already, add them to session
        // If user does not exist make a new one then add to session
        boolean emailDuplicate = service.findUser(authUser.getEmail()).isPresent();
        if(!emailDuplicate) {
            service.addUser(new UserClass(
                authUser.getGivenName(),
                authUser.getFamilyName(),
                authUser.getIdToken().getAccessTokenHash(),
                null,
                authUser.getEmail()
            ));
            senderService.sendEmail(
                    authUser.getEmail(),
                    "Confirmation of Account Creation with Vetcare",
                    "Thank you for making an account with Vetcare.\n"+
                            "We strive to give you access to vets when you need them\n\n"+
                            "If you did not create this account please contact us at sept.petcare@gmail.com");

        }

        System.out.printf("%s has Logged in using OAuth%n",authUser.getEmail());
        // Store user details and redirect to homepage
        request.getSession().setAttribute(LocalStorage.LOGGED_IN_USER_ID, service.findUser(authUser.getEmail()).get());
        response.sendRedirect("/user_profile");
    }
}
