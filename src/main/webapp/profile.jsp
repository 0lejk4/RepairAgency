<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>
<jsp:useBean id="profile_user" scope="request" type="com.gelo.model.domain.User"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <c:import url="WEB-INF/jspf/includes.jsp"/>
    <link rel="stylesheet"
          href="<c:url value="/static/css/profile.css"/>"/>

</head>
<body>
<c:import url="WEB-INF/jspf/header.jsp"/>
<div class="container">
    <div class="card card-profile text-center">
        <img src="<c:url value="/static/images/profile-background.jpeg"/>" alt="Top" class="card-img-top">
        <div class="card-block">
            <img src="<c:url value="/static/images/avatar_default.png"/>"
                 alt="Your face" class="card-img-profile">
            <h4 class="card-title">
                <c:out value="${profile_user.name}"/>
                <br>
                <span class="badge badge-pill badge-info"> <fmt:message key="${profile_user.role}"/> </span>
                <br>
                <c:out value="${profile_user.country}"/>
                <br>
                <span class="badge badge-light"> <small> <c:out value="${profile_user.email}"/> </small></span>
            </h4>
        </div>
    </div>

    <jsp:useBean id="user" scope="session" type="com.gelo.model.domain.User"/>
    <jsp:useBean id="can_review" scope="request" type="java.lang.Boolean"/>
    <c:if test="${profile_user.id != user.id && can_review}">
        <form action="<c:url value="/app/review/add"/>" method="post" class="bg-light p-4 border-success">
            <input type="hidden" name="masterId" value="${profile_user.id}">
            <div class="form-group">
                <label for="title">Title</label>
                <input type="text" name="title" class="form-control" id="title" aria-describedby="titleHelp"
                       placeholder="Enter title">
                <small id="titleHelp" class="form-text text-muted">The biggest point in your opinion</small>
            </div>
            <div class="form-group">
                <label for="text">Text</label>
                <input type="text" name="text" class="form-control" id="text" placeholder="Enter text">
            </div>
            <div class="star-rating mx-auto">
                <span class="star far fa-star" data-rating="0"></span>
                <span class="star far fa-star" data-rating="1"></span>
                <span class="star far fa-star" data-rating="2"></span>
                <span class="star far fa-star" data-rating="3"></span>
                <span class="star far fa-star" data-rating="4"></span>
                <input type="hidden" name="rating" class="rating-value" value="3">
            </div>
            <button type="submit" class="btn btn-primary mx-auto">Submit</button>
        </form>
    </c:if>
    <jsp:useBean id="reviews" scope="request" type="java.util.List<com.gelo.model.domain.Review>"/>
    <div class="justify-content-center">
        <c:forEach items="${reviews}" var="review">
            <div class="card border-dark mx-auto my-4" style="max-width: 47rem;">
                <div class="card-header">
                    <a href="<c:url value="/app/profile?profile_id=${review.author.id}"/>">
                        <c:out value="${review.author.name}"/> <c:out value="${review.author.country}"/>
                    </a>

                    <small class="text-right text-muted star-rating mr-0">
                        <c:forEach begin="0" end="${review.rating.ordinal()}" step="1">
                            <span class="fas fa-star"></span>
                        </c:forEach>
                    </small>
                </div>
                <div class="card-body text-dark">
                    <h5 class="card-title"><c:out value="${review.title}"/></h5>
                    <p class="card-text"><c:out value="${review.text}"/></p>
                </div>
            </div>
        </c:forEach>

        <c:if test="${reviews.size() != 0}">
            <%--Todo: Make this an tag or fragment and reuse--%>
            <form action="<c:url value="/app/profile"/>" method="get">
                <input type="hidden" name="profile_id" value="${profile_user.id}">
                <input id="page" type="hidden" name="page">
                <select name="count"
                        onchange="document.getElementById('page').value = '${requestScope['page']}';submit()">
                    <c:forEach begin="1" end="${requestScope['all_count']}" var="pageNumber" varStatus="status">
                        <option value="${pageNumber}" ${requestScope['count'] == pageNumber ||( status.last && requestScope['count'] > requestScope['all_count'])? 'selected':''}>
                                ${pageNumber}
                        </option>
                    </c:forEach>
                </select>
                <select name="order_field"
                        onchange="document.getElementById('page').value = '${requestScope['page']}';submit()">
                    <option value="id" ${requestScope['order_field'] == 'id'? 'selected':''}>
                        Id
                    </option>
                    <option value="title" ${requestScope['order_field'] == 'title'? 'selected':''}>
                        Title
                    </option>
                    <option value="master_id" ${requestScope['order_field'] == 'master_id'? 'selected':''}>
                        MasterId
                    </option>
                    <option value="text" ${requestScope['order_field'] == 'text'? 'selected':''}>
                        Text
                    </option>
                    <option value="rating" ${requestScope['order_field'] == 'rating'? 'selected':''}>
                        Rating
                    </option>
                </select>
                <select name="ascending"
                        onchange="document.getElementById('page').value = '${requestScope['page']}';submit()">
                    <option value="true" ${requestScope['ascending']== true? 'selected':''}>
                        true
                    </option>
                    <option value="false" ${requestScope['ascending']== false? 'selected':''}>
                        false
                    </option>
                </select>
                <c:if test="${param['page'] > 1}">
                    <button type="submit"
                            onclick="document.getElementById('page').value = '${requestScope['page'] - 1}'"
                            class="btn btn-info">Prev
                    </button>
                </c:if>
                <c:forEach begin="1" end="${requestScope['page_count']}" var="pageNumber">
                    <button type="submit" ${requestScope['page'] == pageNumber?'class="button-success"':''}
                            onclick="document.getElementById('page').value = '${pageNumber}'">
                            ${pageNumber}
                    </button>
                </c:forEach>
                <c:if test="${requestScope['page'] < requestScope['page_count']}">
                    <button type="submit"
                            onclick="document.getElementById('page').value = '${requestScope['page'] + 1}'"
                            class="btn btn-info">Next
                    </button>
                </c:if>
            </form>
        </c:if>
    </div>
</div>
<script>
    var $star_rating = $('.star-rating .star');

    var SetRatingStar = function () {
        return $star_rating.each(function () {
            if (parseInt($star_rating.siblings('input.rating-value').val()) >= parseInt($(this).data('rating'))) {
                return $(this).removeClass('far').addClass('fas');
            } else {
                return $(this).removeClass('fas').addClass('far');
            }
        });
    };

    $star_rating.on('click', function () {
        $star_rating.siblings('input.rating-value').val($(this).data('rating'));
        return SetRatingStar();
    });

    SetRatingStar();
</script>
</body>
</html>
