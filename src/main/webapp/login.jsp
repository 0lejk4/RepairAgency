<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login Page</title>
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
<div id="login-overlay" class="modal-dialog" style="z-index: 9999;">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel">Login to Repair Agency</h4>
        </div>
        <div class="modal-body">
            <div class="row">
                <div class="col-6">
                    <div class="card bg-light card-body mb-3">
                        <info:toast alerts="${requestScope['alerts']}"/>
                        <form action="${pageContext.request.contextPath}/app/login" method="post">
                            <div class="form-group">
                                <label for="language">Language</label>
                                <select id="language" class="form-control" name="language">
                                    <option value="en" ${language == 'en' ? 'selected' : ''}>
                                        <span class="flag-icon flag-icon-us"></span> English
                                    </option>
                                    <option value="uk" ${language == 'uk' ? 'selected' : ''}>
                                        <span class="flag-icon flag-icon-ua"></span> Ukrainian
                                    </option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>:
                                <input type="text"
                                       id="email"
                                       name="email"
                                       class="form-control"
                                       autofocus="autofocus"
                                       placeholder="Username"/>
                            </div>
                            <div class="form-group">
                                <label for="password">Password</label>:
                                <input type="password"
                                       id="password"
                                       name="password"
                                       class="form-control"
                                       placeholder="Password"/>
                            </div>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-3">
                                        <input type="submit"
                                               name="login-submit"
                                               id="login-submit"
                                               class="form-control btn btn-info"
                                               value="Log In"/>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-6">
                    <p class="lead">Register now for <span class="text-success">FREE</span> to create your first order
                    </p>
                    <ul class="list-unstyled" style="line-height: 2">
                        <li><span class="fa fa-check text-success"></span> Faster order callback</li>
                        <li><span class="fa fa-check text-success"></span> Great working hours</li>
                        <li><span class="fa fa-check text-success"></span> Best workers in city</li>
                        <li><span class="fa fa-check text-success"></span> Good discounts for constantly customers</li>
                        <li><span class="fa fa-check text-success"></span> We love what we do</li>
                    </ul>
                    <p><a href="${pageContext.request.contextPath}/register.jsp">Yes please, register now!</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="particles-js"></div>
</body>
</html>