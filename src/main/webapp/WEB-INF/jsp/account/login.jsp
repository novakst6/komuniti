<%-- 
    Document   : login
    Created on : 6.6.2012, 21:27:29
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../parts/header.jspf" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span5"> 
            <div class="page-header">
                <h2>Přihlášení</h2>
            </div>
            <form class=".form-horizontal" action="j_spring_openid_security_check" id=”googleOpenId” method="post" target="_top">
                <label for="btn_open_id_google">Přihlašte se pomocí svého Google účtu</label>
                <input id="openid_identifier" name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id" />
                <img src="<spring:url value="/themes/google_logo.jpg" />" alt="google_logo" />
                <button type="submit" class="btn" id="btn_open_id_google">Přihlásit</button>
            </form> 

            <c:if test="${param.login == 'true'}">
                <form class=".form-horizontal" action="<spring:url value="j_spring_security_check"/>" method="post">
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-error">
                            <a class="close" data-dismiss="alert" href="#">×</a>
                            <b>Ouha! </b>Chybné uživatelské jméno nebo heslo!

                        </div>
                    </c:if>   
                    <div class="control-group">
                        <label for="j_username">Uživatelské jméno:</label>
                        <input type="text" name="j_username" id="j_username" />                   
                    </div>
                    <div class="control-group">
                        <label for="j_password">Heslo:</label>                       
                        <input type="password" name="j_password" id="j_password" />
                    </div>
                    <div class="control-group">
                        <button type="submit" class="btn" id="btn_login">Odeslat</button>
                    </div>
                </form>
            </c:if>
        </div>
        <div class="span7">
            <div class="page-header">
                <h3>Nápověda</h3>
            </div>
            <p>
                Pro přístup je zapotřebí vytvořit si  účet  pro Google služby (využíváme balík služeb Google Apps).
            <ul>
                <li>Účet je možné přiřadit k běžnému emailu (např. jmeno@seznam.cz),</li>
                <li>to je pak uživatelské jméno, kterým se přihlašujete</li>
                <li>heslo si určíte sám/sama (může klidně použít stejné jako u jeho vlastního emailu, máte nad ním kontrolu jen vy).</li>
                <li>Stránka pro registraci Google služeb k vašemu emailu je <a href="https://accounts.google.com/newaccount?hl=cs">ZDE</a>,</li>
                <li><a href="http://www.youtube.com/watch?v=97qjIKWcg1Y">videonávod zde</a>, nápověda <a href="http://www.google.com/support/accounts/bin/answer.py?hl=cs&answer=27441">zde</a>).</li>
                <li>Návod pro zřízení Google účtu k vašemu email</li>

            </ul>
            <!--
            <iframe width="640" height="360" src="http://www.youtube.com/embed/97qjIKWcg1Y?feature=player_embedded" frameborder="0" allowfullscreen></iframe>
            -->
            </p>
        </div>
    </div> 
</div>
</div>
<%@include file="../../parts/footer.jspf" %>
