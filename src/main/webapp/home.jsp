<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="/authtags" %>
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
                <h6 class="display-4"><fmt:message key="home.slide1.text"/></h6>
            </div>
        </div>
        <div class="carousel-item">
            <img class="responsive" src="<c:url value="/static/images/slide2.jpg"/>" alt="Chicago">
            <div class="carousel-caption">
                <h3 class="display-3"><fmt:message key="home.slide2.header"/></h3>
                <h6 class="display-4"><fmt:message key="home.slide2.text"/></h6>
            </div>
        </div>
        <div class="carousel-item">
            <img class="responsive" src="<c:url value="/static/images/slide3.jpg"/>" alt="New York">
            <div class="carousel-caption">
                <h3 class="display-3"><fmt:message key="home.slide3.header"/></h3>
                <h6 class="display-4"><fmt:message key="home.slide3.text"/></h6>
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
        <auth:hasPermission user="${sessionScope.get('user')}" permissionType="${PermissionType.CREATE_ORDER}">
            <div class="row">
                <h1 class="display-4 mb-3 mx-auto">Create Order</h1>
            </div>
            <div class="row">
                <a class="mx-auto" href="<c:url value="/order.jsp"/>"> <img
                        src="<c:url value="/static/images/create_order_image.png"/>"
                        alt="Create order"> </a>
            </div>
        </auth:hasPermission>

        <div class="row">
            <div class="col-lg-4">
                <img class="rounded-circle"
                     src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
                     alt="Generic placeholder image" width="140" height="140">
                <h2>Heading</h2>
                <p>Donec sed odio dui. Etiam porta sem malesuada magna mollis euismod. Nullam id dolor id nibh ultricies
                    vehicula ut id elit. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo
                    cursus magna.</p>
                <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
            </div><!-- /.col-lg-4 -->
            <div class="col-lg-4">
                <img class="rounded-circle"
                     src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
                     alt="Generic placeholder image" width="140" height="140">
                <h2>Heading</h2>
                <p>Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Cras
                    mattis consectetur purus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo, tortor mauris
                    condimentum nibh.</p>
                <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
            </div><!-- /.col-lg-4 -->
            <div class="col-lg-4">
                <img class="rounded-circle"
                     src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
                     alt="Generic placeholder image" width="140" height="140">
                <h2>Heading</h2>
                <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula
                    porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
                    ut fermentum massa justo sit amet risus.</p>
                <p><a class="btn btn-secondary" href="#" role="button">View details »</a></p>
            </div><!-- /.col-lg-4 -->
        </div><!-- /.row -->


        <!-- START THE FEATURETTES -->

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading">First featurette heading. <span
                        class="text-muted">It'll blow your mind.</span></h2>
                <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis
                    euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus,
                    tellus ac cursus commodo.</p>
            </div>
            <div class="col-md-5">
                <img class="featurette-image img-fluid mx-auto" data-src="holder.js/500x500/auto" alt="500x500"
                     style="width: 500px; height: 500px;"
                     src="data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%22500%22%20height%3D%22500%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%20500%20500%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_162d8eb4608%20text%20%7B%20fill%3A%23AAAAAA%3Bfont-weight%3Abold%3Bfont-family%3AArial%2C%20Helvetica%2C%20Open%20Sans%2C%20sans-serif%2C%20monospace%3Bfont-size%3A25pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_162d8eb4608%22%3E%3Crect%20width%3D%22500%22%20height%3D%22500%22%20fill%3D%22%23EEEEEE%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%22184.8984375%22%20y%3D%22261.1%22%3E500x500%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E"
                     data-holder-rendered="true">
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7 order-md-2">
                <h2 class="featurette-heading">Oh yeah, it's that good. <span
                        class="text-muted">See for yourself.</span></h2>
                <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis
                    euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus,
                    tellus ac cursus commodo.</p>
            </div>
            <div class="col-md-5 order-md-1">
                <img class="featurette-image img-fluid mx-auto" data-src="holder.js/500x500/auto" alt="500x500"
                     style="width: 500px; height: 500px;"
                     src="data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%22500%22%20height%3D%22500%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%20500%20500%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_162d8eb4609%20text%20%7B%20fill%3A%23AAAAAA%3Bfont-weight%3Abold%3Bfont-family%3AArial%2C%20Helvetica%2C%20Open%20Sans%2C%20sans-serif%2C%20monospace%3Bfont-size%3A25pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_162d8eb4609%22%3E%3Crect%20width%3D%22500%22%20height%3D%22500%22%20fill%3D%22%23EEEEEE%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%22184.8984375%22%20y%3D%22261.1%22%3E500x500%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E"
                     data-holder-rendered="true">
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading">And lastly, this one. <span class="text-muted">Checkmate.</span></h2>
                <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis
                    euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus,
                    tellus ac cursus commodo.</p>
            </div>
            <div class="col-md-5">
                <img class="featurette-image img-fluid mx-auto" data-src="holder.js/500x500/auto" alt="500x500"
                     style="width: 500px; height: 500px;"
                     src="data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%22500%22%20height%3D%22500%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%20500%20500%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_162d8eb460a%20text%20%7B%20fill%3A%23AAAAAA%3Bfont-weight%3Abold%3Bfont-family%3AArial%2C%20Helvetica%2C%20Open%20Sans%2C%20sans-serif%2C%20monospace%3Bfont-size%3A25pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_162d8eb460a%22%3E%3Crect%20width%3D%22500%22%20height%3D%22500%22%20fill%3D%22%23EEEEEE%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%22184.8984375%22%20y%3D%22261.1%22%3E500x500%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E"
                     data-holder-rendered="true">
            </div>
        </div>

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
                                    <span class="badge badge-pill badge-success">${master.summaryOrdersCount} <fmt:message key="orders.done"/></span>
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