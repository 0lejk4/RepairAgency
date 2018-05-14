<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="WEB-INF/jspf/includes.jsp"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page Not Found</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/static/css/500.css"/>
    <c:import url="WEB-INF/jspf/includes.jsp"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1><fmt:message key="field.oops"/></h1>
                <h2>${requestScope['statusCode']} <fmt:message key="field.internal.server.error"/></h2>
                <div class="alert alert-danger">
                    ${requestScope['error']}
                </div>
                <div class="error-actions">
                    <a href="${pageContext.request.contextPath}/app/home" class="btn btn-primary btn-lg"><span
                            class="fa fa-home"></span>
                        <fmt:message key="field.take.me.home"/> </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
