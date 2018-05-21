<%@ tag body-content="empty" dynamic-attributes="dynattrs" %>
<%@ attribute name="entities" type="java.util.List" required="true" rtexprvalue="true" %>
<%@ attribute name="link" type="java.lang.String" required="true" rtexprvalue="true" %>
<%@ attribute name="hidden_fields" type="java.lang.String" required="false" rtexprvalue="true" %>
<%@ attribute name="order_fields" type="java.util.List<java.lang.String>" required="true" rtexprvalue="true" %>
<%@ attribute name="order_field" type="java.lang.String" required="true" rtexprvalue="true" %>
<%@ attribute name="count" type="java.lang.Long" required="true" rtexprvalue="true" %>
<%@ attribute name="all_count" type="java.lang.Long" required="true" rtexprvalue="true" %>
<%@ attribute name="page_count" type="java.lang.Long" required="true" rtexprvalue="true" %>
<%@ attribute name="page" type="java.lang.Long" required="true" rtexprvalue="true" %>
<%@ attribute name="ascending" type="java.lang.Boolean" required="true" rtexprvalue="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope['language']}"/>
<fmt:setBundle basename="repair_agency_localization"/>

<c:if test="${!(entities.size() == 0 && page == 1)}">
    <form action="<c:url value="${link}"/>" method="get" class="row justify-content-between">
            ${hidden_fields}
        <input id="page" type="hidden" name="page">
        <div class="bg-info mx-5 p-2 border rounded">
            <label for="count"><span class="text-white lead"> <fmt:message key="field.count"/> </span> </label>
            <select id="count" class="btn" name="count"
                    onchange="document.getElementById('page').value = '${page}';submit()">
                <c:forEach begin="1" end="${all_count}" var="pageNumber" varStatus="status">
                    <option value="${pageNumber}" ${count == pageNumber ||( status.last && count > all_count)? 'selected':''}>
                            ${pageNumber}
                    </option>
                </c:forEach>
            </select>
            <label for="order_field"><span class="text-white lead"> <fmt:message key="field.sort"/> </span></label>
            <select id="order_field" class="btn" name="order_field"
                    onchange="document.getElementById('page').value = '${page}';submit()">
                <c:forEach items="${order_fields}" var="field">
                    <option value="${field}" ${order_field== field? 'selected':''}>
                            ${field}
                    </option>
                </c:forEach>
            </select>
            <label for="ascending"><span class="text-white lead"> <i class="fas fa-sort-amount-up"> </i></span></label>
            <select id="ascending" class="btn" name="ascending"
                    onchange="document.getElementById('page').value = '${page}';submit()">
                <option value="true" ${ascending == true? 'selected':''}>
                    <fmt:message key="field.no"/>
                </option>
                <option value="false" ${ascending == false? 'selected':''}>
                    <fmt:message key="field.yes"/>
                </option>
            </select>
        </div>
        <div class="bg-info mx-5 p-2 border rounded">
            <c:if test="${param['page'] > 1}">
                <button type="submit"
                        onclick="document.getElementById('page').value = '${page - 1}'"
                        class="btn btn-light">Previous
                </button>
            </c:if>
            <c:forEach begin="1" end="${page_count}" var="pageNumber">
                <button type="submit" ${page == pageNumber?'class="btn-primary btn"':'class="btn btn-light"'}
                        onclick="document.getElementById('page').value = '${pageNumber}'">
                        ${pageNumber}
                </button>
            </c:forEach>
            <c:if test="${page < page_count}">
                <button type="submit"
                        onclick="document.getElementById('page').value = '${page + 1}'"
                        class="btn btn-light">Next
                </button>
            </c:if>
        </div>
    </form>
</c:if>