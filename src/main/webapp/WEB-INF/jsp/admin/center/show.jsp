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
                <h2>Administrace center</h2>
            </div>
            
             
            <div class="pull-left" style="margin: 0em 0em 2em 0em;">               
               <a href="add.htm" class="btn btn-primary">Vytvořit nové centrum</a>                
            </div>
                

            <table class="table table-striped">
                <tbody>
                    <c:forEach items="${centers}" var="c">
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="span1"><span class="badge">${c.id}</span></div>
                                    <div class="span5"><h4><a href="detail.htm?id=${c.id}">${c.name}</a></h4></div>
                                    <div class="span2"><span class="badge badge-inverse">${c.region.name}</span></div>
                                    <div class="span2"><a class="btn btn-primary" href="admin.htm?id=${c.id}">Správce</a></div>
                                    <div class="span2">
                                        <div class="btn-group pull-left">
                                    <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                                        <i class="icon-cog"></i> 
                                        Další akce
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a href="edit.htm?id=${c.id}">Upravit</a></li>
                                        <li><a href="delete.htm?id=${c.id}">Smazat</a></li>
                                    </ul>
                                </div>
                                    </div>
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


