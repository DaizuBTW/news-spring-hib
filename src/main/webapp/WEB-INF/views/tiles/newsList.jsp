<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="/WEB-INF/views/locale.jsp" %>


<div class="body-title">
	<c:out value="${menu_news_list}" />
	<form class="news-filter">
		<select name="category">
			<optgroup label="<c:out value="${category_label}" />">
				<option value="0" selected><c:out value="${category_all}" /></option>
				<option value="1"><c:out value="${sport}" /></option>
				<option value="2"><c:out value="${society}" /></option>
				<option value="3"><c:out value="${weather}" /></option>
			</optgroup>
		</select>
		<input type="hidden" name="command" value="go_to_news_list" />
		<input type="submit" value="<c:out value="${button_apply}" />" />
	</form>
</div>

<form action="doDeleteNews" method="post">
	<c:forEach var="news" items="${requestScope.newsList}">
		<div class="single-news-wrapper">
			<div class="single-news-header-wrapper">
				<div class="news-title">
					<h2><c:out value="${news.title}" /></h2>
				</div>
				<div class="news-date">
					<c:out value="${news.date}" />
				</div>

				<div class="news-content">
					<c:out value="${news.brief}" />
				</div>
				<div class="news-link-to-wrapper">
					<div class="link-position">
						<c:if test="${sessionScope.user.role.title eq 'admin'}">
							<a href="updateNews?id=${news.id}"><c:out value="${button_update}" /></a>
						</c:if>
						
						<a href="viewNews?id=${news.id}"><c:out value="${button_more}" /></a>
   					    
   					    <c:if test="${sessionScope.user.role.title eq 'admin'}">
   					         <input type="checkbox" name="id" value="${news.id }" />
   					    </c:if>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>


	<c:if test="${sessionScope.user.role.title eq 'admin'}">
		<div class="body-button-position">
				<input type="submit" value="<c:out value="${button_delete}" />" />
		</div>
	</c:if>
	<c:if test="${requestScope.newsList eq null}">
		<p>No news.</p>
	</c:if>
</form>
