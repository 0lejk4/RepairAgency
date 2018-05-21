package com.gelo.mock.entity;

import com.gelo.model.domain.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EntityMocks {

    public static Set<Permission> createManagerPermissions() {
        return new HashSet<>(Arrays.asList(
                new Permission(3L, PermissionType.ANSWER_ORDER),
                new Permission(5L, PermissionType.CHECK_MANAGE_HISTORY)
        ));
    }

    public static Review createReview() {
        return new Review(322L,
                createTestUser(),
                "Test review title",
                "Test review text",
                Review.Star.FIVE);
    }

    public static Set<Permission> createUserPermissions() {
        return new HashSet<>(Arrays.asList(
                new Permission(2L, PermissionType.CREATE_ORDER),
                new Permission(4L, PermissionType.CHECK_ORDER_HISTORY),
                new Permission(1L, PermissionType.LEAVE_REVIEW)
        ));
    }

    public static Set<Permission> createAdminPermissions() {
        return new HashSet<>(Arrays.asList(
                new Permission(9L, PermissionType.REGISTER_USER),
                new Permission(10L, PermissionType.CHECK_ALL_USERS),
                new Permission(11L, PermissionType.CHECK_ALL_ORDERS)
        ));
    }

    public static Set<Permission> createMasterPermissions() {
        return new HashSet<>(Arrays.asList(
                new Permission(6L, PermissionType.CHECK_WORK_HISTORY),
                new Permission(8L, PermissionType.ENROLL_ORDER),
                new Permission(7L, PermissionType.FINISH_ORDER)
        ));
    }

    public static Order createTestOrder() {
        return new Order(1L,
                createTestUser(),
                "Вітаю, здається зламався акамулятор.",
                new BigDecimal(547),
                createTestUser(),
                createTestUser(),
                "Справді зламався акамулятор. Ми досить швидко відремонтуємо його.",
                true,
                true);
    }

    public static User createTestUser2() {
        return new User.UserBuilder()
                .name("Test")
                .email("test1@test1.com")
                .country("Testlandia")
                .password("1234qwer")
                .role(new Role(1L, RoleType.ROLE_USER, createUserPermissions()))
                .build();
    }

    public static User createTestUser() {
        return new User.UserBuilder()
                .id(23L)
                .name("Oleg")
                .email("test@test.com")
                .country("Ukraine")
                .activeOrdersCount(0)
                .summaryOrdersCount(6)
                .password("asda213dasd")
                .role(new Role(1L, RoleType.ROLE_USER, createUserPermissions()))
                .build();
    }


}
