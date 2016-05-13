/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mack.controllers.impl;

import ejb.beans.Hash;
import ejb.beans.UsuarioBean;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import mack.controllers.AbstractController;
import shared.entities.Usuario;

/**
 *
 * @author denis
 */
public class LoginController extends AbstractController  {

    @Override
    public void execute() {
            Context cont = null;
        try {
            cont = new InitialContext();
            UsuarioBean user = (UsuarioBean) cont.lookup("java:global/AppEnterprise/ModuloEJB/UsuarioBean");
            
            Usuario u = new Usuario();
            u.setNome(this.getRequest().getParameter("login"));
            u.setSenha(Hash.generateStrongPasswordHash(this.getRequest().getParameter("senha")));
            u.setSobrenome(this.getRequest().getParameter("sobrenome"));
           
            
               
                    user.save(u);
               
            
            
        } catch (NamingException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
