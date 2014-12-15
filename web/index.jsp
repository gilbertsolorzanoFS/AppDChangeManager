<%-- 
    Document   : index
    Created on : Oct 25, 2014, 4:51:07 PM
    Author     : gilbert.solorzano
    Main index.jsp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="js/jquery-1.11.1.min.js"></script>
        <script src="js/bootstrap.js"></script>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <!-- [if lt  IE 9]>
            <script src="js/html5shiv.js"></script>
            <script src="js/respond.js"></script>
        <![endif]-->
        <script>
            if(typeof loginError === 'undefined'){
                $('#errorLabel').hide();
            }else{
                $('#errorLabel').show();
            }
        </script>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-xs-2">
                   
                </div>
                <div class="col-xs-6 col1">
                    <div class="page-header">
                        <h1>AppDynamics Change Mgr</h1>
                    </div>
                </div>
                <div class="col-xs-2">
                    
                </div>
            </div>
            <div class="row">
                <div class="col-xs-2">
                   
                </div>
                <div class="col-xs-6 col1">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Please Login
                            <p id="errorLabel" hidden="true">The username or password provided was incorrect, please try again.</p>
                        </div>
                        <div class="panel-body">
                            <form action="LoginServlet" method="post">
                                <div class="form-group">
                                    <label for="username" class="col-xs-2">Username:</label>
                                    <div class="col-xs-10">
                                        <input type="text" name="username">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="password" class="col-xs-2">Password:</label>
                                    <div class="col-xs-10">
                                        <input type="password" name="password">
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary">Login</button>
                                
                            </form>
                        </div>
                    </div>
                 
                            
                </div>
                <div class="col-xs-2">
                    
                </div>
            </div>
        </div>
    </body>
</html>
