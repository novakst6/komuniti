/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller;

import cz.komuniti.model.entity.FileEntity;
import java.io.OutputStream;
import java.sql.Blob;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import org.apache.commons.io.IOUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author novakst6
 */
@Controller
@RequestMapping(value={"file"})
@Secured(value={"ROLE_USER"})
public class FileController extends TemplateController {
    
    @RequestMapping(value={"download.htm"},method= RequestMethod.GET)
    public String download(
                @RequestParam(value="id",required=true)
                Long id,
                HttpServletResponse response
            ) throws Exception 
    {
        try {
            if(isParameterNull(id))
            {
                getInfoMessages().setWarnMessage("Parametr id musí být vyplněný.");
                return "redirect:/index.htm";
            }
            
            FileEntity file = getFileManager().findById(id);
            
            if(isParameterNull(file))
            {
                getInfoMessages().setWarnMessage("Objekt se zadaným id se nepodařilo najít.");
                return "redirect:/index.htm";
            }
            
            response.setHeader("Content-Disposition", "inline;filename=\"" + file.getName() + "\"");
            OutputStream out = response.getOutputStream();
            response.setContentType(file.getContentType());
            Blob blob = new SerialBlob(file.getStream());
            IOUtils.copy(blob.getBinaryStream(), out);
            out.flush();
            out.close();
            
        } catch (Exception e) {
            getInfoMessages().setErrorMessage("ERROR "+e.getMessage());
            return "redirect:/index.htm";
        }
        return null;
    }
}
