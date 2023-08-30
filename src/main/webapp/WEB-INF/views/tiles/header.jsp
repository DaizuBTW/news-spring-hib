<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="/WEB-INF/views/locale.jsp" %>

<div class="wrapper">
    <div class="newstitle"><h2>News<br />management</h2></div>


    <div class="local-link">
        <c:if test="${not (sessionScope.user eq 'active')}">

            <div align="right">
                <a href="#signin" class="overlayLink" ><c:out value="${popup_btn_enter}"/></a>
                <a href="#registration" class="overlayLink"><c:out value="${popup_btn_register}"/></a>
                <!--<c:if test="${not (requestScope.AuthenticationError eq null)}">
                    <font color="red">
                        <c:out value="${login_error}" />
                    </font>
                    <script>
                        window.alert("<c:out value="${login_error}" />");
                    </script>
                </c:if>-->
            </div>

        </c:if>

        <c:if test="${sessionScope.user eq 'active'}">

            <div align="right">
                <p class="name"><c:out value="${sessionScope.name}"/> <c:out value="${sessionScope.surname}"/></p>
                <p class="username"><c:out value="${sessionScope.username}"/> (<c:out value="${sessionScope.role}"/>)</p><br/>
                <form action="signOut" method="post">
                    <input type="submit" value="<c:out value="${header_singout}" />"/><br/>
                </form>
            </div>

        </c:if>
    </div>

</div>
