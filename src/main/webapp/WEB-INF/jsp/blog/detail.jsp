<%-- 
    Document   : show
    Created on : 7.6.2012, 15:37:43
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@include file="../../parts/header.jspf" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">

            <div class="page-header">
                <h2>Článek</h2>
            </div>

            <div class="well">
                <h3>${article.title}</h3>
            </div>
            <br />
            <div class="container-fluid">
                <div class="row"><b>Datum vložení: </b> ${article.dateOfInsert}</div>
                <div class="row"><span><b>Autor článku: </b> ${article.author.googleName} </span><span class="badge badge-info">${article.author.email}</span></div>
                <c:if test="${article.edited == true}">
                    <div class="row"><b>Editováno: </b>${article.dateOfEdit}</div> 
                    <div class="row"><b>Uživatelem: </b><span class="badge badge-info">${article.editor.email}</span></div>
                </c:if>
            </div>
            <br />
            <div class="container-fluid">
                <div class="well">
                    <h4>Text:</h4>
                    <p>${article.text}</p> 
                </div>
            </div>

            <div class="well">
                <h3>Komentáře</h3>
            </div>
            <div class="row-fluid">
                <div class="span8">
                    <div class="well">
                        <h4>Přidat komentář k článku</h4>
                        <%-- formulář pro vložení komentáře --%>
                        <form:form action="detail.htm" commandName="addFormModel" method="POST" >
                            <div class="control-group"> 
                                <form:label path="text" >Text komentáře:</form:label>
                                <form:textarea path="text" />
                                <form:errors path="text" cssClass="alert alert-error" element="div" />
                            </div>
                            <form:hidden path="id" />
                            <button type="submit"  class="btn btn-primary" id="btn_comment_submit">Přidat komentář</button>
                        </form:form>
                    </div>
                    <c:forEach items="${article.comments}" var="c">
                        <table class="table table-striped">
                            <tr>
                                <td>
                                    <div class="row-fluid">
                                        <div class="span5"><b>Datum vložení:</b> ${c.dateOfInsert}</div>
                                        <div class="span5"><span><b>Autor:</b> ${c.author.googleName} </span><span class="label label-info"> ${c.author.email}</span></div>
                                        <sec:authorize ifAnyGranted="ROLE_ADMIN_CONTENT,ROLE_ADMIN" >
                                        <div class="span2"><a href="block.htm?id=${c.id}&amp;idArticle=${article.id}">Blokovat</a></div>
                                        </sec:authorize>
                                    </div>
                                    <div class="row-fluid">
                                        <div class="span12">
                                            <c:choose>
                                                <c:when test="${c.banned == true}">
                                                    <i>Komentář byl smazán.</i>
                                                </c:when>
                                                <c:when test="${c.banned == false}">
                                                    <p>${c.text}</p>
                                                </c:when>
                                            </c:choose>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </c:forEach>
                </div>
            </div>
        </div>

    </div>
</div>
<%@include file="../../parts/footer.jspf" %>


