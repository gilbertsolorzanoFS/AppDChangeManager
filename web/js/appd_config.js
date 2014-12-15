/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var POPULATE={};

var CHANGES={
    delay: 1000,
    attempts: 3,
    running: false,
    url: '/AppDChangeManager/ManageChanges',
    load: function(){
        
        if(this.running){
            return;
        }
        
        this.running = true;
 
        $.getJSON(this.url,function(data, textStatus, jqXHR){
               // console.log(data);                
                
        }).fail(function(jqXHR, textStatus, errorThrown){
                alert('Error grabbing data ' + errorThrown);
                return;
        });
        
        
    }
    
};

var CONFIGS = {
    container: '#c_intro',
    url: '/AppDChangeManager/ConfigServlet',
    url_1: '/AppDChangeManager/ManageChanges?type=ReadyForApproval',
    delay: 1000,
    attempts: 3,
    running: false,
    init: function(){
        this.container = $(this.container);
        var _config = this;
        $( this.container).scroll( 
                function(){_config.checkScroll();}
        );
    },
    checkScroll: function(){
        var cfg_table_div = $(this.container);
        if(cfg_table_div[0].scrollHeight - cfg_table_div.height() - cfg_table_div.scrollTop() <= 0){this.load();}
    },
    reset: function(){
        this.delay = 1000;
        this.attempts = 3;
    },
    load: function(){
        
        if(this.running){
            return;
        }
        
        this.running = true;
        
        $.getJSON(this.url,function(data, textStatus, jqXHR){
                //console.log(data);
                POPULATE.configs(data.configs);
                POPULATE.auth(data.auths);
                POPULATE.controllers(data.controllers);
                POPULATE.conn(data.conns);
     
                POPULATE.conn_dd();
                POPULATE.contr_dd();
                POPULATE.auth_dd();
                POPULATE.conf_dd();
                POPULATE.chg_pass_dd();
                
        }).fail(function(jqXHR, textStatus, errorThrown){
                alert('Error grabbing data ' + errorThrown);
                return;
        });
        
        $.getJSON(this.url_1,function(data, textStatus, jqXHR){
                //console.log(data);
                POPULATE.changes(data);
                $('#approve_change').prop("disabled",true);
                $('#execute_change').prop("disabled",true);
                
                
        }).fail(function(jqXHR, textStatus, errorThrown){
                alert('Error grabbing data ' + errorThrown);
                return;
        });
        
    }
    
};

POPULATE.requests = function(data){
    // First clear it
    $('#b_requests_table tr').each(function(){
        $(this).remove();
    });
    
    for(var i=0; i < data.length;i++){
        var item = data[i];
        //console.log(item);
        var src;
        var dest;
        if(item.srcItemLocation.locationType === 1){
            src=item.srcItemLocation.controller+"|"+item.srcItemLocation.application+"|"+item.srcItemLocation.tier;
        }else{
            src=item.srcItemLocation.controller+"|"+item.srcItemLocation.application;
        }
        
        if(item.destItemLocation.locationType === 1){
            dest=item.destItemLocation.controller+"|"+item.destItemLocation.application+"|"+item.destItemLocation.tier;
        }else{
            dest=item.destItemLocation.controller+"|"+item.destItemLocation.application;
        }
        $('#b_requests_table').append('<tr><td class="id">'+item.id
                +'</td><td>'+item.itemName
                +'</td><td>'+item.itemType
                +'</td><td>'+src
                +'</td><td>'+dest+'</td></tr>');

    }
};

POPULATE.approve_b = function(){
    $('#approve_change').button().on("click",function(){
        var tmpChange=$('#approve_change').text().split(" ");
        var changeId=tmpChange[tmpChange.length-1];
        var url='/AppDChangeManager/ManageChanges?type=approve_chg&id='+changeId;
        console.log(url);
        $.post(url);
    });
    
    $('#execute_change').button().on("click",function(){
        var tmpChange=$('#execute_change').text().split(" ");
        var changeId=tmpChange[tmpChange.length-1];
        var url='/AppDChangeManager/ManageChanges?type=execute_chg&id='+changeId;
        console.log(url);
        $.post(url);
    });
    
    $('#reject_change').button().on("click",function(){
        var tmpChange=$('#reject_change').text().split(" ");
        var changeId=tmpChange[tmpChange.length-1];
        var url='/AppDChangeManager/ManageChanges?type=reject_chg&id='+changeId;
        console.log(url);
        $.post(url);
        
    });
    
};

// This is going to be the changes
POPULATE.changes = function(data){

    for(var i=0; i < data.length;i++){
        var item = data[i];
        //console.log(item);
        $('#change_table').append('<tr><td class="id">'+item.id
                +'</td><td>'+item.requester
                +'</td><td>'+item.descr
                +'</td><td>'+item.approved
                +'</td><td>'+item.readyForApproval
                +'</td></tr>');
    }
    
    $('#change_table tr').click(function(){
        $('#change_table tr').each(function(){
                $(this).removeClass('highlight');
        });
        $(this).addClass('highlight');
        var changeId=$(this)[0].childNodes[0].textContent;
        var approved=$(this)[0].childNodes[3].textContent;
        var executed=$(this)[0].childNodes[4].textContent;
        
        //console.log($(this)[0].childNodes[0].textContent);
        var url='/AppDChangeManager/ManageRequests?changeId='+changeId;
        $('#approve_change').text('Approve Change '+changeId);
        $('#execute_change').text('Execute Change '+changeId);
        $('#reject_change').text('Reject Change '+changeId);
        if(approved === 'false'){ $('#approve_change').prop("disabled",false);POPULATE.approve_b();}
        else{ $('#execute_change').prop("disabled",false);POPULATE.approve_b();}
        
        //console.log($(this)[0].childNodes[0].textContent + "::" + url);
        $.getJSON(url,function(data, textStatus, jqXHR){
                //console.log(data);
                POPULATE.requests(data);

        }).fail(function(jqXHR, textStatus, errorThrown){
                alert('Error grabbing data ' + errorThrown);
                return;
        });
        //alert($(this).text());
    }); 
/*
    $('#b_config_table').find('.del :button').on('click',function(e){
        POPULATE.conf_d(this);
        e.preventDefault();
    });

    $('#b_config_table').find('.edit :button').on('click',function(e){
        POPULATE.conf_u(this);
        e.preventDefault();
    });
*/
};

//Nothing else to do
POPULATE.configs = function(data){

    for(var i=0; i < data.length;i++){
        var item = data[i];
        //console.log(item);
        $('#b_config_table').append('<tr><td class="id">'+item.name
                +'</td><td>'+item.value
                +'</td><td class="edit"><input type="button" value="Edit"/></td>'
                +'<td class="del"><input type="button" value="Del"/></td></tr>');
    }

    $('#b_config_table').find('.del :button').on('click',function(e){
        POPULATE.conf_d(this);
        e.preventDefault();
    });

    $('#b_config_table').find('.edit :button').on('click',function(e){
        POPULATE.conf_u(this);
        e.preventDefault();
    });
};


POPULATE.auth = function(data){

    //console.log("Adding rows");
    for(var i=0; i < data.length;i++){
        var item = data[i];
        // console.log(data);
        var acctType = 'Site';
        if( item.acctType === 1 ){ 
            acctType = 'Controller';
            // We are only adding controller information
            $('#authentication_conn_form').append('<option class="id">'
                    +item.id+':'+item.displayName+'</option>');
        }

        $('#b_auth_table').append('<tr><td class="id">'+item.id+'</td><td>'
                +item.displayName+'</td><td class="username">'+item.userName
                +'</td><td>'+acctType+'</td><td class="groupname">'+item.groupName
                +'</td><td class="chgPass"><input type="button" value="Change Pass"/>'
                +'</td><td class="del"><input type="button" value="Del"/></td></tr>'
        );


    }    

    $('#b_auth_table').find('.del :button').on('click',function(e){
        POPULATE.auth_d(this);
        e.preventDefault();
    });
    
    $('#b_auth_table').find('.chgPass :button').on('click',function(e){
        //POPULATE.auth_d(this); this is going to be the dialog to change the password.
        POPULATE.chg_pass_row(this);
        e.preventDefault();
    });

};

POPULATE.controllers = function(data){
    

        // We should clear the table the populate it
        for(var i=0; i < data.length;i++){
            var item = data[i];
            var useSSL = 'False';
            if( item.useSSL === 1) useSSL = 'True';
            //console.log(item);
            $('#b_controller_table').append('<tr><td class="id">'+item.id
                    +'</td><td>'+item.displayName+'</td><td>'+item.fqdn+'</td><td>'
                    +item.port+'</td><td>'+useSSL+'</td><td>'+item.account
                    +'</td><td class="del"><input type="button" value="Del"/></td></tr>');
            // Form options
            $('#controller_conn_form').append('<option class="id">'+item.id+':'+item.displayName+'</option>');
        }

        $('#b_controller_table').find('.del :button').on('click',function(e){
            POPULATE.contr_d(this);
            e.preventDefault();
        });

}

POPULATE.conn = function(data){
        
        for(var i=0; i < data.length;i++){
            var item = data[i];
            // console.log(item);
            $('#b_conn_table').append('<tr><td class="id">'+item.id
                    +'</td><td>'+item.displayName+'</td><td>'+item.controllerId
                    +'</td><td>'+item.authId
                    +'</td><td class="del"><input type="button" value="Del"/></td></tr>');
        }

        $('#b_conn_table').find('.edit :button').on('click',function(e){
            POPULATE.conn_u(this);
            e.preventDefault();
        });

        $('#b_conn_table').find('.del :button').on('click',function(e){
            POPULATE.conn_d(this);
            e.preventDefault();
        });
    
};

/*
 * 
 * No need to update this section anymore, its complete
 */
POPULATE.conf_d = function(button){
    var $button = $(button);
    var $row = $button.parents('tbody tr');
    
    // We need to delete here!!
    var dN=$row[0].childNodes[0].textContent;
    //console.log("Deleting id " + dN);
    
    var dataS = "/AppDChangeManager/ConfigServlet?type=del_conf&displayName="+dN;
    $.post(dataS);
    $row.remove();
};

/*
 * 
 * @param {type} button
 * @returns {undefined}
 */
POPULATE.conn_d = function(button){
    var $button = $(button);
    var $row = $button.parents('tbody tr');
    
    // We need to delete here!!
    var id=$row[0].childNodes[0].textContent;
    //console.log("Deleting id " + id);
    
    var dataS = "/AppDChangeManager/ConfigServlet?type=del_conn&id="+id;
    $.post(dataS);
    $row.remove();
};

POPULATE.auth_d = function(button){
    var $button = $(button);
    var $row = $button.parents('tbody tr');
    // We need to delete here!!
    //console.log($row);
    var id=$row[0].childNodes[0].textContent;
    //console.log("Deleting id " + id);
    
    var dataS = "/AppDChangeManager/ConfigServlet?type=del_auth&id="+id;
    $.post(dataS);
    $row.remove();
};

POPULATE.contr_d = function(button){
    var $button = $(button);
    var $row = $button.parents('tbody tr');
    // We need to delete here!!
    //console.log($row);
    var id=$row[0].childNodes[0].textContent;
    //console.log("Deleting id " + id);
    
    var dataS = "/AppDChangeManager/ConfigServlet?type=del_contr&id="+id;
    $.post(dataS);
    
    $row.remove();
};

POPULATE.conf_a = function(){
    var dN=$('#displayName_conf_form').val();
    var value=$('#value_conf_form').val();
    
    $('#dialog-form-conf_a').dialog("close");
    var dataS = "/AppDChangeManager/ConfigServlet?type=add_conf&displayName="
            +dN+"&value="+value;

    $.post(dataS, function(data){
        var item=data;
        $('#b_config_table').append('<tr><td class="id">'+item.name+'</td><td>'
                +item.value+'</td><td class="edit"><input type="button" value="Edit"/></td>'
                +'<td class="del"><input type="button" value="Del"/></td></tr>');
        
    });
    
    $('#b_config_table').find('.del :button').on('click',function(e){
        POPULATE.conf_d(this);
        e.preventDefault();
    });

    $('#b_config_table').find('.edit :button').on('click',function(e){
        POPULATE.conf_u(this);
        e.preventDefault();
    });
    
};

// Authentications
POPULATE.auth_a = function(){
   
    var dN=$('#displayName_user_form').val();
    var userName=$('#name_user_form').val();
    var pass = $('#pass1_user_form').val();
    var uType = $('#user_type_form').val();
    var role = $('#role_grp_form').val();
    
    
    var iType=1;
    if(uType === 'Site') iType=0;
    
    $('#dialog-form-user_a').dialog("close");
    var dataS = "/AppDChangeManager/ConfigServlet?type=add_auth&displayName="
            +dN+"&username="+userName+"&password="+pass+"&acctType="
            +iType+"&rolename="+role;
    //var dataS = "/AppDChangeManager/ConfigServlet?type=add_grp&userId="+uId+"&roleName="+role;
    
    $.post(dataS, function(data){
        //console.log(data);
        var item=data;
        var acctType = 'Site';
        if( item.acctType === 1 ){ 
            acctType = 'Controller';
            // We are only adding controller information
            $('#authentication_conn_form').append('<option class="id">'+item.id
                    +':'+item.displayName+'</option>');
        }
         $('#b_auth_table').append('<tr><td class="id">'+item.id+'</td><td>'
                 +item.displayName+'</td><td class="username">'+item.userName
                 +'</td><td>'+acctType+'</td><td class="groupname">'+item.groupName
                 +'</td><td class="chgPass"><input type="button" value="Change Pass"/>'
                 +'</td><td class="del"><input type="button" value="Del"/></td></tr>');
    });
    
    
    $('#b_auth_table').find('.del :button').on('click',function(e){
        POPULATE.auth_d(this);
        e.preventDefault();
    });

    $('#b_auth_table').find('.chgPass :button').on('click',function(e){
        //POPULATE.auth_d(this); this is going to be the dialog to change the password.
        e.preventDefault();
    });
    
    
};

//Connection
POPULATE.conn_a = function(){
    // These are the variables that we are going to use
    var contr_id=$('#controller_conn_form').val().split(':')[0];
    var auth_id=$('#authentication_conn_form').val().split(':')[0];
    var dN=$('#displayName_conn_form').val();
    
    $('#dialog-form-conn_a').dialog("close");
    var dataS = "/AppDChangeManager/ConfigServlet?type=add_conn&controllerId="
            +contr_id+"&authenticationId="+auth_id+"&displayName="+dN;
    
    
    $.post(dataS, function(data){
        var item=data;
        $('#b_conn_table').append('<tr><td class="id">'+item.id+'</td><td>'
                +item.displayName+'</td><td>'+item.controllerId+'</td><td>'
                +item.authId+'</td><td class="del"><input type="button" value="Del"/></td></tr>');
        
    });
    

    $('#b_conn_table').find('.del :button').on('click',function(e){
        POPULATE.conn_d(this);
        e.preventDefault();
    });
};

//Controllers
POPULATE.contr_a = function(){
    //console.log("Called conn_a " + $('#controller_conn_form').val() + " " + $('#authentication_conn_form').val() + " " + $('#displayName_conn_form').val());
    var dN=$('#displayName_contr_form').val();
    var fqdn=$('#fqdn_contr_form').val();
    var port=$('#port_contr_form').val();
    var useSSL=$('#ssl_contr_form').val();
    var acct=$('#acct_contr_form').val();
    
    $('#dialog-form-contr_a').dialog("close");
    var dataS = "/AppDChangeManager/ConfigServlet?type=add_contr&fqdn="+fqdn+"&port="+port+"&displayName="+dN+"&useSSL="+useSSL+"&account="+acct;
    //console.log(dataS);
    $.post(dataS, function(data){
        
        var item=data;
        var useSSL = 'False';
        if( item.useSSL === 1) useSSL = 'True';
        //console.log(item);
        $('#b_controller_table').append('<tr><td>'+item.id+'</td><td>'
                +item.displayName+'</td><td>'+item.fqdn+'</td><td>'+item.port
                +'</td><td>'+useSSL+'</td><td>'+item.account
                +'</td><td class="del"><input type="button" value="Del"/></td></tr>');
        // Form options
        $('#controller_conn_form').append('<option>'+item.id+':'+item.displayName+'</option>');
    });
};

POPULATE.conf_dd = function(){
    var cf_dialog=$('#dialog-form-conf_a');
      cf_dialog.dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
            "Create Configuration": POPULATE.conf_a,
            Cancel: function(){
                cf_dialog.dialog("close");
            }
        },
        close: function(){
            form[0].reset();
        }
    });
    
    var form = cf_dialog.find("form").on("submit",function(event){
        event.preventDefault();
        POPULATE.conf_a();
    });
    
    $('#create_conf').button().on("click",function(){
        //console.log("--create-conf clicked");
        cf_dialog.dialog("open");
    });
    
};

POPULATE.auth_dd = function(){
    
    var at_dialog=$('#dialog-form-user_a');
      at_dialog.dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
            Create_User: POPULATE.auth_a,
            Cancel: function(){
                at_dialog.dialog("close");
            }
        },
        close: function(){
            form[0].reset();
        }
    });
    
    var form = at_dialog.find("form").on("submit",function(event){
        event.preventDefault();
        POPULATE.auth_a();
    });
    
    $('#create_user').on("click",function(){
        //console.log("--create-user clicked");
        at_dialog.dialog("open");
    });
    
};

POPULATE.chg_pass_a = function(){
    var uId = $('#uId_chg').val();
    var uName = $('#uName_chg').val();
    var pass = $('#password_chg').val();
    var at_dialog=$('#dialog-form-chg_passwd');

    var dataS = "/AppDChangeManager/ConfigServlet?type=chg_passwd&id="
            +uId+"&password="+pass;
    
    console.log(dataS);
    at_dialog.dialog("close");
    
    $.post(dataS);
};

POPULATE.chg_pass_row = function(button){
    var $button = $(button);
    var at_dialog=$('#dialog-form-chg_passwd');
    var $row = $button.parents('tbody tr');
    
    var uId=$row[0].childNodes[0].textContent;
    var uName=$row[0].childNodes[2].textContent;
    
    $('#uId_chg').val(uId).prop("disabled",true);
    $('#uName_chg').val(uName).prop("disabled",true);
    
    at_dialog.dialog("open");
    
    
};

POPULATE.chg_pass_dd = function(){
  // This will be a dialog, hich will ask for the new password, no validation
  // make the dialog visible, get the strings compare then
  // from the button we need to make sure that we create the dialog
  // dn
  // username:XXX
  // New Password:XXXX
  // 
  
  var at_dialog=$('#dialog-form-chg_passwd');
      at_dialog.dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
            "Change Password": POPULATE.chg_pass_a,
            Cancel: function(){
                at_dialog.dialog("close");
            }
        },
        close: function(){
            form[0].reset();
        }
    });
    
    var form = at_dialog.find("form").on("submit",function(event){
        event.preventDefault();
        POPULATE.chg_pass_a();
    });
        
    
};


POPULATE.contr_dd = function(){
    var ct_dialog=$('#dialog-form-contr_a');
      ct_dialog.dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
            "Create Controller": POPULATE.contr_a,
            Cancel: function(){
                ct_dialog.dialog("close");
            }
        },
        close: function(){
            form[0].reset();
        }
    });
    
    var form = ct_dialog.find("form").on("submit",function(event){
        event.preventDefault();
        POPULATE.contr_a();
    });
    
    $('#create_contr').on("click",function(){
        //console.log("--create-contr clicked");
        ct_dialog.dialog("open");
    });
};

POPULATE.conn_dd = function(){

    var cn_dialog=$('#dialog-form-conn_a');
      cn_dialog.dialog({
        autoOpen: false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
            "Create Connection": POPULATE.conn_a,
            Cancel: function(){
                cn_dialog.dialog("close");
            }
        },
        close: function(){
            form[0].reset();
        }
    });
    
    var form = cn_dialog.find("form").on("submit",function(event){
        event.preventDefault();
        POPULATE.conn_a();
    });
    
    $('#create_conn').button().on("click",function(){
        //console.log("--create-conn clicked");
        cn_dialog.dialog("open");
    });
        
    
};

// For conf we can only update values, test this:
POPULATE.conf_u = function(button){
    var $button = $(button);
    var $row = $button.parents('tbody tr');
    var $cells = $row.children('td').not('.edit').not('.id').not('.del');
    
    if($row.data('flag')){
        // This is when we are editing
        $cells.each(function(){
            var _cell = $(this);
            // This just rest the values
            _cell.html(_cell.find('input').val());
        });
        
        $row.data('flag',false);
        $button.val('Edit');
        // This is where we post the information: we need to get the values of the cells
        var cells_ = $row.children('td').not('.edit').not('.del');
        //now how do we get each one ??
        //console.log(cells_[1].data);
        var dataS='/AppDChangeManager/ConfigServlet?type=update_conf&displayName=' + cells_[0].innerText+'&value='+cells_[1].innerText;
        //console.log(dataS);
        $.post(dataS); 
        
    }else{
        //console.log("Open for edit");
        
        $cells.each(function(){
            var _cell = $(this);
            _cell.data('text', _cell.html()).html('');
            
            var $input = $('<input type="text" />').val(_cell.data('text')).width(_cell.width() - 16);
            
            _cell.append($input);
        });
        
        $row.data('flag',true);
        $button.val('Save');
    }
    
    
};


/*
 * 
 */

$(document).ready(function(){
    CONFIGS.load();
    $('#configs').tabs();
});
// EOF
