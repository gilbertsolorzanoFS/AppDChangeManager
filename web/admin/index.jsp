<%--
    Document   : index
    Created on : Nov 14, 2014, 7:00:22 AM
    Author     : gilbert.solorzano
    page: admin/index.jsp

${pageContext.request.contextPath}
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/appd_.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery-ui.min.css">

    <script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/appd_config.js"></script>
    <title>User Page</title>
  </head>
  <body>
    <nav class="navbar navbar-default" role="navigation">
      <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">AppDChangeManager</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <li><a href="../user/index.jsp">User</a></li>
            <li class="active"><a href="../admin/index.jsp">Admin<span class="sr-only">(current)</span></a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <form action="/AppDChangeManager/LogoutServlet" method="post" class="logout">
              <input type="submit" class="btn btn-warning" value="Logout">
              </form>
            </ul>
          </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
      </nav>
    <div class="container">
      <h1>Configurations</h1>
      <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active">
          <a href="#c_intro" aria-controls="c_intro" role="tab" data-toggle="tab">Configurations</a>
        </li>
        <li role="presentation">
          <a href="#change_section" aria-controls="change_section" role="tab" data-toggle="tab">Changes</a>
        </li>
      </ul>
      <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="c_intro">
          <div>
            <h4>Configurations</h4>
            <div class="scroll_table">
              <table class="table table-bordered" id="config_table">
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Value</th>
                    <th>Update</th>
                    <th>Delete</th>
                  </tr>
                </thead>
                <tbody id="b_config_table"></tbody>
              </table>
            </div>
            <button id="create_conf" type="submit" class="btn btn-primary">Add Configuration</button>
            <div id="dialog-form-conf_a" title="Add Configuration">
              <form>
                <fieldset>
                  <label for="displayName_conf_form">Configuration Name</label>
                  <input type="text" id="displayName_conf_form" value="Name" class="text ui-widget-content ui-corner-all">
                  <label for="value_conf_form">Configuration Value</label>
                  <input type="text" id="value_conf_form" value="Value" class="text ui-widget-content ui-corner-all">
                  <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
                </fieldset>
              </form>
            </div>
          </div>
          <div>
            <h4>Users</h4>
            <div class="scroll_table">
              <table class="table table-bordered" id="auth_table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Display Name</th>
                    <th>User Name</th>
                    <th>Account Type</th>
                    <th>Group Name</th>
                    <th>Change Password</th>
                    <th>Delete</th>
                  </tr>
                </thead>
                <tbody id="b_auth_table"></tbody>
              </table>
            </div>
            <button id="create_user" type="submit" class="btn btn-primary">Add User</button>
            <div id="dialog-form-user_a" title="Add User">
              <form>
                <fieldset>
                  <label for="displayName_user_form">User DisplayName</label>
                  <input type="text" id="displayName_user_form" value="User DisplayName" class="text ui-widget-content ui-corner-all">
                  <label for="name_user_form">User Name</label>
                  <input type="text" id="name_user_form" value="User Name" class="text ui-widget-content ui-corner-all">
                  <br>
                  <label for="pass1_user_form">Password</label>
                  <input type="password" id="pass1_user_form" value="Password" class="text ui-widget-content ui-corner-all">
                  <br>
                  <label for="role_grp_form">User's Group</label>
                  <select name="role" id="role_grp_form">
                      <option value="AppD-User">AppD-User</option>
                      <option value="AppD-Admin">AppD-Admin</option>
                  </select>
                  <br>
                  <!-- Allow submission with keyboard -->
                  <label for="user_type_form">Account Type</label>
                  <select name="user_account" id="user_type_form">
                      <option value="Site">Site</option>
                      <option value="Controller">Controller</option>
                  </select>
                  <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
                </fieldset>
              </form>
            </div>
            <div id="dialog-form-chg_passwd" title="Change Passwd">
              <form>
                <fieldset>
                <label for="uId_chg">User Id</label>
                <input type="text" name="uId_chg" id="uId_chg" value="User Id" class="text ui-widget-content ui-corner-all">
                <br>
                <label for="uName_chg">UserName</label>
                <input type="text" name="uName_chg" id="uName_chg" value="User Name" class="text ui-widget-content ui-corner-all">
                <br>
                <label for="password_chg">Password</label>
                <input type="password" name="password_chg" id="password_chg" value="xxxxxxx" class="text ui-widget-content ui-corner-all">
                <!-- Allow form submission with keyboard without duplicating the dialog button -->
                <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
              </fieldset>
              </form>
            </div>
           </div>
          <div>
            <h4>Controllers</h4>
            <div class="scroll_table">
              <table class="table table-bordered" id="controller_table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Display Name</th>
                    <th>Controller FQDN</th>
                    <th>Port</th>
                    <th>Use SSL</th>
                    <th>Account</th>
                    <th>Delete</th>
                  </tr>
                </thead>
                <tbody id="b_controller_table"></tbody>
              </table>
            </div>
            <button id="create_contr" type="submit" class="btn btn-primary">Add Controller</button>
            <div id="dialog-form-contr_a" title="Add Controller">
              <form>
                <fieldset>
                  <label for="displaName_contr_form">Display Name</label>
                  <input type="text" id="displayName_contr_form" value="Controller DisplayName" class="text ui-widget-content ui-corner-all">
                  <label for="fqdn_contr_form">Controller FQDN</label>
                  <input type="text" id="fqdn_contr_form" value="Controller FQDN" class="text ui-widget-content ui-corner-all">
                  <label for="port_contr_form">Controller Port</label>
                  <input type="text" id="port_contr_form" value="Port" class="text ui-widget-content ui-corner-all">
                  <br>
                  <!-- Allow submission with keyboard -->
                  <label for="ssl_contr_form">Use SSL</label>
                  <select name="useSSL" id="ssl_contr_form">
                      <option value="TRUE">TRUE</option>
                      <option value="FALSE">FALSE</option>
                  </select>
                  <br>
                  <label for="acct_contr_form">Account</label>
                  <input type="text" id="acct_contr_form" value="customer1" class="text ui-widget-content ui-corner-all">
                  <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
                </fieldset>
              </form>
            </div>
          </div>
          <div>
            <h4>Connections</h4>
            <div class="scroll_table">
              <table class="table table-bordered" id="conn_table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Display Name</th>
                    <th>Controller</th>
                    <th>Authentication</th>
                    <th>Delete</th>
                  </tr>
                </thead>
                <tbody id="b_conn_table"></tbody>
              </table>
            </div>
            <button id="create_conn" type="submit" class="btn btn-primary">Add Connection</button>
            <div id="dialog-form-conn_a" title="Add Connection">
              <form>
                <fieldset>
                  <label for="displaName_conn_form">Display Name</label>
                  <input type="text" id="displayName_conn_form" value="Controller Connection" class="text ui-widget-content ui-corner-all">

                  <!-- These will be filled in by the calls -->
                  <label for="controller_conn_form">Select Controller</label>
                  <select name="controller" id="controller_conn_form"> </select>

                  <label for="authentication_conn_form">User to Use</label>
                  <select name="authentication" id="authentication_conn_form"> </select>
                  <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
                </fieldset>
              </form>
            </div>
          </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="change_section">
          <div id="changes">
            <h4>Changes</h4>
            <div class="scroll_table">
              <table class="table table-bordered" id="change_table">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Requester</th>
                    <th>Description</th>
                    <th>Approved</th>
                    <th>Read For Approval</th>
                  </tr>
                </thead>
                <tbody id="b_changes_table"></tbody>
              </table>
            </div>
          </div>
          <div id="requests">
            <h4>Requests</h4>
            <div class="scroll_table">
              <table class="table table-bordered" id="request_table">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Item Name</th>
                    <th>Item Type</th>
                    <th>Source</th>
                    <th>Destination</th>
                  </tr>
                </thead>
                <tbody id="b_requests_table"></tbody>
              </table>
            </div>
            <button id="approve_change" type="submit" class="btn btn-primary">Approve Change </button>
            <button id="execute_change" type="submit" class="btn btn-primary">Execute Change </button>
            <button id="reject_change" type="submit" class="btn btn-primary">Reject Change </button>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
