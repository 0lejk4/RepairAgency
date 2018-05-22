package com.gelo.util.tags;


import com.gelo.model.domain.PermissionType;
import com.gelo.model.domain.User;
import com.gelo.util.SecurityUtils;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * The type Permission check tag.
 */
public class PermissionCheckTag extends BodyTagSupport {
    private Logger logger = Logger.getLogger(PermissionCheckTag.class);

    private PermissionType permissionType;
    private User user;

    /**
     * Instantiates a new Permission check tag.
     */
    public PermissionCheckTag() {
    }

    /**
     * Instantiates a new Permission check tag.
     *
     * @param permissionType the permission type
     * @param user           the user
     */
    public PermissionCheckTag(PermissionType permissionType, User user) {
        this.permissionType = permissionType;
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
     * Sets permission type.
     *
     * @param permissionType the permission type
     */
    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    public void setBodyContent(BodyContent bc) {
        super.setBodyContent(bc);
    }

    /**
     * Performs check if the user has permission type and
     * shows body content if user has that permission.
     * Uses SecurityUtils for this purposes
     *
     * @return status code that orders to skip body rendering
     */
    public int doAfterBody() {
        try {
            BodyContent bodyContent = super.getBodyContent();
            String bodyString = bodyContent.getString();
            JspWriter out = bodyContent.getEnclosingWriter();
            if (permissionType == null || (user != null && SecurityUtils.hasPermission(user, permissionType))) {
                out.print(bodyString);
            }

            bodyContent.clear();
        } catch (IOException e) {
            logger.error("Error in PermissionCheckTag.doAfterBody()", e);
            e.printStackTrace();
        }

        return SKIP_BODY;
    }
}
