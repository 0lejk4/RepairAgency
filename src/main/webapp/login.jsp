<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login Page - RepairAgency</title>
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
<%--Todo: localizate--%>
<div id="login-overlay" class="modal-dialog modal-lg" style="z-index: 9999;">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel"><fmt:message key="login.header"/></h4>
        </div>
        <div class="modal-body">
            <div class="row">
                <div class="col-6">
                    <div class="card bg-light card-body mb-3">
                        <info:toast alerts="${requestScope['alerts']}"/>
                        <form action="<c:url value="/app/language"/>" method="post">
                            <div class="form-group">
                                <label for="language"><fmt:message key="field.language"/> </label>
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
                        <form action="${pageContext.request.contextPath}/app/login" method="post">
                            <div class="form-group">
                                <label for="email"><fmt:message key="field.email"/></label>:
                                <input type="text"
                                       id="email"
                                       name="email"
                                       class="form-control"
                                       autofocus="autofocus"
                                       placeholder="Username"/>
                            </div>
                            <div class="form-group">
                                <label for="password"><fmt:message key="field.password"/></label>:
                                <input type="password"
                                       id="password"
                                       name="password"
                                       class="form-control"
                                       placeholder="Password"/>
                            </div>
                            <div class="form-group">
                                <div class="row">
                                    <div class="mx-auto">
                                        <input type="submit"
                                               name="login-submit"
                                               id="login-submit"
                                               class="form-control btn btn-info"
                                               value="<fmt:message key="login.submit"/>"/>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-6">
                    <p class="lead"><fmt:message key="login.register.now"/> <span class="text-success"><fmt:message
                            key="phrase.free"/> </span> <fmt:message key="login.register.to"/>
                    </p>
                    <ul class="list-unstyled" style="line-height: 2">
                        <li><span class="fa fa-check text-success"></span> <fmt:message key="login.advantage.callback"/>
                        </li>
                        <li><span class="fa fa-check text-success"></span> <fmt:message key="login.advantage.hours"/>
                        </li>
                        <li><span class="fa fa-check text-success"></span> <fmt:message key="login.advantage.workers"/>
                        </li>
                        <li><span class="fa fa-check text-success"></span> <fmt:message key="login.advantage.opinion"/>
                        </li>
                        <li><span class="fa fa-check text-success"></span> <fmt:message key="login.advantage.love"/>
                        </li>
                    </ul>

                    <h3 class="row">
                        <a class="mx-auto badge badge-info" href="<c:url value="/register.jsp"/>"><fmt:message
                                key="register.please"/> </a>
                    </h3>


                    <h3 class="row">
                        <a class="mx-auto badge badge-light" href="<c:url value="/app/home"/>"><i
                                class="fas fa-home"></i><fmt:message key="header.home"/></a>
                    </h3>

                </div>
            </div>
        </div>
    </div>
</div>
<div id="particles-js"></div>
</body>
</html>