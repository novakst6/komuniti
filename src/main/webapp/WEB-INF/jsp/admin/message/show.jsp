<%-- 
    Document   : inbox
    Created on : 16.7.2012, 19:06:14
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
        <div class="span6">

            <div class="page-header">
                <h2>Administrace zpráv</h2>
            </div>

            <div class="pull-right">
                <form:form commandName="fulltextFilterForm" action="show.htm" method="POST">
                    <form:input path="keywords" id="text_keyword" />
                    <button class="btn" type="submit" id="btn_fulltext">Hledej</button>
                </form:form>
            </div>
            
            <table class="table table-bordered">
                <tbody>
                    <c:forEach items="${messages}" var="msg">
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="span2"><div style="margin-bottom: 5px;"><span class="badge">${msg.id}</span></div><div><span class="badge badge-inverse">${msg.author.email}</span></div></div>
                                    <div class="span6"><a href="detail.htm?id=${msg.id}" id="link_message${msg.id}">${msg.subject}</a></div>
                                    <div class="span2">${msg.sendDate}</div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach> 
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@include file="../../../parts/footer.jspf" %>
