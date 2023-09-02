<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/views/locale.jsp" %>
<div class="overlay" id="signin">
    <div class="login-wrapper">
        <div class="login-content">

            <a href="#" class="close">x</a>
            <h3><c:out value="${popup_label_signin}"/></h3>
            <form class="pop-up" action="signIn" method="post">
                <input type="hidden" name="command" value="do_sign_in"/>
                <label for="username">
                    <p class="label_popup" onclick="onClose(this)"><c:out value="${popup_login}"/></p>
                    <input type="text" name="username" value="admin" id="username"
                           placeholder="<c:out value="${header_login}" /> "/>
                </label>
                <label for="password">
                    <p class="label_popup"><c:out value="${popup_password}"/></p>
                    <input type="password" name="password" value="password" id="password"
                           placeholder="<c:out value="${header_password}" />"/>
                </label>
                <input type="submit" name="log-in" value="<c:out value="${popup_btn_enter}" />">
                <c:if test="${not (requestScope.AuthenticationError eq null)}">
                    <font color="red">
                        <c:out value="${login_error}" />
                    </font>
                </c:if>
            </form>
        </div>
    </div>
</div>


<div class="overlay" id="registration">
    <div class="login-wrapper">
        <div class="login-content">
            <a href="#" class="close">x</a>
            <h3><c:out value="${popup_label_register}"/></h3>
            <form:form class="pop-up" action="registration" modelAttribute="user" method="post">
                <form:label  path="username">
                    <p class="label_popup"><c:out value="${popup_login}"/></p>
                    <form:input type="text" path="username"/>
                </form:label>
                <form:label for="password" path="password">
                    <p class="label_popup"><c:out value="${popup_password}"/></p>
                    <form:input type="password" name="password" path="password"/>
                </form:label>
                <form:label for="name" path="userDetails.name">
                    <p class="label_popup"><c:out value="${popup_name}"/></p>
                    <form:input type="text" name="name" id="name" path="userDetails.name"/>
                </form:label>
                <form:label for="surname" path="userDetails.surname">
                    <p class="label_popup"><c:out value="${popup_surname}"/></p>
                    <form:input type="text" name="surname" id="surname" path="userDetails.surname"/>
                </form:label>
                <c:if test="${not (requestScope.AuthenticationError eq null)}">
                    <font color="red">
                        <c:out value="${login_error}" />
                    </font>
                </c:if>
                <input type="submit" name="log-in" value="${popup_btn_register}"/>
            </form:form>
        </div>
    </div>
</div>