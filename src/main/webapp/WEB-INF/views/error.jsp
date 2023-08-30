<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="/WEB-INF/views/locale.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link rel="stylesheet" type="text/css" href="sources/css/newsStyle.css">
</head>
    <body>
        <div class="error-page">
            <div class="error-wrapper">
                <h1><c:out value="${error_label}"/></h1>
                <h2><c:out value="${error_content}"/></h2>
                <br/>
                <br/>
                <div class="menu-wrapper-error">
                    <p>
                        <fmt:message bundle="${loc}" key="${error}" var="error_message"/>
                        <c:out value="${error_message}"/>
                    </p>
                </div>
                <form action="news_list" method="post">
                    <input type="submit" class="error-input" value="<c:out value="${button_back_to_news}"/>">
                </form>
            </div>
        </div>
    </body>
</html>