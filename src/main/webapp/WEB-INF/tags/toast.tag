<%@ tag body-content="empty" dynamic-attributes="dynattrs" %>
<%@ attribute name="alerts" type="java.util.List<com.gelo.validation.Alert>" required="true" rtexprvalue="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>
<c:if test="${alerts.size() > 0}">
    <c:forEach items="${alerts}" var="alert" varStatus="status">
        <div class="alert alert-${alert.type.toString()} alert-dismissible fade show alert-fixed" role="alert"
             style="bottom: ${(status.index)* 50}px;z-index: ${9999 - status.index};">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong><fmt:message key="${alert.message}"/>
            </strong>
        </div>
    </c:forEach>
</c:if>