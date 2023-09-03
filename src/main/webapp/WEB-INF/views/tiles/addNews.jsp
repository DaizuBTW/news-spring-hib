<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/locale.jsp" %>


<div class="body-title">
    <a href="newsList"><c:out value="${button_back_to_news}"/> >> </a> <c:out value="${label_add}"/>
</div>

<div class="add-table-margin">
    <form:form action="doAddNews" modelAttribute="news" method="post">
        <div class="news-wrapper">
            <form:label path="title">
                <p class="label_popup"><c:out value="${news_title}"/></p>
                <form:textarea path="title" cols="40" rows="3" id="title"/>
            </form:label>
            <form:label path="date">
                <p class="label_popup"><c:out value="${news_date}"/></p>
                <form:input type="datetime-local" path="date" id="date"/>
            </form:label>
            <form:label path="brief">
                <p class="label_popup"><c:out value="${news_brief}"/></p>
                <form:textarea path="brief" cols="40" rows="5" id="brief"/>
            </form:label>
            <form:label path="content">
                <p class="label_popup"><c:out value="${news_content}"/></p>
                <form:textarea path="content" cols="40" rows="15" id="content"/>
            </form:label>
        </div>
        <div class="body-button-position">
            <input type="hidden" name="user_id" value="<c:out value="${sessionScope.user.id}"/>"/>
            <input type="submit" value="<c:out value="${button_add}" />"/>
        </div>
    </form:form>
</div>
