<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="/authtags" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="com.gelo.model.domain.PermissionType" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${sessionScope['language']}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/home.css"/>
    <c:import url="WEB-INF/jspf/includes.jsp"/>

</head>
<body>

<c:import url="WEB-INF/jspf/header.jsp"/>
<div class="container-fluid">
    <auth:hasPermission user="${sessionScope['user']}" permissionType="${PermissionType.CREATE_ORDER}">
        <form action="${pageContext.request.contextPath}/app/order/create" method="post">
            <div class="form-group">
                <label for="authorDescription"><fmt:message key='field.description'/></label>:
                <textarea class="form-control" name="authorDescription" id="authorDescription" rows="3" cols="12"></textarea>
            </div>
            <input type="submit" value="<fmt:message key='create.order'/>">
        </form>
    </auth:hasPermission>
</div>
</body>
</html>
