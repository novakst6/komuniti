<%-- 
    Document   : show
    Created on : 2.8.2012, 15:37:43
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../../../parts/header.jspf" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">
            <%@include file="../../../parts/adminmenu.jspf" %>
        </div>
        <div class="span10">

            <div class="page-header">
                <h2>Administrace souborů</h2>
            </div>
            
            <div class="pull-right">
                <form:form commandName="fileTextFilterForm" action="show.htm" method="POST">
                    <form:input path="keywords" id="text_keywords" />
                    <button class="btn" type="submit" id="btn_fulltext_submit" >Hledej</button>
                </form:form>
            </div>

            <div class="pull-left" style="margin: 0em 0em 2em 0em;">               
                <a href="add.htm" class="btn btn-primary">Přidat nový soubor</a>                
            </div>

            <table class="table table-striped">
                <c:forEach items="${files}" var="f">
                    <tr>
                        <td>
                            <div class="row-fluid">
                                <div class="span1"><span class="badge">${f.id}</span></div>
                                <div class="span7">
                                    <div><a href="<spring:url value="/file/download.htm?id=${f.id}"/>" >${f.name}</a></div>
                                    <div>${f.description}</div>
                                </div>
                                <div class="span2">
                                    <div>${f.contentType}</div>
                                    <div>${f.fileSize} bytů</div>
                                </div>     
                                <div class="span2"><a class="btn btn-danger" href="delete.htm?id=${f.id}">Smazat</a></div>
                            </div>
                        </td>
                    </tr>                                             
                </c:forEach>
            </table>

            <%@include file="../../../parts/paginator.jspf" %>
        </div>
    </div>
</div>
<%@include file="../../../parts/footer.jspf" %>


