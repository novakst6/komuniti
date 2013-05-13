<%-- 
    Document   : registration
    Created on : 7.6.2012, 0:11:37
    Author     : novakst6
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="../../parts/header.jspf" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span5"> 
            <div class="page-header">
                <h2>Registrační formulář</h2>
            </div>
            <form class=".form-horizontal" action="j_spring_openid_security_check" id=”googleOpenId” method="post" target="_top">
                <label for="btn_open_id_google">Registrujte se pomocí svého účtu u Googlu</label>
                <input id="openid_identifier" name="openid_identifier" type="hidden" value="https://www.google.com/accounts/o8/id" />
                <img src="<spring:url value="/themes/google_logo.jpg" />" alt="google_logo" />
                <button type="submit" class="btn btn-primary" id="btn_open_id_google">Registrovat se</button>
            </form> 
            <c:if test="${param.registration == 'true'}">

                <form:form cssClass=".form-horizontal" action="registration.htm" commandName="userRegistrationForm" method="POST">
                    <fieldset>
                        <div class="control-group">
                            <form:errors path="*" cssClass="alert alert-error" element="div"/>
                        </div>
                        <div class="control-group">
                            <form:label path="email">Email:</form:label>
                            <form:input path="email" id="in_email"/>
                            <form:errors path="email" cssClass="alert alert-error" element="div"/>
                        </div>
                        <div class="control-group">
                            <form:label path="emailConfirm">Potvrzení emailové adresy:</form:label>
                            <form:input path="emailConfirm" id="in_email_confirm"/>
                            <form:errors path="emailConfirm" cssClass="alert alert-error" element="div"/>
                        </div>
                        <div class="control-group">
                            <form:label path="password">Heslo:</form:label>
                            <form:password path="password" id="in_password"/>
                            <form:errors path="password" cssClass="alert alert-error" element="div"/>
                        </div>
                        <div class="control-group">
                            <form:label path="passwordConfirm">Potvrzení hesla:</form:label>
                            <form:password path="passwordConfirm" id="in_password_confirm"/>
                            <form:errors path="passwordConfirm" cssClass="alert alert-error" element="div"/>
                        </div>
                        <div class="control-group">
                            <form:label path="regionId">Region:</form:label>
                            <form:select path="regionId">
                                <c:forEach items="${regions}" var="r">
                                    <c:forEach items="${r}" var="n">
                                        <form:option value="${n.region.id}" id="opt_region_${n.region.id}">
                                            <c:forEach begin="1" end="${n.index}">&nbsp;&nbsp;</c:forEach>
                                            ${n.region.name}
                                        </form:option>
                                    </c:forEach>
                                </c:forEach>
                            </form:select> 
                            <form:errors path="regionId" cssClass="alert alert-error" element="div"/>
                        </div>
                        <div class="control-group">
                            <form:label path="centerId">Centrum:</form:label>
                            <form:select path="centerId">
                                <c:forEach items="${centerMap}" var="c">
                                    <form:option value="${c.key}" id="opt_center_${c.key}">
                                        ${c.value}
                                    </form:option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="centerId" cssClass="alert alert-error" element="div"/>
                        </div>
                        <button type="submit" class="btn">Odeslat</button> 
                    </fieldset>
                </form:form>

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
            <iframe width="640" height="360" src="http://www.youtube.com/embed/97qjIKWcg1Y?feature=player_embedded" frameborder="0" allowfullscreen></iframe>

            </p>
        </div>
    </div> 
</div>
</div>
<%@include file="../../parts/footer.jspf" %>
