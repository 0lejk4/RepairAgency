<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>
<%@ taglib prefix="auth" uri="/authtags" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="orders" scope="request" type="java.util.List<com.gelo.model.domain.Order>"/>
<html>
<head>
    <title>User history - RepairAgency</title>
    <c:import url="WEB-INF/jspf/includes.jsp"/>
</head>
<body>
<c:import url="WEB-INF/jspf/header.jsp"/>
<div class="container-fluid table-responsive">
    <h4 class="display-4 row"><span class="mx-auto"><fmt:message key="field.your.orders"/></span></h4>
    <info:pagination all_count="${requestScope['all_count']}" order_field="${requestScope['order_field']}"
                     ascending="${requestScope['ascending']}" page_count="${requestScope['page_count']}"
                     page="${requestScope['page']}" count="${requestScope['count']}"
                     order_fields="${['id','price', 'author_id', 'master_id', 'done', 'manager_id', 'accepted']}"
                     entities="${orders}"
                     link="${'/app/order/history'}"/>


    <table class="table table-bordered table-hover table-sm">
        <thead class="thead-dark">
        <tr>
           <th scope="col"><fmt:message key="field.id"/></th>
           <th scope="col"><fmt:message key="field.description"/></th>
           <th scope="col"><fmt:message key="field.manager"/></th>
           <th scope="col"><fmt:message key="field.manager.description"/></th>
           <th scope="col"><fmt:message key="field.price"/></th>
           <th scope="col"><fmt:message key="field.master"/></th>
           <th scope="col"><fmt:message key="field.status"/></th>
        </tr>
        </thead>

        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.id}</td>
                <td>${order.authorDescription}</td>
                <td><a href="<c:url value="/app/profile?profile_id=${order.manager.id}"/>">${order.manager.name}</a>
                </td>
                <td>${order.managerDescription}</td>
                <td>${order.price}${order.price == null?'':'₴'}</td>
                <c:choose>
                    <c:when test="${order.master == null}">
                        <td><fmt:message key="field.searching.master"/></td>
                        <td><span class="badge badge-info"><fmt:message key="field.in.progress"/></span></td>
                    </c:when>
                    <c:otherwise>
                        <td>
                            <a href="<c:url value="/app/profile?profile_id=${order.master.id}"/>">${order.master.name}</a>
                        </td>
                        <td><span class="badge badge-success"><fmt:message key="field.done"/></span></td>
                    </c:otherwise>
                </c:choose>

            </tr>
        </c:forEach>
    </table>


</body>
</html>
