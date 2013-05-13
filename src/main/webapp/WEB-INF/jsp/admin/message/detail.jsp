<%-- 
    Document   : detail
    Created on : 16.7.2012, 19:07:33
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
                <h2>Zpráva</h2>
            </div> 
            <table class="table table-bordered">
                <tr>
                    <td>
                        <div class="row-fluid">
                            <div class="span12"><span><b>Zpráva od:</b>  </span><span class="badge badge-info">${message.author.email}</span></div>
                        </div>
                        <div class="row-fluid">
                            <div class="span12"><span><b>Datum:</b>  </span><span></span>${message.sendDate}</div>
                        </div>
                        <div class="row-fluid">
                            <div class="span12"><span><b>Předmět:</b>  </span><span>${message.subject}</span></div>
                        </div>
                        <div class="row-fluid">
                            <div class="span12"><p>${message.text}</p></div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<%@include file="../../../parts/footer.jspf" %>
