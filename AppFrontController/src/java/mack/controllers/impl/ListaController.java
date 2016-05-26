
package mack.controllers.impl;

import mack.controllers.AbstractController;
import ejb.beans.UsuarioBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import ejb.entities.Usuario;


public class ListaController extends AbstractController {

    @Override
    public void execute() {
        
     try {
         Context cont = new InitialContext();
          UsuarioBean user = (UsuarioBean) cont.lookup("java:global/AppEnterprise/ModuloEJB/UsuarioBean");
            this.setReturnPage("/index.jsp");
            this.getRequest().setAttribute("usuarios", user.list());
     } catch (NamingException ex) {
         this.setReturnPage("/error.jsp");
     }
       
        
       
//            this.setReturnPage("/index.jsp");
//            this.getRequest().setAttribute("usuarios", userBean.list());

       

    }

}

















