package com.gelo.controller.router.handlers;

import com.gelo.controller.router.annotation.PostMapping;
import com.gelo.controller.router.annotation.PreAuthorize;
import com.gelo.model.domain.RoleType;
import com.gelo.services.UserService;
import com.gelo.util.BeanStorage;
import com.gelo.util.PasswordUtils;
import com.gelo.util.Transport;
import com.gelo.util.constants.Paths;
import com.gelo.validation.Alert;
import com.gelo.validation.forms.UserCreationForm;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gelo.validation.Alert.single;

/**
 * Handler for admin`s user creation action.
 * Form data is validated by UserCreationForm class.
 * It checks if the user with input email exists and shows warning message to admin.
 * Also handler encodes password using PasswordUtils method.
 */
@PostMapping
@PreAuthorize(role = RoleType.ROLE_ADMIN)
public class AdminUserCreationHandler implements Handler {
    private Logger logger = Logger.getLogger(AdminUserCreationHandler.class);
    UserService userService = BeanStorage.INSTANCE.get(UserService.class);

    @Override
    public Transport execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserCreationForm form = new UserCreationForm(request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("country"),
                request.getParameter("password"),
                request.getParameter("roleId"));

        if (form.valid()) {
            form.setPassword(PasswordUtils.encryptPassword(form.getPassword()));
            boolean status;

            if (userService.emailTaken(form.getEmail())) {
                request.setAttribute("alerts", single(Alert.danger("email.taken")));
            } else {
                status = userService.save(form.parseUser());
                if (status) {
                    logger.info("User registered with email=" + form.getEmail());
                    request.setAttribute("alerts", single(Alert.success("admin.register.success")));
                } else {
                    return Transport.absolute(Paths.SERVER_ERROR);
                }
            }
        } else {
            request.setAttribute("alerts",
                    form.getErrorList());
        }
        return Transport.absolute(Paths.ADMIN_PAGE);
    }
}
