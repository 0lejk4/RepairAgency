<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register page - RepairAgency</title>
    <c:import url="WEB-INF/jspf/includes.jsp"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/particles.min.js"></script>
    <script type="text/javascript">
        particlesJS.load('particles-js', '/static/js/particles.json', function () {
            console.log('callback - particles.js config loaded');
        });
    </script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/login.css"/>
</head>
<body>

<div id="login-overlay" class="modal-dialog modal-lg" style="z-index: 9999;">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel"><fmt:message key="register.become.client"/></h4>
        </div>
        <div class="modal-body">
            <div class="row">
                <div class="col">
                    <div class="card bg-light card-body mb-3">
                        <info:toast alerts="${requestScope['alerts']}"/>
                        <form action="<c:url value="/app/language"/>" method="post">
                            <div class="form-group">
                                <label for="language"><fmt:message key="field.language"/></label>
                                <select onchange="submit()" id="language" class="form-control" name="language">
                                    <option value="en" ${sessionScope.language == 'en' ? 'selected' : ''}>
                                        <span class="flag-icon flag-icon-us"></span> <fmt:message
                                            key="header.language.english"/>
                                    </option>
                                    <option value="uk" ${sessionScope.language == 'uk' ? 'selected' : ''}>
                                        <span class="flag-icon flag-icon-ua"></span> <fmt:message
                                            key="header.language.ukrainian"/>
                                    </option>
                                </select>
                            </div>
                        </form>
                        <form action="<c:url value="/app/register"/>" method="post">
                            <div class="form-group">
                                <label for="email" class="control-label"><fmt:message key="field.email"/></label>
                                <input id="email"
                                       name="email"
                                       class="form-control"
                                />
                            </div>
                            <div class="form-group">
                                <label for="password" class="control-label"><fmt:message key="field.password"/></label>
                                <input id="password"
                                       name="password"
                                       class="form-control"
                                       type="password"
                                />
                            </div>
                            <div class="form-group">
                                <label for="name" class="control-label"><fmt:message key="field.name"/></label>
                                <input id="name"
                                       name="name"
                                       class="form-control"
                                />
                            </div>
                            <div class="form-group">
                                <label for="country" class="control-label"><fmt:message key="field.country"/></label>
                                <input id="country"
                                       name="country"
                                       class="form-control"
                                />
                            </div>

                            <div class="form-group row">
                                <button type="submit" class="mx-auto btn btn-success">
                                    <h2><fmt:message key="field.register"/></h2></button>
                            </div>
                            <div class="form-group">
                                <h3 class="row">
                                    <span class="mx-auto"><fmt:message key="register.already"/> <a class="badge badge-info"
                                                                                 href="${pageContext.request.contextPath}/login.jsp"><fmt:message
                                            key="login.header"/></a></span>
                                </h3>
                            </div>
                            <div class="form-group">
                                <h3 class="row">
                                    <a class="mx-auto badge badge-light" href="<c:url value="/app/home"/>"><i
                                            class="fas fa-home"></i><fmt:message key="header.home"/></a>
                                </h3>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="particles-js"></div>
</body>
</html>