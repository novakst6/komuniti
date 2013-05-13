<%-- 
    Document   : admin
    Created on : 13.6.2012, 1:01:43
    Author     : novakst6
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../parts/header.jspf" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">
            <%@include file="../../parts/adminmenu.jspf" %>
        </div>
        <div class="span10">
            <div class="page-header">
                <h2>Administrace</h2>
            </div>
            <div class="container-fluid">
            <p>Toto je administrační rozhraní stránek, kde je možné upravovat či vytvářet nové objekty (např. uživatelské účty) vložené uživateli. Vlevo si vyberte z nabídky, který objekt chcete upravovat.</p>
            </div>
            </div>
    </div>
</div>
        <%@include file="../../parts/footer.jspf" %>

