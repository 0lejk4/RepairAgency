<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="/authtags" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>
<%@ page import="com.gelo.model.domain.PermissionType" %>
<jsp:useBean id="orders" scope="request" type="java.util.List<com.gelo.model.domain.Order>"/>
<html>
<head>
    <title>Manager page - RepairAgency</title>
    <c:import url="WEB-INF/jspf/includes.jsp"/>

</head>
<body>

<c:import url="WEB-INF/jspf/header.jsp"/>
<div class="container-fluid">
    <h4 class="display-4 row"><span class="mx-auto"> <fmt:message key="field.manage.orders"/></span></h4>
    <info:pagination all_count="${requestScope['all_count']}" order_field="${requestScope['order_field']}"
                     ascending="${requestScope['ascending']}" page_count="${requestScope['page_count']}"
                     page="${requestScope['page']}" count="${requestScope['count']}"
                     order_fields="${['id','price', 'author_id', 'master_id', 'done', 'manager_id', 'accepted']}"
                     entities="${orders}"
                     link="${'/app/order/manage'}"/>

    <auth:hasPermission user="${sessionScope['user']}" permissionType="${PermissionType.ANSWER_ORDER}">
        <table class="table table-bordered table-hover table-sm">
            <thead class="thead-dark">
            <tr>
                <th scope="col"><fmt:message key="field.id"/></th>
                <th scope="col"><fmt:message key="field.author"/></th>
                <th scope="col"><fmt:message key="field.description"/></th>
                <th scope="col"></th>
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
                                <label for="manager_price"><fmt:message key="field.price"/> (₴)</label>:
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
</body>
</html>
