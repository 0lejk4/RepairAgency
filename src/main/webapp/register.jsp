<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register Page</title>
    <c:import url="WEB-INF/jspf/includes.jsp"/>
</head>
<body>
<%--Todo: localizate--%>
<div class="container">
    <info:toast alerts="${requestScope['alerts']}"/>
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <h1>Registration</h1>
            <form action="<c:url value="/app/language"/>">
                <select id="language" class="selectpicker" data-width="fit" name="language" onchange="submit()">
                    <option data-content='<span class="flag-icon flag-icon-us"></span> English' value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                    <option data-content='<span class="flag-icon flag-icon-uk"></span> Ukrainian' value="uk" ${language == 'uk' ? 'selected' : ''}>Ukrainian</option>
                </select>
            </form>
            <form action="${pageContext.request.contextPath}/app/register" method="post">
                <div class="form-group">
                    <label for="email" class="control-label">Email</label>
                    <input id="email"
                           name="email"
                           class="form-control"
                    />
                </div>
                <div class="form-group">
                    <label for="password" class="control-label">Password</label>
                    <input id="password"
                           name="password"
                           class="form-control"
                           type="password"
                    />
                </div>
                <div class="form-group">
                    <label for="name" class="control-label">Name</label>
                    <input id="name"
                           name="name"
                           class="form-control"
                    />
                </div>
                <div class="form-group">
                    <label for="country" class="control-label">Country</label>
                    <input id="country"
                           name="country"
                           class="form-control"
                    />
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-success">Register</button>
                    <span>Already registered? <a href="${pageContext.request.contextPath}/login.jsp">login</a></span>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>