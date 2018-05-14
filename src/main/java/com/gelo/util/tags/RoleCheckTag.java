package com.gelo.util.tags;

import com.gelo.model.domain.RoleType;
import com.gelo.model.domain.User;
import com.gelo.util.SecurityUtils;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * The type Role check tag.
 */
public class RoleCheckTag extends BodyTagSupport {
    private Logger logger = Logger.getLogger(RoleCheckTag.class);
    private RoleType roleType;
    private User user;

    /**
     * Instantiates a new Role check tag.
     */
    public RoleCheckTag() {
    }

    /**
     * Instantiates a new Role check tag.
     *
     * @param roleType the role type
     * @param user     the user
     */
    public RoleCheckTag(RoleType roleType, User user) {
        this.roleType = roleType;
        this.user = user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets role type.
     *
     * @param roleType the role type
     */
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public void setBodyContent(BodyContent bc) {
        super.setBodyContent(bc);
    }

    /**
     * Performs check if the user has role type and
     * shows body content if user has that role.
     * Uses SecurityUtils for this purposes
     * @return status code that orders to skip body rendering
     */
    public int doAfterBody() {
        try {
            BodyContent bodyContent = super.getBodyContent();
            String bodyString = bodyContent.getString();

            JspWriter out = bodyContent.getEnclosingWriter();
            if (roleType == null || (user != null && SecurityUtils.hasRole(user, roleType))) {
                out.print(bodyString);
            }
            bodyContent.clear();
        } catch (IOException e) {
            logger.error("Error in RoleCheckTag.doAfterBody()", e);
            e.printStackTrace();
        }

        return SKIP_BODY;
    }
}
