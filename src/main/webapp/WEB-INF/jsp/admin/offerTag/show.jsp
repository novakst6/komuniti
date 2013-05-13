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
                <h2>Administrace kategorií nabídky</h2>
            </div>
            
             
            <div class="pull-left" style="margin: 0em 0em 2em 0em;">               
               <a href="add.htm" class="btn btn-primary">Vytvořit novou kategorii nabídky</a>                
            </div>
                
          
            <table class="table table-striped">
                    <c:forEach items="${tags}" var="tag">
                        <tr>
                            <td>
                                <div class="row-fluid">
                                    <div class="span1"><span class="badge">${tag.id}</span></div>
                                    <div class="span7">${tag.name}</div>
                                    <div class="span2"><a href="edit.htm?id=${tag.id}" class="btn btn-navbar">Upravit</a></div>
                                    <div class="span2"><a class="btn btn-danger" href="delete.htm?id=${tag.id}">Smazat</a></div>
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


