<%-- 
    Document   : show
    Created on : 7.6.2012, 15:37:43
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
                <h2>Administrace uživatelů</h2>
            </div>
            
             
            <div class="pull-left" style="margin: 0em 0em 2em 0em;">               
               <a href="add.htm" class="btn btn-primary">Vytvořit nového uživatele</a>                
            </div>
            <div class="pull-right">
                <form:form commandName="userAdminFulltextFilterForm" action="show.htm" method="POST">
                    <form:input path="keywords" id="text_keywords" />
                    <button class="btn" type="submit" id="btn_fulltext_submit" >Hledej</button>
                </form:form>
            </div>    
            
            <table class="table table-striped">
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="span1">
                                        <span class="badge">${user.id}</span>
                                        <c:if test="${user.active == true}">
                                            <span class="badge badge-success">A</span>
                                        </c:if>
                                        <c:if test="${user.active == false}">
                                            <span class="badge badge-important">D</span>
                                        </c:if>
                                    </div>
                                    <div class="span5">${user.email} [${user.googleName}]</div>
                                    <div class="span2">
                                    <c:forEach items="${user.roles}" var="role" >
                                        <span class="badge badge-inverse">${role.name}</span>
                                    </c:forEach>
                                    </div>
                                    <div class="span1"><a class="btn btn-primary" href="active.htm?id=${user.id}">Aktivovat</a></div>
                                    <div class="span1"><a class="btn btn-danger" href="deactive.htm?id=${user.id}">Deaktivovat</a></div>
                                    <div class="span2">
                                        <div class="btn-group pull-left">
                                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                                        <i class="icon-cog"></i> 
                                        Další akce
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a href="rights.htm?id=${user.id}">Změnit role</a></li>
                                        <li><a href="edit.htm?id=${user.id}">Editovat</a></li>
                                        <li><a href="delete.htm?id=${user.id}">Smazat</a></li>
                                    </ul>
                                </div>
                                    </div>
                                </div>
                            </td>
                    </c:forEach>
                        </tbody>
            </table> 
   
                <%@include file="../../../parts/paginator.jspf" %>
        </div>
      </div>
</div>
        <%@include file="../../../parts/footer.jspf" %>


