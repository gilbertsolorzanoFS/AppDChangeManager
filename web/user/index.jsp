<%--
    Document   : index
    Created on : Nov 10, 2014, 9:45:44 AM
    Author     : gilbert.solorzano
    Page: user/index.jsp
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
    <script src="${pageContext.request.contextPath}/js/appd.js"></script>

    <title>User Changes Page</title>
  </head>
  <body>
    <div class="container">
      <h1>User Changes</h1>
      <div class="">
        <form action="/AppDChangeManager/LogoutServlet" method="post" class="pull-right">
          <input type="submit" class="btn btn-warning" value="Logout">
        </form>
      </div>
      <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#edit_changes" aria-controls="edit_changes" role="tab" data-toggle="tab" aria-expanded="true">Edit Changes</a></li>
        <li role="presentation"><a href="#view_changes" aria-controls="view_changes" role="tab" data-toggle="tab">View Changes</a></li>
      </ul>
      <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="edit_changes">
          <div id="changes">
            <h4>Changes</h4>
            <div class="scroll_table">
              <table class="table table-bordered " id="change_table">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Requester</th>
                    <th>Description</th>
                    <th>Approved</th>
                    <th>Execute</th>
                  </tr>
                </thead>
                <tbody id="b_changes_table">
                </tbody>
              </table>
            </div>
            <button id="create_change" type="submit" class="btn btn-primary">Add Change</button>
            <div id="dialog-form-chg_a" title="Add Change">
              <form>
                <fieldset>
                  <label for="descr_chg_form">Description</label>
                  <input type="text" id="descr_chg_form" value="Descr" class="text ui-widget-content ui-corner-all">
                  <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
                </fieldset>
              </form>
            </div>
          </div>
          <div id="requests">
            <h4>Requests</h4>
            <div class="scroll_table">
              <table class="table table-bordered scroll_table" id="request_table">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Item Name</th>
                    <th>Item Type</th>
                    <th>Source</th>
                    <th>Destination</th>
                    <th>Delete</th>
                  </tr>
                </thead>
                <tbody id="b_requests_table">
                </tbody>
              </table>
            </div>
            <button id="create_request" type="submit" class="btn btn-primary">Add Request to</button>
            <button id="request_readyforappr" type="submit" class="btn btn-primary">Ready For Approval</button>
          </div>
          <div id="dialog-form-req" title="Add Request">
            <form id="form-req" >
              <fieldset>
                <h4>Add Request</h4>
                <label for="req_type">Type</label>
                <select name="req_type" id="req_type">
                    <option value="_default_">Select Type</option>
                    <option value="auto-discovery">Auto-Discovery</option>
                    <option value="custom-match-pojo">Custom-Pojo</option>
                    <option value="health-rule">Health-Rule</option>
                </select>
                <br>
                <label for="req_srcConn">Source Connection</label>
                <select name="req_srcConn" id="req_srcConn">
                    <option value="_default_">Select A Connection</option>
                </select>
                <br>

                <label for="req_srcApp">Source Application</label>
                <select name="req_srcApp" id="req_srcApp">
                    <option value="_default_">Select An Application</option>
                </select>
                <br>

                <label for="req_srcTier">Source Tier</label>
                <select name="req_srcTier" id="req_srcTier">
                    <option value="_default_">Select A Tier</option>
                </select>
                <br>

                <label for="req_srcItemName">Item Name</label>
                <select name="req_srcItemName" id="req_srcItemName">
                    <option value="_default_">Select An Item</option>
                </select>
                <br>

                <label for="req_destConn">Destination Connection</label>
                <select name="req_destConn" id="req_destConn">
                    <option value="_default_">Select A Connection</option>
                </select>
                <br>

                <label for="req_destApp">Destination Application</label>
                <select name="req_destApp" id="req_destApp">
                    <option value="_default_">Select An Application</option>
                </select>
                <br>

                <label for="req_destTier">Destination Tier</label>
                <select name="req_destTier" id="req_destTier">
                    <option value="_default_">Select A Tier</option>
                </select>
                <br>

              </fieldset>
            </form>
          </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="view_changes">
          <div id="v_changes">
            <h4>Changes</h4>
            <label for="v_change_sel">View Changes</label>
            <select name="v_change_sel" id="v_change_sel">
                <option value="Approved">Approved Changes</option>
                <option value="Completed">Completed Changes</option>
                <option value="Not-ReadyForApproval">Changes Ready For Approval</option>
                <option value="All">All Changes</option>
            </select>
            <div class="scroll_table">
              <table class="table table-bordered" id="v_change_table">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Requester</th>
                    <th>Description</th>
                    <th>Approved</th>
                    <th>Execute</th>
                  </tr>
                </thead>
                <tbody id="v_b_changes_table">

                </tbody>
              </table>
            </div>
          </div>
          <div id="v_requests">
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
                <tbody id="v_b_requests_table">
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
