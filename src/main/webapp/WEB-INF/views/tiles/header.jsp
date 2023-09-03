<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="/WEB-INF/views/locale.jsp" %>

<div class="wrapper">
    <div class="newstitle"><h2>News<br/>management</h2></div>


    <div class="local-link">
        <c:if test="${not (sessionScope.user_status eq 'active')}">
            <div align="right">
                <a href="#signin" class="overlayLink"><c:out value="${popup_btn_enter}"/></a>
                <a href="#registration" class="overlayLink"><c:out value="${popup_btn_register}"/></a>
            </div>
        </c:if>

        <c:if test="${sessionScope.user_status eq 'active'}">
            <div align="right">
                <p class="name">${sessionScope.user.userDetails.name} <c:out
                        value="${sessionScope.user.userDetails.surname}"/></p>
                <p class="username"><c:out value="${sessionScope.user.username}"/> (<c:out
                        value="${sessionScope.user.role.title}"/>)</p><br/>
                <form action="signOut" method="post">
                    <input type="submit" value="<c:out value="${header_singout}" />"/><br/>
                </form>
            </div>

        </c:if>
    </div>

</div>
