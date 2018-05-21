package com.gelo.controller.router.annotation;

import com.gelo.model.domain.PermissionType;
import com.gelo.model.domain.RoleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that is used in Authorized router to handle role and permission`s security
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PreAuthorize {
    /**
     * @return - permissions that user should have to access resource, default - NONE
     */
    PermissionType[] permissions() default PermissionType.NONE;

    /**
     * @return - role that user should have to access resource, default - NONE
     */
    RoleType role() default RoleType.NONE;
}
