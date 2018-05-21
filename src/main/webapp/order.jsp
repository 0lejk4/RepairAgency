<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="/authtags" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${sessionScope['language']}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create order - RepairAgency</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home.css"/>
    <c:import url="WEB-INF/jspf/includes.jsp"/>

</head>
<body style="background: #232741 50% 50%;">

<c:import url="WEB-INF/jspf/header.jsp"/>
<div class="container-fluid">
    <auth:hasPermission user="${sessionScope['user']}" permissionType="${PermissionType.CREATE_ORDER}">
        <div class="mx-auto my-5 text-center text-white">
            <h2><fmt:message key="order.policy"/></h2>
            <ul class="list-unstyled">
                <li><p class="lead"><fmt:message key="order.policy.polite"/></p></li>
                <li><p class="lead"><fmt:message key="order.policy.wise"/></p></li>
                <li><p class="lead"><fmt:message key="order.policy.spam"/></p></li>
                <li><p class="lead"><fmt:message key="order.policy.master"/></p></li>
                <li><p class="lead"><fmt:message key="order.policy.track"/></p></li>
            </ul>
        </div>
        <div class="card mx-auto my-5" style="width: 47rem;">

            <div class="card-header">
                <fmt:message key="admin.register.prompt"/>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/app/order/create" method="post">
                    <div class="form-group">
                        <label for="authorDescription"><fmt:message key='field.description'/></label>:
                        <textarea class="form-control" name="authorDescription" id="authorDescription" rows="3"
                                  cols="12"></textarea>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="mx-auto">
                                <input type="submit" class="btn btn-success mx-auto"
                                       value="<fmt:message key='create.order'/>">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </auth:hasPermission>
</div>
</body>
</html>
