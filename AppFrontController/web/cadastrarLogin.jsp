<%-- 
    Document   : cadastrarLogin
    Created on : 28/02/2016, 18:53:36
    Author     : linolica1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <script>
        function validarSenha(){ 
         senha = document.formulario.senha.value;
         senha2 = document.formulario.senha2.value;
         if (senha != senha2){
          alert("Repita a senha corretamente");
                    document.formulario.senha2.focus();
                    return false;
                }
            }
        </script>        
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/projeto.css">
    </head>
    <body>
        <form name="formulario" action="FrontControllerServlet?control=Login" method="POST" >
            <p>
                <label for="lg">Nome</label>
                <input type="text" name="nome" id="lg"/>
            </p>
            <p>
                <label for="lg">Sobrenome</label>
                <input type="text" name="sobrenome" id="lg"/>
            </p>
            <p>
                <label for="senha">Senha</label>
                <input type="password" name="senha" id="senha"/>
            </p>

            <p>
                <label for="senha2">Repita a Senha</label>
                <input type="password" name="senha2" id="senha2"/>
            </p>

            <input type="submit" value="Enviar" onclick="return validarSenha();" />
        </form>
    </body>
</html>

