package com.gelo.controller.router.handlers;

import com.gelo.controller.router.annotation.PostMapping;
import com.gelo.services.UserService;
import com.gelo.util.BeanStorage;
import com.gelo.util.PasswordUtils;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;
import com.gelo.validation.Alert;
import com.gelo.validation.forms.RegisterForm;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gelo.validation.Alert.single;

/**
 * Handles register action.
 * Validation is done using RegisterForm class.
 * If form is valid and the user with given email is
 * not registered password is encrypted and user is saved.
 */
@PostMapping
public class RegisterHandler implements Handler {
    private static Logger logger = Logger.getLogger(RegisterHandler.class);
    UserService userService = BeanStorage.INSTANCE.get(UserService.class);
    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Transport page;
        RegisterForm form = new RegisterForm(request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("country"),
                request.getParameter("password"));

        if (form.valid()) {
            boolean status;

            if (userService.emailTaken(form.getEmail())) {
                page = Transport.absolute(Paths.REGISTER_PAGE);
                request.setAttribute("alerts",
                        single(Alert.danger("email.taken")));
            } else {
                form.setPassword(PasswordUtils.encryptPassword(form.getPassword()));
                status = userService.save(form.parseUser());
                if (status) {
                    logger.info("User registered with email=" + form.getEmail());
                    page = Transport.absolute(Paths.LOGIN_PAGE);
                    request.setAttribute("alerts",
                            single(Alert.success("register.success")));
                } else {
                    page = Transport.absolute(Paths.NOT_FOUND);
                }
            }
        } else {
            page = Transport.absolute(Paths.REGISTER_PAGE);
            request.setAttribute("alerts",
                    form.getErrorList());
        }
        return page;
    }
}
