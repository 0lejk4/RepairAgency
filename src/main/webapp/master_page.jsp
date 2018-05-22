<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="auth" uri="/authtags" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>
<jsp:useBean id="orders" scope="request" type="java.util.List<com.gelo.model.domain.Order>"/>

<html>
<head>
    <title>Master page - RepairAgency</title>
    <c:import url="WEB-INF/jspf/includes.jsp"/>
</head>
<body>

<c:import url="WEB-INF/jspf/header.jsp"/>
<div class="container-fluid">
    <h4 class="display-4 row"><span class="mx-auto"><fmt:message key="field.find.work"/></span></h4>
    <info:pagination all_count="${requestScope['all_count']}" order_field="${requestScope['order_field']}"
                     ascending="${requestScope['ascending']}" page_count="${requestScope['page_count']}"
                     page="${requestScope['page']}" count="${requestScope['count']}"
                     order_fields="${['id','price', 'author_id', 'master_id', 'done', 'manager_id', 'accepted']}"
                     entities="${orders}"
                     link="${'/app/order/master'}"/>
    <auth:hasPermission user="${sessionScope['user']}" permissionType="${PermissionType.ENROLL_ORDER}">
        <table class="table table-bordered table-hover table-sm">
            <thead class="thead-dark">
            <tr>
                <th scope="col"><fmt:message key="field.id"/></th>
                <th scope="col"><fmt:message key="field.author"/></th>
                <th scope="col"><fmt:message key="field.description"/></th>
                <th scope="col"><fmt:message key="field.manager"/></th>
                <th scope="col"><fmt:message key="field.manager.description"/></th>
                <th scope="col"><fmt:message key="field.price"/></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td>${order.id}</td>
                    <td>
                        <a href="<c:url value="/app/profile?profile_id=${order.author.id}"/>">${order.author.name}</a>
                    </td>
                    <td>${order.authorDescription}</td>
                    <td>
                        <a href="<c:url value="/app/profile?profile_id=${order.manager.id}"/>">${order.manager.name}</a>
                    </td>
                    <td>${order.managerDescription}</td>
                    <td>${order.price}${order.price == null?'':'₴'}</td>
                    <td>
                        <form method="post" action="<c:url value="/app/order/enroll"/>">
                            <input type="hidden" name="order_id" value="${order.id}">
                            <button class="btn btn-success" type="submit"><fmt:message
                                    key="field.enroll"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </auth:hasPermission>
</div>
</body>
</html>
