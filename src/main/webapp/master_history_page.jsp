<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="auth" uri="/authtags" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>
<jsp:useBean id="workHistory" scope="request" type="java.util.List<com.gelo.model.domain.Order>"/>
<html>
<head>
    <title>Master history - RepairAgency</title>
    <c:import url="WEB-INF/jspf/includes.jsp"/>
</head>
<body>

<c:import url="WEB-INF/jspf/header.jsp"/>
<div class="container-fluid">
    <h4 class="display-4 row"><span class="mx-auto"><fmt:message key="field.work.history"/></span></h4>
    <info:pagination all_count="${requestScope['all_count']}" order_field="${requestScope['order_field']}"
                     ascending="${requestScope['ascending']}" page_count="${requestScope['page_count']}"
                     page="${requestScope['page']}" count="${requestScope['count']}"
                     order_fields="${['id','price', 'author_id', 'master_id', 'done', 'manager_id', 'accepted']}"
                     entities="${workHistory}"
                     link="${'/app/order/master/history'}"/>

    <auth:hasPermission user="${sessionScope['user']}" permissionType="${PermissionType.CHECK_WORK_HISTORY}">
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
            <c:forEach items="${workHistory}" var="order">
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
                    <c:if test="${order.done == null || order.done == false}">
                        <td>

                            <form method="post" action="<c:url value="/app/order/finish"/>">
                                <input type="hidden" name="order_id" value="${order.id}">
                                <button class="btn btn-success" type="submit"><fmt:message
                                        key="field.finish"/></button>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </auth:hasPermission>
</div>
</body>
</html>
