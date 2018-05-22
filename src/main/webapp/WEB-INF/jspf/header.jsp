<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="user" scope="session" type="com.gelo.model.domain.User"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="auth" uri="/authtags" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>
<html lang="${sessionScope['language']}">
<body>
<div class="navbar navbar-light bg-light navbar-expand-md" role="navigation">
    <div class="container-fluid">
        <button type="button" class="navbar-toggler" data-toggle="collapse" data-target=".navbar-collapse">&#x2630;
        </button>
        <a href="<c:url value="/app/home"/>" class="navbar-brand"><fmt:message key="header.name"/><i
                class="fa fa-wrench"></i></a>
        <div
                class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active nav-item"><a href="<c:url value="/app/home"/>"
                                               class="nav-link"><fmt:message key="header.home"/></a>
                </li>
                <li class="active nav-item"><a href="<c:url value="/app/profile"/>"
                                               class="nav-link"><fmt:message key="header.profile"/></a>
                </li>
                <auth:hasRole user="${user}" roleType="${RoleType.ROLE_MANAGER}">
                    <li class="active nav-item"><a href="<c:url value="/app/order/manage"/>"
                                                   class="nav-link"><fmt:message key="header.manage"/></a>
                    </li>
                </auth:hasRole>
                <auth:hasRole user="${user}" roleType="${RoleType.ROLE_MANAGER}">
                    <li class="active nav-item"><a href="<c:url value="/app/order/manage/history"/>"
                                                   class="nav-link"><fmt:message key="header.manage.history"/></a>
                    </li>
                </auth:hasRole>
                <auth:hasRole user="${user}" roleType="${RoleType.ROLE_USER}">
                    <li class="active nav-item"><a href="<c:url value="/app/order/history"/>"
                                                   class="nav-link"><fmt:message key="header.history"/></a>
                    </li>
                </auth:hasRole>
                <auth:hasRole user="${user}" roleType="${RoleType.ROLE_MASTER}">
                    <li class="active nav-item"><a href="<c:url value="/app/order/master"/>"
                                                   class="nav-link"><fmt:message key="header.master"/></a>
                    </li>
                </auth:hasRole>
                <auth:hasRole user="${user}" roleType="${RoleType.ROLE_MASTER}">
                    <li class="active nav-item"><a href="<c:url value="/app/order/master/history"/>"
                                                   class="nav-link"><fmt:message key="header.master.history"/></a>
                    </li>
                </auth:hasRole>
                <auth:hasRole user="${user}" roleType="${RoleType.ROLE_ADMIN}">
                    <li class="active nav-item"><a href="<c:url value="/app/order/admin"/>"
                                                   class="nav-link"><fmt:message key="header.admin"/></a>
                    </li>
                </auth:hasRole>

            </ul>
            <ul class="nav navbar-nav ml-auto">
                <li class="active nav-item">
                    <form action="<c:url value="/app/language"/>" method="post">
                        <select id="language" class="form-control" data-width="fit" name="language" onchange="submit()">
                            <option value="en" ${language == 'en' ? 'selected' : ''}>
                                <fmt:message key="header.language.english"/>
                            </option>
                            <option value="uk" ${language == 'uk' ? 'selected' : ''}>
                                <fmt:message key="header.language.ukrainian"/>
                            </option>
                        </select>
                    </form>
                </li>
                <li class="dropdown nav-item"><a href="#" class="dropdown-toggle nav-link" data-toggle="dropdown">

                    <span class="fa fa-user"></span>&#xA0;

                    <strong><c:out value="${user.name}"/> </strong>

                </a>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li class="dropdown-item">
                            <div class="navbar-login">
                                <div class="row">
                                    <div class="col-xl-4">
                                        <p class="text-center"><span class="fa fa-user icon-size"></span>
                                        </p>
                                    </div>
                                    <div class="col-xl-8">
                                        <p class="text-left"><strong><c:out value="${user.name}"/></strong>
                                        </p>
                                        <p class="text-left small"><c:out value="${user.email}"/></p>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li class="divider dropdown-item"></li>
                        <li class="dropdown-item">
                            <div class="navbar-login navbar-login-session">
                                <div class="row">
                                    <div class="col-xl-12">
                                        <p>
                                        <form action="<c:url value="/app/logout"/>" method="post">
                                            <input type="submit" class="btn btn-danger btn-block"
                                                   value="<fmt:message key="header.logout"/>">
                                        </form>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>
<info:toast alerts="${requestScope['alerts']}"/>
</body>
</html>
