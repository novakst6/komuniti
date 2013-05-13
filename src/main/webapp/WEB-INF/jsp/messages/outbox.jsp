<%-- 
    Document   : outbox
    Created on : 16.7.2012, 19:06:41
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="../../parts/header.jspf" %>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">
            <%@include file="../../parts/mailmenu.jspf" %>
        </div>
        <div class="span10">

            <div class="page-header">
                <h2>Odeslané zprávy</h2>
            </div>
                
            <table class="table table-striped">
                    <c:forEach items="${outbox}" var="msg">              
                        <tr>
                             <td>
                                 <div class="row-fluid">
                                     <c:if test="${msg.readed == true}">
                                     <div class="span2"><div style="margin-bottom: 5px;"><span class="badge badge-success">Přečteno</span></div><div><span class="badge badge-info">${msg.author.email}</span></div></div>
                                     <div class="span6"><a href="detail.htm?id=${msg.id}" id="link_message${msg.id}">${msg.subject}</a></div>
                                     <div class="span2">${msg.sendDate}</div>
                                     </c:if>
                                     <c:if test="${msg.readed == false}">
                                     <div class="span2"><div style="margin-bottom: 5px;"><span class="badge badge-important">Nepřečteno</span></div><div><span class="badge badge-info">${msg.author.email}</span></div></div>
                                     <div class="span6"><a href="detail.htm?id=${msg.id}" id="link_message${msg.id}"><b>${msg.subject}</b></a></div>
                                     <div class="span2"><b>${msg.sendDate}</b></div>
                                     </c:if>
                                     <div class="span2"><div class="control-group"><a class="btn btn-danger" href="delete.htm?id=${msg.id}" id="link_delete${msg.id}">Smazat</a></div></div>
                                 </div>
                             </td>
                         </tr>
                    </c:forEach> 
                </tbody>
            </table>
            
            <%@include file="../../parts/paginator.jspf" %>
        </div>
      </div>
</div>
        <%@include file="../../parts/footer.jspf" %>
