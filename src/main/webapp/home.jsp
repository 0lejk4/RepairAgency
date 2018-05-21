<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="/authtags" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${sessionScope['language']}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home - RepairAgency</title>
    <link rel="stylesheet" href="<c:url value="/static/css/home.css"/>"/>
    <c:import url="WEB-INF/jspf/includes.jsp"/>

</head>
<body>
<c:if test="${sessionScope.get('user') != null}">
    <c:import url="WEB-INF/jspf/header.jsp"/>
</c:if>


<div id="demo" class="carousel slide carousel-margin" data-ride="carousel">

    <!-- Indicators -->
    <ul class="carousel-indicators">
        <li data-target="#demo" data-slide-to="0" class="active"></li>
        <li data-target="#demo" data-slide-to="1"></li>
        <li data-target="#demo" data-slide-to="2"></li>
    </ul>

    <!-- The slideshow -->
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img class="responsive" src="<c:url value="/static/images/slide1.jpg"/>"
                 alt="Hard working employee">
            <div class="carousel-caption">
                <h3 class="display-3"><fmt:message key="home.slide1.header"/></h3>
                <p class="display-4"><fmt:message key="home.slide1.text"/></p>
            </div>
        </div>
        <div class="carousel-item">
            <img class="responsive" src="<c:url value="/static/images/slide2.jpg"/>" alt="Chicago">
            <div class="carousel-caption">
                <h3 class="display-3"><fmt:message key="home.slide2.header"/></h3>
                <p class="display-4"><fmt:message key="home.slide2.text"/></p>
            </div>
        </div>
        <div class="carousel-item">
            <img class="responsive" src="<c:url value="/static/images/slide3.jpg"/>" alt="New York">
            <div class="carousel-caption">
                <h3 class="display-3"><fmt:message key="home.slide3.header"/></h3>
                <p class="display-4"><fmt:message key="home.slide3.text"/></p>
            </div>
        </div>
    </div>

    <!-- Left and right controls -->
    <a class="carousel-control-prev" href="#demo" data-slide="prev">
        <span class="carousel-control-prev-icon"></span>
    </a>
    <a class="carousel-control-next" href="#demo" data-slide="next">
        <span class="carousel-control-next-icon"></span>
    </a>

</div>


<main role="main">
    <div class="container marketing">

        <c:if test="${sessionScope.get('user') == null}">
            <div class="row my-4">
                <div class="col">
                    <div class="row">
                        <blockquote class="col-6 blockquote mx-auto text-center">
                            <p class="mb-0">
                                <fmt:message key="home.register.prompt"/>
                            </p>
                            <footer class="blockquote-footer"><fmt:message key="repair.agency"/> <cite
                                    title="Source Title">"<fmt:message key="repair.agency"/>"</cite></footer>
                        </blockquote>
                    </div>
                    <div class="row">
                        <a class="btn btn-success mx-auto" href="<c:url value="/register.jsp"/>"><fmt:message
                                key="register.please"/></a></div>
                </div>
            </div>
            <div class="row my-4">
                <span class="mx-auto"><fmt:message key="register.already"/> <a class="btn btn-info" href="<c:url value="/login.jsp"/>"><fmt:message key="login.header"/></a></span>
            </div>

        </c:if>

        <auth:hasPermission user="${sessionScope.get('user')}" permissionType="${PermissionType.CREATE_ORDER}">
            <div class="row">
                <h1 class="display-4 mb-3 mx-auto"><fmt:message key="create.order"/></h1>
            </div>
            <div class="row">
                <a class="mx-auto mb-5" href="<c:url value="/order.jsp"/>"> <img
                        src="<c:url value="/static/images/create_order_image.png"/>"
                        alt="Create order"> </a>
            </div>
        </auth:hasPermission>

        <div class="row">
            <div class="col-lg-4">
                <i class="fas fa-certificate fa-7x"></i>
                <h2><fmt:message key="home.advantage.first.header"/> </h2>
                <p><fmt:message key="home.advantage.first.text"/></p>
            </div><!-- /.col-lg-4 -->
            <div class="col-lg-4">
                <i class="fas fa-trophy fa-7x"></i>
                <h2><fmt:message key="home.advantage.second.header"/></h2>
                <p><fmt:message key="home.advantage.second.text"/></p>
            </div><!-- /.col-lg-4 -->
            <div class="col-lg-4">
                <i class="fas fa-wrench fa-7x"></i>
                <h2><fmt:message key="home.advantage.third.header"/></h2>
                <p><fmt:message key="home.advantage.third.text"/></p>
            </div><!-- /.col-lg-4 -->
        </div><!-- /.row -->

        <hr class="featurette-divider">

        <div class="row">
            <h1 class="display-4 mb-3 mx-auto"><fmt:message key="master.leaderboard"/></h1>
        </div>
        <div class="mx-auto list-group justify-content-center col-sm-12 col-md-10 col-lg-8">
            <jsp:useBean id="best_masters" scope="request" type="java.util.List<com.gelo.model.domain.User>"/>
            <c:forEach items="${best_masters}" var="master" varStatus="status">
                <div class="row list-group-item list-group-item-action flex-column align-items-start mb-2">
                    <div class="d-flex w-100 justify-content-center display-4">
                        <h2 class="mb-1">
                            <a href="<c:url value="/app/profile?profile_id=${master.id}"/>">
                                <span class="badge badge-pill badge-info">${master.name}</span>
                            </a>
                        </h2>
                    </div>
                    <div class="mb-1 d-flex justify-content-between mr-0">
                        <div class="col">
                            <img src="<c:url value="/static/images/avatar_default.png"/>"
                                 alt="Your face"
                                 style=" border-radius: 50%;border: 5px solid #e6e5e1;">
                        </div>
                        <div class="col">
                            <div class="row justify-content-end">
                                <i class="fa fa-trophy fa-10x mx-auto"
                                   style="color:${status.index == 0?'gold':status.index == 1?'silver':'#cd7f32'};"></i>
                            </div>
                            <div class="row justify-content-end display-4">
                                <small class="mx-auto">
                                    <span class="badge badge-pill badge-success">${master.summaryOrdersCount} <fmt:message
                                            key="orders.done"/></span>
                                </small>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

    </div>


</main>
</body>
</html>