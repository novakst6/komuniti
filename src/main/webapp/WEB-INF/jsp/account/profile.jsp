<%-- 
    Document   : profile
    Created on : 7.7.2012, 17:29:08
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../parts/header.jspf" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="container">
    <div class="page-header">
        <h2>Profil uživatele ${user.googleName}</h2>
    </div>
    <h3>Informace:</h3>
    <ul>
        <li><b>Člen centra:</b> ${user.centrum.name}</li>
        <li><b>Region:</b> ${user.region.name}</li>
    </ul>
    <h3>Vaše aktuální nabídky:</h3>
    <c:forEach items="${userOffers}" var="o">                       
        <div class="row show-grid">
            <div class="span5"><a>${o.title}</a></div>
            <div class="span1"><a>Ukončit</a></div>
        </div>                  
    </c:forEach>
</div>


<%@include file="../../parts/footer.jspf" %>
