<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="/authtags" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.gelo.model.domain.PermissionType" %>
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
        <li class="nav-item"><a class="nav-link active" id="v-pills-manage-orders-tab" data-toggle="pill"
                                href="#v-pills-manage-orders" role="tab"
                                aria-controls="v-pills-manage-orders" aria-selected="true"><fmt:message
                key="field.manage.orders"/></a></li>
        <li class="nav-item"><a class="nav-link" id="v-pills-workhistory-tab" data-toggle="pill"
                                href="#v-pills-workhistory" role="tab"
                                aria-controls="v-pills-workhistory" aria-selected="false"><fmt:message
                key="field.manage.history"/></a></li>
    </ul>
    <div class="tab-content" id="v-pills-tabContent">
        <div class="tab-pane fade show active table-responsive" id="v-pills-manage-orders" role="tabpanel"
             aria-labelledby="v-pills-manage-orders-tab">
            <auth:hasPermission user="${sessionScope['user']}" permissionType="${PermissionType.ANSWER_ORDER}">
                <table class="table table-bordered table-hover table-sm">
                    <tr>
                        <td><fmt:message key="field.id"/></td>
                        <td><fmt:message key="field.author"/></td>
                        <td><fmt:message key="field.description"/></td>
                        <td></td>
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
                                <button onclick="document.getElementById('order_id_input').value ='${order.id}'"
                                        type="button"
                                        class="btn btn-primary" data-toggle="modal" data-target="#manager_answer">
                                    <fmt:message key="field.answer"/>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <!-- Modal -->
                <div class="modal fade" id="manager_answer" tabindex="-1" role="dialog"
                     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLongTitle"><fmt:message
                                        key="field.answer.prompt"/></h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form method="post"
                                      action="<c:url value="/app/order/answer"/>">
                                    <input id="order_id_input" type="hidden" name="order_id">
                                    <input type="hidden" name="answer" value="${false}">
                                    <div class="form-group">
                                        <label for="managerDescription"><fmt:message key="field.description"/></label>:
                                        <textarea class="form-control" name="managerDescription" id="managerDescription"
                                                  rows="3" cols="12"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="manager_price"><fmt:message key="field.price"/></label>:
                                        <input type="text" class="form-control" name="price" id="manager_price"/>
                                    </div>
                                    <button name="accepted" class="btn btn-success" type="submit">
                                        <i class="fa fa-close"><fmt:message key="field.accept"/></i>
                                    </button>
                                    <button name="declined" class="btn btn-danger" type="submit">
                                        <i class="fa fa-close"><fmt:message key="field.decline"/></i>
                                    </button>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message
                                        key="field.close"/></button>
                            </div>
                        </div>
                    </div>
                </div>
            </auth:hasPermission>
        </div>
        <div class="tab-pane fade table-responsive" id="v-pills-workhistory" role="tabpanel" aria-labelledby="v-pills-workhistory-tab">
            <auth:hasPermission user="${sessionScope['user']}" permissionType="${PermissionType.CHECK_MANAGE_HISTORY}">
                <table class="table table-bordered table-hover table-sm">
                    <tr>
                        <td><fmt:message key="field.id"/></td>
                        <td><fmt:message key="field.author"/></td>
                        <td><fmt:message key="field.description"/></td>
                        <td><fmt:message key="field.price"/></td>
                        <td><fmt:message key="field.master"/></td>
                        <td><fmt:message key="field.manager.description"/></td>
                        <td><fmt:message key="field.accepted"/></td>
                    </tr>

                    <jsp:useBean id="managementHistory" scope="request"
                                 type="java.util.List<com.gelo.model.domain.Order>"/>
                    <c:forEach items="${managementHistory}" var="order">
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
                            <td>${order.managerDescription}</td>
                            <td>${order.accepted}
                        </tr>
                    </c:forEach>
                </table>
            </auth:hasPermission>
        </div>
    </div>


</div>
</body>
</html>
