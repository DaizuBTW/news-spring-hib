<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="/WEB-INF/views/locale.jsp" %>

<c:out value="${guestpage_label}"/>

<div class="body-title">
    <a href="controller?command=go_to_news_list"><c:out value="${menu_news_list}"/> >> </a> <c:out value="${menu_latest_news}"/>
</div>

<form action="command.do?method=delete" method="post">
    <c:forEach var="news" items="${requestScope.newsList}">
        <div class="single-news-wrapper">
            <div class="single-news-header-wrapper">
                <div class="news-title">
                    <h2><c:out value="${news.title}"/></h2>
                </div>
                <div class="news-date">
                    <c:out value="${news.date}"/>
                </div>

                <div class="news-content">
                    <c:out value="${news.brief}"/>
                </div>
            </div>
        </div>

    </c:forEach>

    <div class="no-news">
        <c:if test="${requestScope.newsList eq null}">
            No news.
        </c:if>
    </div>

</form>
