<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ include file="/WEB-INF/views/locale.jsp" %>


<div class="body-title">
	<c:out value="${menu_user_list}" />
</div>

<form action="doDeleteNews" method="post">
	<c:forEach var="users" items="${requestScope.users}">
		<div class="single-news-wrapper">
			<div class="single-news-header-wrapper">
				<div class="news-content">
					<p class="name"><c:out value="${users.userDetails.name}"/> <c:out value="${users.userDetails.surname}"/></p>
					<p class="username"><c:out value="${users.username}"/> (<c:out value="${users.role.title}"/>)</p>
				</div>

			</div>
		</div>
	</c:forEach>


	<c:if test="${sessionScope.role eq 'admin'}">
		<div class="body-button-position">
				<input type="submit" value="<c:out value="${button_delete}" />" />
		</div>
	</c:if>
	<c:if test="${requestScope.news eq null}">
		<p>No news.</p>
	</c:if>
</form>
