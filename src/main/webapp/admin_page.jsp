<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="auth" uri="/authtags" %>
<%@ page import="com.gelo.model.domain.PermissionType" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>
<html>
<head>
    <title>Order</title>
    <c:import url="WEB-INF/jspf/includes.jsp"/>

</head>
<body>

<c:import url="WEB-INF/jspf/header.jsp"/>
<div class="container-fluid">
    <auth:hasPermission user="${sessionScope['user']}" permissionType="${PermissionType.REGISTER_USER}">
        <div class="card mx-auto my-5" style="width: 47rem;">
            <div class="card-header">
                <fmt:message key="admin.register.prompt"/>
            </div>
            <div class="card-body">
                <form method="post"
                      action="<c:url value="/app/admin/user/create"/>">
                    <div class="form-group">
                        <label for="name"> <fmt:message key="field.name"/></label>:
                        <input type="text" class="form-control" name="name" id="name"/>
                    </div>
                    <div class="form-group">
                        <label for="email"><fmt:message key="field.email"/></label>:
                        <input type="text" class="form-control" name="email" id="email"/>
                    </div>
                    <div class="form-group">
                        <label for="country"><fmt:message key="field.coutry"/></label>:
                        <input type="text" class="form-control" name="country" id="country"/>
                    </div>
                    <div class="form-group">
                        <label for="password"><fmt:message key="field.password"/></label>:
                        <input type="text" class="form-control" name="password" id="password"/>
                    </div>
                    <div class="form-group">
                        <label for="roleId"><fmt:message key="field.role"/></label>:
                        <select class="form-control" name="roleId" id="roleId">
                            <option value="1"><fmt:message key="ROLE_USER"/></option>
                            <option value="2"><fmt:message key="ROLE_MANAGER"/></option>
                            <option value="3"><fmt:message key="ROLE_MASTER"/></option>
                            <option value="4"><fmt:message key="ROLE_ADMIN"/></option>
                        </select>
                    </div>
                    <button name="accepted" class="btn btn-success" type="submit">
                        <i class="fa fa-close"><fmt:message key="field.register"/> </i>
                    </button>
                </form>
            </div>
        </div>
    </auth:hasPermission>
    <div class="card text-center">
        <div class="card-header">
            <ul class="nav nav-pills nav-fill" id="v-pills-tab" role="tablist">
                <li class="nav-item"><a class="nav-link active" id="v-pills-orders-tab" data-toggle="pill"
                                        href="#v-pills-orders" role="tab"
                                        aria-controls="v-pills-orders" aria-selected="true"><fmt:message
                        key="field.orders"/> </a>
                </li>
                <li class="nav-item"><a class="nav-link" id="v-pills-users-tab" data-toggle="pill" href="#v-pills-users"
                                        role="tab"
                                        aria-controls="v-pills-users" aria-selected="false"><fmt:message
                        key="field.users"/></a>
                </li>
            </ul>
        </div>
        <div class="card-body">
            <div class="tab-content" id="v-pills-tabContent">
                <div class="tab-pane fade show table-responsive active" id="v-pills-orders" role="tabpanel"
                     aria-labelledby="v-pills-orders-tab">
                    <auth:hasPermission user="${sessionScope['user']}"
                                        permissionType="${PermissionType.CHECK_ALL_ORDERS}">
                        <table class="table table-bordered table-hover table-sm">
                            <thead class="thead-dark">
                            <tr>
                                <th scope="col"><fmt:message key="field.id"/></th>
                                <th scope="col"><fmt:message key="field.author"/></th>
                                <th scope="col"><fmt:message key="field.description"/></th>
                                <th scope="col"><fmt:message key="field.price"/></th>
                                <th scope="col"><fmt:message key="field.master"/></th>
                                <th scope="col"><fmt:message key="field.manager"/></th>
                                <th scope="col"><fmt:message key="field.manager.description"/></th>
                                <th scope="col"><fmt:message key="field.accepted"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <jsp:useBean id="orders" scope="request"
                                         type="java.util.List<com.gelo.model.domain.Order>"/>
                            <c:forEach items="${orders}" var="order">
                                <tr>
                                    <td>${order.id}</td>
                                    <td>
                                        <a href="<c:url value="/app/profile?profile_id=${order.author.id}"/>">${order.author.name}</a>
                                    </td>
                                    <td>${order.authorDescription}</td>
                                    <td>${order.price}</td>
                                    <td>
                                        <a href="<c:url value="/app/profile?profile_id=${order.master.id}"/>">${order.master.name}</a>
                                    </td>
                                    <td>
                                        <a href="<c:url value="/app/profile?profile_id=${order.manager.id}"/>">${order.manager.name}</a>
                                    </td>
                                    <td>${order.managerDescription}</td>
                                    <td>${order.accepted}
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </auth:hasPermission>
                </div>
                <div class="tab-pane fade table-responsive" id="v-pills-users" role="tabpanel"
                     aria-labelledby="v-pills-users-tab">
                    <auth:hasPermission user="${sessionScope['user']}"
                                        permissionType="${PermissionType.CHECK_ALL_USERS}">
                        <table class="table table-bordered table-hover table-sm">
                            <tr>
                                <td><fmt:message key="field.id"/></td>
                                <td><fmt:message key="field.name"/></td>
                                <td><fmt:message key="field.email"/></td>
                                <td><fmt:message key="field.coutry"/></td>
                                <td><fmt:message key="field.active.order.count"/></td>
                                <td><fmt:message key="field.summary.order.count"/></td>
                                <td><fmt:message key="field.role"/></td>
                            </tr>

                            <jsp:useBean id="users" scope="request" type="java.util.List<com.gelo.model.domain.User>"/>
                            <c:forEach items="${users}" var="u">
                                <tr>
                                    <td>${u.id}</td>
                                    <td><a href="<c:url value="/app/profile?profile_id=${u.id}"/>">${u.name}</a></td>
                                    <td>${u.email}</td>
                                    <td>${u.country}</td>
                                    <td>${u.activeOrdersCount}</td>
                                    <td>${u.summaryOrdersCount}</td>
                                    <td><fmt:message key="${u.role}"/></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </auth:hasPermission>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
