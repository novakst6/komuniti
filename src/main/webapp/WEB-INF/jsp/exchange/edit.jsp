<%-- 
    Document   : add
    Created on : 2.8.2012, 15:39:49
    Author     : novakst6
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="../../parts/header.jspf" %>

<style type="text/css" media="all">
    <!--
    @import url("<spring:url value="/texteditor/css/widgEditor.css" />");
    -->
</style>

<script src="<spring:url value="/texteditor/scripts/widgEditor.js" />"></script>
<script>
    var counter = 1;
    var limit = 3;
    function addInput(divName){
        if (counter == limit)  {
            alert("Dosáhli jste maximálního počtu " + counter + " souborů");
        }
        else {
            var newdiv = document.createElement('div');
            newdiv.innerHTML = "<div class=\"control-group\"><span>Popis souboru: </span><span><input id=\"descriptionOfFiles"+(counter + 1)+"\" name=\"descriptionOfFiles["+(counter + 1)+"]\" type=\"text\" value=\"\"/> <input id=\"files"+(counter + 1)+"\" name=\"files["+(counter + 1)+"]\" type=\"file\" value=\"\"/></span></div>";
            document.getElementById(divName).appendChild(newdiv);
            counter++;
        }
    }
</script>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">

            <div class="page-header">
                <h2>Úprava nabídky</h2>
            </div>

            <form:form action="edit.htm" method="POST" commandName="editFormModel" enctype="multipart/form-data">
                <div class="row-fluid">
                    <div class="span8">    

                        <form:errors path="*" cssClass="alert alert-error" element="div" />
                        <div class="control-group"> 
                            <form:label path="title" >Název:</form:label>
                            <form:input path="title" cssClass="input-xxlarge" id="text_title"/>
                            <form:errors path="title" cssClass="alert alert-error" element="div" />
                        </div>
                        <div class="control-group">

                            <form:label path="text" >Text:</form:label>
                            <form:textarea path="text" cssClass="widgEditor nothing" id="text_text"/>
                            <form:errors path="text" cssClass="alert alert-error" element="div" />
                        </div>
                        <div class="row-fluid">
                            <div class="span3">
                                <div class="well">
                                    <h4>Kategorie nabídky:</h4>
                                </div>
                                <table class=" table table-striped" style="width: 10em;">
                                    <c:forEach items="${tags}" var="t">
                                        <tr>
                                            <td style="width:2em;"><form:checkbox path="tagsId" value="${t.id}"/></td>
                                            <td style="width:8em;">${t.name}</td>
                                        </tr>    
                                    </c:forEach>

                                </table>
                            </div>
                            <div class="span9">
                                <div class="well">
                                    <h4>Soubory:</h4>
                                </div>
                                <b>Nahrané soubory:</b>
                                <table class="table table-striped"> 
                                    <c:forEach items="${editFormModel.filesOrig}" var="file">                          
                                        <tr>
                                            <td style="width: 3em;">
                                                <i class="icon-trash"></i> <form:checkbox path="filesToDelete" value="${file.id}"/>
                                            </td>
                                            <td>
                                                <a href="<spring:url value="/file/download.htm?id=${file.id}" />">${file.name}</a> 
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>

                                <h4>Přidat nové:</h4>
                                <div id="dynamicFiles">
                                    <div class="control-group">
                                        <span>
                                            Popis souboru:                                                      
                                        </span>
                                        <span>
                                            <form:input path="descriptionOfFiles[0]" />  
                                            <form:input path="files[0]" type="file" />                                      
                                        </span>
                                    </div> 
                                </div>
                                <div class="control-group">    
                                    <a class="btn btn-navbar" onclick="addInput('dynamicFiles')" >Přidat soubor</a> 
                                </div>
                            </div>
                        </div> </div>
                </div>
                <div class="control-group">              
                    <button class="btn btn-primary" id="btn_edit_offer_submit">Uložit</button>
                </div>  
                <form:hidden path="id" />
            </form:form>    
        </div>

    </div>
</div>

<%@include file="../../parts/footer.jspf" %>


