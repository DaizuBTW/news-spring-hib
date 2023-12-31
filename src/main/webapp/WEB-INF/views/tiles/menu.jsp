<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="/WEB-INF/views/locale.jsp" %>


<div class="menu-wrapper">
    <div class="menu-title-wrapper">
        <div class="menu-title">
            <c:out value="${label_news}"/>
        </div>
    </div>
    <div class="list-menu-invisible-wrapper">
        <hr>
        <div class="list-menu-wrapper">
            <ul style="text-align: left;">
                <li style="padding-left: 15px;">

                    <a href="newsList"><c:out value="${menu_news_list}"/></a><br/>
                </li>
                <c:if test="${sessionScope.user.role.title eq 'admin'}">
                    <li style="padding-left: 15px;">
                        <a href="addNews"><c:out value="${menu_add_news}"/></a><br/>
                    </li>
                    <li style="padding-left: 15px;">
                        <a href="userlist"><c:out value="${menu_user_list}"/></a><br/>
                    </li>
                </c:if>
            </ul>
        </div>
        <div class="clear"></div>

    </div>
</div>
<div class="menu-lang">
    <a href="localization?local=en"> <c:out value="${en_button}"/> </a>
    <a href="localization?local=ru"> <c:out value="${ru_button}"/> </a>
</div>
<c:if test="${not (sessionScope.error eq null)}">
    <div class="menu-wrapper-error">
        <p>
            <fmt:message bundle="${loc}" key="${sessionScope.error}" var="error_message"/>
            <c:out value="${error_message}"/>
        </p>
    </div>
</c:if>


