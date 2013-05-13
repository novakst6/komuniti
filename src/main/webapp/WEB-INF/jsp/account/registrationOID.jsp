<%-- 
    Document   : registrationOID
    Created on : 7.6.2012, 0:11:37
    Author     : novakst6
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../parts/header.jspf" %>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="span5"> 
            <div class="page-header">
                <h2>Registrační formulář</h2>
            </div>
            <form:form cssClass=".form-horizontal" action="registrationOID.htm" commandName="googleRegistrationForm" method="POST">
                <fieldset>
                    <div class="control-group">
                        <form:errors path="*" cssClass="alert alert-error" element="div"/>
                    </div>
                    <div class="control-group">
                        <form:label cssClass="control-label" path="email">Email: ${googleRegistrationForm.email}</form:label>
                        <form:hidden path="email"/>
                        <form:errors path="email" cssClass="alert alert-error" element="div"/>
                    </div>

                    <div class="control-group">
                        <form:label cssClass="control-label" path="firstName">Jméno: ${googleRegistrationForm.firstName}</form:label>
                        <form:hidden path="firstName"/>
                        <form:errors path="firstName" cssClass="alert alert-error" element="div"/>
                    </div>

                    <div class="control-group">
                        <form:label cssClass="control-label" path="lastName">Příjmení: ${googleRegistrationForm.lastName}</form:label>
                        <form:hidden path="lastName"/>
                        <form:errors path="lastName" cssClass="alert alert-error" element="div"/>
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
        </div>
        <div class="span7">

        </div>
    </div> 
</div>
</div>
<%@include file="../../parts/footer.jspf" %>
