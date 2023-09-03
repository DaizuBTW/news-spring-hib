<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/locale.jsp" %>


<div class="body-title">
    <a href="main"><c:out value="${button_back_to_news}"/> >> </a> <c:out value="${label_update}"/>
</div>

<div class="add-table-margin">
    <form:form action="doUpdateNews" modelAttribute="news" method="post">
        <div class="news-wrapper">
            <form:label path="title">
                <p class="label_popup"><c:out value="${news_title}"/></p>
                <form:textarea path="title" cols="40" rows="3" id="title"/>
            </form:label>
            <form:label path="date">
                <p class="label_popup"><c:out value="${news_date}"/></p>
                <form:input path="date" type="datetime-local" name="date" id="date"/>
            </form:label>
            <form:label path="brief">
                <p class="label_popup"><c:out value="${news_brief}"/></p>
                <form:textarea path="brief" cols="40" rows="5" id="brief"/>
            </form:label>
            <form:label path="content">
                <p class="label_popup"><c:out value="${news_content}"/></p>
                <form:textarea path="content" cols="40" rows="15" id="content"/>
            </form:label>
            <!--
            <label for="category">
                <p class="label_popup"><c:out value="${category_label}" /></p>
                <select name="category" id="category">
                    <option value="1"><c:out value="${sport}" /></option>
                    <option value="2"><c:out value="${society}" /></option>
                    <option value="3"><c:out value="${weather}" /></option>
                </select>
            </label>
            -->
        </div>
        <c:if test="${not (requestScope.newsError eq null)}">
            <div class="menu-wrapper-error">
                <p>
                    <fmt:message bundle="${loc}" key="${requestScope.newsError}" var="error_message"/>
                    <c:out value="${error_message}"/>
                </p>
            </div>
        </c:if>
        <div class="body-button-position">
            <form:input path="id" type="hidden" name="id" value="${sessionScope.news.id}"/>
            <input type="hidden" name="user_id" value="<c:out value="${sessionScope.user.id}"/>"/>
            <input type="submit" value="<c:out value="${button_update}" />"/>
        </div>
    </form:form>
</div>
