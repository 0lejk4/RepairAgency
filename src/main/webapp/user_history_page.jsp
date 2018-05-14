<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>
<%@ taglib prefix="auth" uri="/authtags" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.gelo.model.domain.PermissionType" %>
<html>
<head>
    <title>Your history</title>
    <c:import url="WEB-INF/jspf/includes.jsp"/>
</head>
<body>
<c:import url="WEB-INF/jspf/header.jsp"/>
<div class="container-fluid table-responsive">
    <h2 class="display-5 mx-auto"><fmt:message key="field.your.orders"/></h2>

    <table class="table table-bordered table-hover table-sm">
        <tr>
            <td><fmt:message key="field.id"/></td>
            <td><fmt:message key="field.description"/></td>
            <td><fmt:message key="field.manager"/></td>
            <td><fmt:message key="field.manager.description"/></td>
            <td><fmt:message key="field.price"/></td>
            <td><fmt:message key="field.master"/></td>
            <td><fmt:message key="field.status"/></td>
        </tr>

        <jsp:useBean id="orders" scope="request" type="java.util.List<com.gelo.model.domain.Order>"/>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.id}</td>
                <td>${order.authorDescription}</td>
                <td><a href="<c:url value="/app/profile?profile_id=${order.manager.id}"/>">${order.manager.name}</a>
                </td>
                <td>${order.managerDescription}</td>
                <td>${order.price}</td>
                <c:choose>
                    <c:when test="${order.master == null}">
                        <td><fmt:message key="field.searching.master"/></td>
                        <td><fmt:message key="field.in.progress"/></td>
                    </c:when>
                    <c:otherwise>
                        <td>
                            <a href="<c:url value="/app/profile?profile_id=${order.master.id}"/>">${order.master.name}</a>
                        </td>
                        <td><fmt:message key="field.done"/></td>
                    </c:otherwise>
                </c:choose>

            </tr>
        </c:forEach>
    </table>


</body>
</html>
