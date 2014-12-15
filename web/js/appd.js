/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * This is going to be the javascript file for the user section
 * 
 * 
 * user view
 */

var POPULATE={};

var CONFIGS = {
    container: '#edit_changes',
    url_1: '/AppDChangeManager/ManageChanges?type=Not-ReadyForApproval',
    url_2: '/AppDChangeManager/ManageChanges?type=Approved',
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

        $.getJSON(this.url_1,function(data, textStatus, jqXHR){
                POPULATE.changes(data);
                
                POPULATE.req_dd();
                POPULATE.chg_dd();
                
                
        }).fail(function(jqXHR, textStatus, errorThrown){
                alert('Error grabbing data ' + errorThrown);
                return;
        });
        
        // View population
        $.getJSON(this.url_2,function(data, textStatus, jqXHR){
                POPULATE.view_selector();
                POPULATE.change_view(data);
                
        }).fail(function(jqXHR, textStatus, errorThrown){
                alert('Error grabbing data ' + errorThrown);
                return;
        });
        
    }
    
};

POPULATE.view_selector = function(){
    // This is going to select the data that we view.
    $('#v_change_sel').change(function(){
        var _val=$(this).val();
        var url='/AppDChangeManager/ManageChanges?type='+_val;
        console.log("Checking change " + url);
        $.getJSON(url,function(data, textStatus, jqXHR){
                POPULATE.change_view(data);
        }).fail(function(jqXHR, textStatus, errorThrown){
                alert('Error grabbing data ' + errorThrown);
                return;
        });
        
        
    });
};

POPULATE.view_requests = function(data){
    // First clear it
    $('#v_b_requests_table tr').each(function(){
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
        $('#v_b_requests_table').append('<tr><td class="id">'+item.id
                +'</td><td>'+item.itemName
                +'</td><td>'+item.itemType
                +'</td><td>'+src
                +'</td><td>'+dest+'</td></tr>'); 
    }

};

POPULATE.view_change_action = function(){
    $('#v_b_changes_table tr').click(function(){
        $('#v_b_changes_table tr').each(function(){
                $(this).removeClass('highlight');
        });
        $(this).addClass('highlight');
        
        var changeId=$(this)[0].childNodes[0].textContent;
        
        var url='/AppDChangeManager/ManageRequests?changeId='+changeId;
        console.log(changeId + "::" + url);
        $.getJSON(url,function(data, textStatus, jqXHR){
                //console.log(data);
                POPULATE.view_requests(data);

        }).fail(function(jqXHR, textStatus, errorThrown){
                alert('Error grabbing data ' + errorThrown);
                return;
        });
    }); 

};

POPULATE.change_view = function(data){
    var _tbl = $('#v_b_changes_table');
    // Clear both tables
    $('#v_b_changes_table tr').each(function(){
        $(this).remove();
    });
    
    $('#v_b_requests_table tr').each(function(){
        $(this).remove();
    });
    
    for(var i=0; i < data.length;i++){
        var item = data[i];
        _tbl.append('<tr><td class="id">'+item.id
                +'</td><td>'+item.requester
                +'</td><td>'+item.descr
                +'</td><td>'+item.approved
                +'</td><td>'+item.readyForApproval
                +'</td></tr>');
    }
    
    POPULATE.view_change_action();
};


POPULATE.ready_for_approv = function(){
    var tmpChange=$('#request_readyforappr').text().split(" ");
    var changeId=tmpChange[1];
    var dataS = "/AppDChangeManager/ManageChanges?type=readyForApproval_chg&id="+changeId;
    
    console.log(dataS);
    //$.post(dataS);
    // Just going to load everything.
    //POPULATE.reset_page();
    
};

POPULATE.reset_page = function(){
    //First we need to update the buttons
    
    var url_1 = '/AppDChangeManager/ManageChanges?type=Not-ReadyForApproval';
    
    $.getJSON(this.url_1,function(data, textStatus, jqXHR){
                POPULATE.changes(data);                      
        }).fail(function(jqXHR, textStatus, errorThrown){
                alert('Error grabbing data ' + errorThrown);
                return;
        });
    
};


POPULATE.change_action = function(){
    $('#b_changes_table tr').click(function(){
        $('#b_changes_table tr').each(function(){
                $(this).removeClass('highlight');
        });
        $(this).addClass('highlight');
        
        var changeId=$(this)[0].childNodes[0].textContent;
        console.log(changeId);
        $('#create_request').prop("disabled",false);
        $('#create_request').text('Add Request To '+changeId);
        $('#request_readyforappr').prop("disabled",false);
        $('#request_readyforappr').text('Change '+changeId+' Ready For Approval');
        
        var url='/AppDChangeManager/ManageRequests?changeId='+changeId;
        console.log(changeId + "::" + url);
        $.getJSON(url,function(data, textStatus, jqXHR){
                console.log(data);
                POPULATE.requests(data);

        }).fail(function(jqXHR, textStatus, errorThrown){
                alert('Error grabbing data ' + errorThrown);
                return;
        });
    }); 
    
    $('#request_readyforappr').button().on("click",function(event){ 
       POPULATE.ready_for_approv();
   });
};

/*
 * This is going to populate the initial changes table
 * @param {type} data
 * @returns {undefined}
 * 
 */
POPULATE.changes = function(data){
    var _tbl = $('#b_changes_table');
    $('#create_request').prop("disabled",true);
    $('#create_request').text('Add Request To ');
    $('#request_readyforappr').prop("disabled",true);
    $('#request_readyforappr').text('Ready For Approval');
    
    for(var i=0; i < data.length;i++){
        var item = data[i];
        _tbl.append('<tr><td class="id">'+item.id
                +'</td><td>'+item.requester
                +'</td><td>'+item.descr
                +'</td><td>'+item.approved
                +'</td><td>'+item.readyForApproval
                +'</td></tr>');
    }
    
    POPULATE.change_action();


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
                +'</td><td>'+dest+'</td><td class="del"><input type="button" value="Del"/></td></tr>'); 
    }
    $('#b_requests_table').find('.del :button').on('click',function(e){
        POPULATE.req_d(this);
        e.preventDefault();
    });
};

// This is where we are going to add a change.
POPULATE.chg_a = function(){
    var descr = $('#descr_chg_form').val();
    
    var dataS = "/AppDChangeManager/ManageChanges?type=add_chg&descr="+descr;
    
    //console.log(dataS);
    
    $.post(dataS,function(data){
        //console.log("--Change value: ");
        //console.log(data);
        var item=data;
        
        $('#b_changes_table').append('<tr><td class="id">'+item.id
                +'</td><td>'+item.requester
                +'</td><td>'+item.descr
                +'</td><td>'+item.approved
                +'</td><td>'+item.readyForApproval
                +'</td></tr>');
        
    });
    
    $('#dialog-form-chg_a').dialog("close");
    POPULATE.change_action();
    
};



POPULATE.reset_req_form = function(){
    console.log("Called reset_req_form.");
    
    $('#req_srcConn option').each(function(){$(this).remove();});
    $('#req_srcConn')[0].options.length =0;
    $('#req_srcConn').append('<option value="_default_">Source Connection</option>');
    
    $('#req_destConn option').each(function(){$(this).remove();});
    $('#req_destConn')[0].options.length =0;
    $('#req_destConn').append('<option value="_default_">Source Connection</option>');
    
    $('#req_srcApp option').each(function(){$(this).remove();});
    $('#req_srcApp')[0].options.length =0;
    $('#req_srcApp').append('<option value="_default_">Select An Application</option>');
    
    //$('#req_srcApp').find('option:not(first)').remove();
    
    
    $('#req_destApp option').each(function(){$(this).remove();});
    $('#req_destApp')[0].options.length =0;
    $('#req_destApp').append('<option value="_default_">Select An Application</option>');
    //$('#req_destApp').find('option:not(first)').remove();
    
    
    $('#req_srcTier option').each(function(){$(this).remove();});
    $('#req_srcTier')[0].options.length =0;
    $('#req_srcTier').append('<option value="_default_">Select A Tier</option>');
    //$('#req_srcTier').find('option:not(first)').remove();
    
    
    $('#req_srcItemName option').each(function(){$(this).remove();});
    $('#req_srcItemName')[0].options.length =0;
    $('#req_srcItemName').append('<option value="_default_">Select An Item</option>');
    
    //$('#req_srcItemName').find('option:not(first)').remove();
    
    
    $('#req_destTier option').each(function(){$(this).remove();});
    $('#req_destTier')[0].options.length =0;
    $('#req_destTier').append('<option value="_default_">Select A Tier</option>');
    
    //$('#req_destTier').find('option:not(first)').remove();
    console.log("Completed reset_req_form.");
    
};


POPULATE.chg_d = function(button){
    var $button = $(button);
    var $row = $button.parents('tbody tr');
    
    // We need to delete here!!
    var dN=$row[0].childNodes[0].textContent;
    //console.log("Deleting id " + dN);
    
    var dataS = "/AppDChangeManager/ManageChanges?type=delete_chg&id="+dN;
    $.post(dataS);
    $row.remove();  
};

POPULATE.req_d = function(button){
    var $button = $(button);
    var $row = $button.parents('tbody tr');
    
    // We need to delete here!!
    var dN=$row[0].childNodes[0].textContent;
    //console.log("Deleting id " + dN);
    
    var dataS = "/AppDChangeManager/ManageRequests?type=delete_request&id="+dN;
    $.post(dataS);
    $row.remove();  
};

POPULATE.req_a = function(){
    //How are we going to get the change id?
  console.log("req_a was called " + $('#create_request').text()); 
  var tmpChange=$('#create_request').text().split(" ");
  var changeId=tmpChange[tmpChange.length-1];
  var itemType=$('#req_type option:selected').val();
  var srcConn=$('#req_srcConn option:selected').val().split(":")[0];
  var srcApp=$('#req_srcApp option:selected').val();
  var srcTier=$('#req_srcTier option:selected').val();
  var itemName=$('#req_srcItemName option:selected').val();
  var destConn=$('#req_destConn option:selected').val().split(":")[0];
  var destApp=$('#req_destApp option:selected').val();
  var destTier=$('#req_destTier option:selected').val();
  
  var url='/AppDChangeManager/ManageRequests?type=add_request&changeId='+changeId;
  if((itemType !== '_default_') && (srcConn  !== '_default_') 
          && (srcApp  !== '_default_') && (itemName  !== '_default_') 
          && (destConn  !== '_default_') && (destApp  !== '_default_') ){
      
          url=url+'&itemName='+itemName;
          url=url+'&itemType='+itemType;
          url=url+'&srcConnId='+srcConn;
          url=url+'&destConnId='+destConn;
          url=url+'&srcILApp='+srcApp;
          if(srcTier !== '_default_') url=url+'&srcILTier='+srcTier;
          url=url+'&destILApp='+destApp;
          if(destTier !== '_default_') url=url+'&destILTier='+destTier;
          
          console.log('The url is ' + url);
          $.post(url,function(data){
                console.log("Request data should be here!");
                console.log(data);
                var item=data;
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
                +'</td><td>'+dest+'</td><td class="del"><input type="button" value="Del"/></td></tr>');

            });
            $('#b_requests_table').find('.del :button').on('click',function(e){
                POPULATE.req_d(this);
                e.preventDefault();
            });
  }else{ 
      url='unexpected default value';
  }
          
  
  POPULATE.reset_req_form();
  $('#dialog-form-req').dialog("close");
};



// This is going to control the actions on the reques form
POPULATE.req_actions=function(){
    //
    var url;
    $('#req_srcConn').change(function(){
        if($(this).val() !== '_default_'){ 
             
            var fullVal=$(this).val();
            var conn =fullVal.split(":")[0];
            //console.log('Src: ' +conn);
            
            url='/AppDChangeManager/ManageItems?type=applications&connectionId='+conn;
            
            $.getJSON(url,function(data, textStatus, jqXHR){
                //console.log(data);
                for(var i=0; i < data.length;i++){
                    var item=data[i].split("|")[1];
                    $('#req_srcApp').append('<option value="'+item+'">'+item+'</option>');  
                }

            }).fail(function(jqXHR, textStatus, errorThrown){
                    alert('Error grabbing data ' + errorThrown);
                    return;
            });
        }
        
    });
    
    // Fill the app
    $('#req_destConn').change(function(){
        if($(this).val() !== '_default_'){ 
            var fullVal=$(this).val();
            var conn =fullVal.split(":")[0];
            
            url='/AppDChangeManager/ManageItems?type=applications&connectionId='+conn;
            
            
            $.getJSON(url,function(data, textStatus, jqXHR){
                //console.log(data);
                //console.log('DestApp '+ data[0]);
                for(var i=0; i < data.length;i++){
                    var item=data[i].split("|")[1];
                    $('#req_destApp').append('<option value="'+item+'">'+item+'</option>');  
                }
                
            }).fail(function(jqXHR, textStatus, errorThrown){
                    alert('Error grabbing data ' + errorThrown);
                    return;
            });
            
            
            
        }
    });
    
    /*
     * First we are going to fill in the tiers and then the items.
     */
    
    $('#req_srcApp').change(function(){
        if($(this).val() !== '_default_'){ 
            var type=$('#req_type option:selected').val();
            var app=$(this).val();
            var conn=$('#req_srcConn option:selected').val().split(":")[0];
            // Grab the tiers

            url='/AppDChangeManager/ManageItems?type=tiers&applicationId='+app+'&connectionId='+conn;
            
            $.getJSON(url,function(data, textStatus, jqXHR){
               
                for(var i=0; i < data.length;i++){
                    var item=data[i].split("|")[1];
                    $('#req_srcTier').append('<option value="'+item+'">'+item+'</option>');  
                }
                
            }).fail(function(jqXHR, textStatus, errorThrown){
                    alert('Error grabbing data ' + errorThrown);
                    return;
            });
            // Grab the types
            if(type !== '_default_'){
                
                url='/AppDChangeManager/ManageItems?type='+type+"&applicationId="+app+'&connectionId='+conn;
                
                $.getJSON(url,function(data, textStatus, jqXHR){
                    
                    for(var i=0; i < data.length;i++){
                        var item=data[i];
                        $('#req_srcItemName').append('<option value="'+item+'">'+item+'</option>');  
                    }
                
                }).fail(function(jqXHR, textStatus, errorThrown){
                        alert('Error grabbing data ' + errorThrown);
                        return;
                });
                
            }
            
        }
    });

    $('#req_destApp').change(function(){
        if($(this).val() !== '_default_'){ 
            var type=$('#req_type option:selected').val();
            var app=$(this).val();
            var conn=$('#req_destConn option:selected').val().split(":")[0];
            // Grab the tiers
            
            url='/AppDChangeManager/ManageItems?type=tiers&applicationId='+app+'&connectionId='+conn;
            
            $.getJSON(url,function(data, textStatus, jqXHR){
                
                for(var i=0; i < data.length;i++){
                    var item=data[i].split("|")[1];
                    $('#req_destTier').append('<option value="'+item+'">'+item+'</option>');  
                }
                
            }).fail(function(jqXHR, textStatus, errorThrown){
                    alert('Error grabbing data ' + errorThrown);
                    return;
            });
            
        }
    });
    
    $('#req_srcTier').change(function(){
        if($(this).val() !== '_default_'){ 
            var type=$('#req_type option:selected').val();
            var tier=$(this).val();
            var app=$('#req_srcApp option:selected').val();
            var conn=$('#req_srcConn option:selected').val().split(":")[0];
            // Grab the tiers
            
            url='/AppDChangeManager/ManageItems?type='+type+"&applicationId="+app+'&connectionId='+conn+'&tierId='+tier;
            
            $.getJSON(url,function(data, textStatus, jqXHR){
                
                for(var i=0; i < data.length;i++){
                    var item=data[i];
                    $('#req_srcItemName').append('<option value="'+item+'">'+item+'</option>');  
                }

            }).fail(function(jqXHR, textStatus, errorThrown){
                    alert('Error grabbing data ' + errorThrown);
                    return;
            });
            
        }
    });
    
    
};

/*
*  This is going to populate the request form, connection information.
*/
POPULATE.req_conns=function(){
    var src=$('#req_srcConn');
    var dest=$('#req_destConn');
    var url='/AppDChangeManager/ConfigServlet';
    
    $.getJSON(url,function(data, textStatus, jqXHR){

                for(var i=0; i < data.conns.length;i++){
                    var item = data.conns[i];
                    // console.log(item);
                    src.append('<option value="'+item.id+':'+item.displayName+'">'
                            +item.id+':'+item.displayName+'</option>');

                    dest.append('<option value="'+item.id+':'+item.displayName+'">'
                            +item.id+':'+item.displayName+'</option>');
                }

    }).fail(function(jqXHR, textStatus, errorThrown){
            alert('Error grabbing connection data:' + errorThrown);
            return;
    });
};

/*
*  This is going to add a change to the database.
*/
POPULATE.chg_dd = function(){
    var chg_dialog=$('#dialog-form-chg_a');
    var _actionButton = $('#create_change');
    
    chg_dialog.dialog({
        autoOpen:false,
        height: 300,
        width: 350,
        modal: true,
        buttons: {
            "Create Change": POPULATE.chg_a,
            Cancel:function(){
                chg_dialog.dialog("close");
            }
        },
        close: function(){
            form[0].reset();
        } 
    });
    
    var form = chg_dialog.find("form").on("submit",function(event){
        event.preventDefault();
        POPULATE.chg_a();    
    });
    
   $('#create_change').button().on("click",function(event){ 
       chg_dialog.dialog("open");
   });
};

/*
* This is going to control the request form. 
*/
POPULATE.req_dd = function() {  
        var req_dialog=$('#dialog-form-req');
        req_dialog.dialog({
            autoOpen: false,
            height: 400,
            width: 450,
            modal: true,
            buttons: {
                "Add Request": POPULATE.req_a,
                Cancel: function(){
                    POPULATE.reset_req_form();
                    req_dialog.dialog("close");
                }
            },
            close: function(){
                form[0].reset();
            }
        });
    
        var form = req_dialog.find("form").on("submit",function(event){
            event.preventDefault();
            POPULATE.req_a();
        });
    
        // When the user clicks on the create-request button, the dialog will
        // become visible.
        $('#create_request').button().on("click",function(){
            //console.log("--create-conf clicked");
            POPULATE.req_conns();
            POPULATE.req_actions();
            req_dialog.dialog("open");
        });

  };

$(document).ready(function(){
    CONFIGS.load();
    $('#change_section_u').tabs();
});