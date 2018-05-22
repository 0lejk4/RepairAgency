package com.gelo.controller.router.handlers;

import com.gelo.controller.router.annotation.PostMapping;
import com.gelo.model.domain.User;
import com.gelo.services.UserService;
import com.gelo.util.BeanStorage;
import com.gelo.util.PasswordUtils;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;
import com.gelo.validation.Alert;
import com.gelo.validation.forms.LoginForm;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handles login action.
 * Validation is done using LoginForm class.
 * Searches user by email and if not shows alert.
 * Then checks if password is same using PasswordUtils method for comparing passwords.
 */
@PostMapping
public class LoginHandler implements Handler {
    private static Logger logger = Logger.getLogger(LoginHandler.class);
    UserService userService = BeanStorage.INSTANCE.get(UserService.class);

    @Override
    public Transport execute(HttpServletRequest request,
                             HttpServletResponse response) throws IOException, ServletException {
        Transport transport;
        LoginForm form = new LoginForm(request.getParameter("email"),
                request.getParameter("password"));

        if (form.valid()) {
            User user;

            user = userService.findByEmail(form.getEmail());

            if (user == null) {
                transport = Transport.absolute(Paths.LOGIN_PAGE);

                logger.error("User not found with email=" + form.getEmail());
                request.setAttribute("alerts",
                        Alert.single(Alert.danger("email.not.found")));

            } else {
                if (PasswordUtils.checkPassword(form.getPassword(), user.getPassword())) {
                    logger.info("User found with details=" + user);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    transport = Transport.redirect(Paths.HOME_PAGE);
                } else {
                    transport = Transport.absolute(Paths.LOGIN_PAGE);
                    logger.error("User password don`t match" + form.getEmail());
                    request.setAttribute("alerts",
                            Alert.single(Alert.danger("password.mismatch")));
                }
            }
        } else {
            transport = Transport.absolute(Paths.LOGIN_PAGE);
            request.setAttribute("alerts", form.getErrorList());
        }

        return transport;
    }
}
