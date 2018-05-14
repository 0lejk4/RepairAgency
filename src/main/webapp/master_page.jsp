<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="auth" uri="/authtags" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
    <ul class="nav nav-pills nav-fill" id="v-pills-tab" role="tablist">
        <li class="nav-item"><a class="nav-link active" id="v-pills-worklist-tab" data-toggle="pill"
                                href="#v-pills-worklist" role="tab"
                                aria-controls="v-pills-worklist" aria-selected="true"><fmt:message
                key="field.find.work"/></a></li>
        <li class="nav-item"><a class="nav-link" id="v-pills-workhistory-tab" data-toggle="pill"
                                href="#v-pills-workhistory" role="tab"
                                aria-controls="v-pills-workhistory" aria-selected="false"><fmt:message
                key="field.work.history"/></a></li>
    </ul>
    <div class="tab-content" id="v-pills-tabContent">
        <div class="tab-pane fade show active table-responsive" id="v-pills-worklist" role="tabpanel"
             aria-labelledby="v-pills-worklist-tab">
            <auth:hasPermission user="${sessionScope['user']}" permissionType="${PermissionType.ENROLL_ORDER}">
                <table class="table table-bordered table-hover table-sm">
                    <tr>
                        <td><fmt:message key="field.id"/></td>
                        <td><fmt:message key="field.author"/></td>
                        <td><fmt:message key="field.description"/></td>
                        <td><fmt:message key="field.manager"/></td>
                        <td><fmt:message key="field.manager.description"/></td>
                        <td></td>
                    </tr>

                    <jsp:useBean id="orders" scope="request" type="java.util.List<com.gelo.model.domain.Order>"/>
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
        <div class="tab-pane fade table-responsive" id="v-pills-workhistory" role="tabpanel" aria-labelledby="v-pills-workhistory-tab">
            <auth:hasPermission user="${sessionScope['user']}" permissionType="${PermissionType.CHECK_WORK_HISTORY}">
                <table class="table table-bordered table-hover table-sm">
                    <tr>
                        <td><fmt:message key="field.id"/></td>
                        <td><fmt:message key="field.author"/></td>
                        <td><fmt:message key="field.description"/></td>
                        <td><fmt:message key="field.manager"/></td>
                        <td><fmt:message key="field.manager.description"/></td>
                        <td></td>
                    </tr>

                    <jsp:useBean id="workHistory" scope="request"
                                 type="java.util.List<com.gelo.model.domain.Order>"/>
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
                            <td>
                                <c:if test="${order.done == null || order.done == false}">
                                    <form method="post" action="<c:url value="/app/order/finish"/>">
                                        <input type="hidden" name="order_id" value="${order.id}">
                                        <button class="btn btn-success" type="submit"><fmt:message
                                                key="field.finish"/></button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </auth:hasPermission>
        </div>
    </div>
</div>
</body>
</html>
