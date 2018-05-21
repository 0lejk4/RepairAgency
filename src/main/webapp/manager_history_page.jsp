<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="/authtags" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>
<jsp:useBean id="managementHistory" scope="request" type="java.util.List<com.gelo.model.domain.Order>"/>
<html>
<head>
    <title>Manager history - RepairAgency</title>
    <c:import url="WEB-INF/jspf/includes.jsp"/>
</head>
<body>

<c:import url="WEB-INF/jspf/header.jsp"/>
<div class="container-fluid">
    <h4 class="display-4 row"><span class="mx-auto"><fmt:message key="field.manage.history"/></span></h4>
    <info:pagination all_count="${requestScope['all_count']}" order_field="${requestScope['order_field']}"
                     ascending="${requestScope['ascending']}" page_count="${requestScope['page_count']}"
                     page="${requestScope['page']}" count="${requestScope['count']}"
                     order_fields="${['id','price', 'author_id', 'master_id', 'done', 'manager_id', 'accepted']}"
                     entities="${managementHistory}"
                     link="${'/app/order/manage/history'}"/>

    <auth:hasPermission user="${sessionScope['user']}" permissionType="${PermissionType.CHECK_MANAGE_HISTORY}">
        <table class="table table-bordered table-hover table-sm">
            <thead class="thead-dark">
            <tr>
              <th scope="col"><fmt:message key="field.id"/></th>
              <th scope="col"><fmt:message key="field.author"/></th>
              <th scope="col"><fmt:message key="field.description"/></th>
              <th scope="col"><fmt:message key="field.price"/></th>
              <th scope="col"><fmt:message key="field.master"/></th>
              <th scope="col"><fmt:message key="field.manager.description"/></th>
              <th scope="col"><fmt:message key="field.accepted"/></th>
            </tr>
            </thead>
            <c:forEach items="${managementHistory}" var="order">
                <tr>
                    <td>${order.id}</td>
                    <td>
                        <a href="<c:url value="/app/profile?profile_id=${order.author.id}"/>">${order.author.name}</a>
                    </td>
                    <td>${order.authorDescription}</td>
                    <td>${order.price}${order.price == null?'':'₴'}</td>
                    <td>
                        <a href="<c:url value="/app/profile?profile_id=${order.master.id}"/>">${order.master.name}</a>
                    </td>
                    <td>${order.managerDescription}</td>
                    <td>${order.accepted}
                </tr>
            </c:forEach>
        </table>
    </auth:hasPermission>
</div>
</body>
</html>
