Mnzn.f7 = {};
$.ajaxSettings.traditional=true;
Mnzn.F7Multi = function(url, name, options) {
	options = options || {};
	var idHidden = $("#" + name);
	var numberHidden = $("#" + name + "Number");
	var nameHidden = $("#" + name + "Name");
	var numberDisplay = $("#" + name + "NumberDisplay");
	var nameDisplay = $("#" + name + "NameDisplay");
	var f7Button = $("#" + name + "Button");
	var dialogDiv = null;
	var ids = [];
	var numbers = [];
	var names = [];
	var init = function() {
		idHidden.find("input[name='"+name+"']").each(function() {
			ids[ids.length] = $(this).val();
		});
		numberHidden.find("input[name='"+name+"Number']").each(function() {
			numbers[numbers.length] = $(this).val();
			numberDisplay.val(numberDisplay.val()+","+$(this).val());
		});
		nameHidden.find("input[name='"+name+"Name']").each(function() {
			names[names.length] = $(this).val();
			nameDisplay.val(nameDisplay.val()+","+$(this).val());
		});
		if(numberDisplay.length>0) {
			var numberValue = numberDisplay.val();
			var numberValueLength = numberValue.length;
			if(numberValueLength>0) {
				numberDisplay.val(numberValue.substring(1,numberValueLength));
			}			
		}
		if(nameDisplay.length>0) {
			var nameValue = nameDisplay.val();
			var nameValueLength = nameValue.length;
			if(nameValueLength>0) {
				nameDisplay.val(nameValue.substring(1,nameValueLength));
			}			
		}	
	};
	init();

	var destroy = function() {
		dialogDiv.dialog("destroy");
		dialogDiv.empty();
	};
	var applayValue = function() {
		idHidden.empty();
		numberHidden.empty();
		nameHidden.empty();
		numberDisplay.val("");
		nameDisplay.val("");
		ids = [];
		numbers = [];
		names = [];
		$("input[name='f7_ids']").each(function() {
			ids[ids.length] = $(this).val();
			idHidden.append("<input type='hidden' name='"+name+"' value='"+$(this).val()+"'/>");
		});
		$("input[name='f7_numbers']").each(function() {
			numbers[numbers.length] = $(this).val();
			numberHidden.append("<input type='hidden' name='"+name+"Number' value='"+$(this).val()+"'/>");
			numberDisplay.val(numberDisplay.val()+","+$(this).val());
		});
		$("input[name='f7_names']").each(function() {
			names[names.length] = $(this).val();
			nameHidden.append("<input type='hidden' name='"+name+"Name' value='"+$(this).val()+"'/>");
			nameDisplay.val(nameDisplay.val()+","+$(this).val());
		});
		if(numberDisplay.length>0) {
			var numberValue = numberDisplay.val();
			var numberValueLength = numberValue.length;
			if(numberValueLength>0) {
				numberDisplay.val(numberValue.substring(1,numberValueLength));
			}
		}
		if(nameDisplay.length>0) {
			var nameValue = nameDisplay.val();
			var nameValueLength = nameValue.length;
			if(nameValueLength>0) {
				nameDisplay.val(nameValue.substring(1,nameValueLength));
			}			
		}
		nameDisplay.focus();
		numberDisplay.focus();
	};

	var settings = {
		title : "F7 Multi Select",
		width : 550,
		height : 450,
		modal : true,
		position : {
			my : "center top",
			at : "center top",
			of : window
		},
		close : destroy,
		buttons : [ {
			id : "f7_ok",
			text : "OK",
			click : function() {
				applayValue();
				destroy();
				if(options.callbacks && typeof options.callbacks.ok=="function") {
					options.callbacks.ok(ids,numbers,names);
				}
			}
		}, {
			text : "Cancel",
			click : function() {
				destroy();
				if(options.callbacks && typeof options.callbacks.cancel=="function") {
					options.callbacks.cancel(ids,numbers,names);
				}
			}
		} ]
	};
	$.extend(settings, options.settings);

	f7Button.click(function() {
		var params = {"d":new Date()*1};
		params.ids = [];
		$("input[name='"+name+"']").each(function() {
			params.ids[params.ids.length] = $(this).val();
		});
		$.extend(params,options.params);
		if (!dialogDiv) {
			dialogDiv = $("<div style='display:none;'>").appendTo(document.body);
		}
		//$.param(params,true)
		dialogDiv.load(url, params, function() {
			dialogDiv.dialog(settings);
		});
	});
};
Mnzn.F7Single = function(url, name, names, focus, options) {
	options = options || {};
	var idField = $("#" + name);
	var f7Button = $("#" + name + "Button");
	
//	var numberHidden = $("#" + name + "Number");
//	var nameHidden = $("#" + name + "Name");
//	var numberDisplay = $("#" + name + "NumberDisplay");
//	var nameDisplay = $("#" + name + "NameDisplay");
	
	var fields = [];
	for(var i = 0,len=names.length;i<len;i++) {
		fields[i] = $("#" + name + names[i]);
	}
	var idValue;
	var values = [];
	
	var dialogDiv = null;

	var destroy = function() {
		dialogDiv.dialog("destroy");
		dialogDiv.empty();
	};
	var applayValue = function() {
		idValue = $("#f7_id").val();
		idField.val(idValue);
		for(var i = 0,len=names.length;i<len;i++) {
			values[i] = $("#f7_id_"+names[i]).val();
			fields[i].val(values[i]);
		}
//		var number = $("#f7_number").val();
//		var name = $("#f7_name").val();
//		numberHidden.val(number);
//		numberDisplay.val(number);
//		nameHidden.val(name);
//		nameDisplay.val(name);
	};
	var doFocus = function() {
		if(focus) {
			$("#"+focus).focus();
		}
//		if(numberDisplay.length>0) {
//			numberDisplay.focus();
//		} else if(nameDisplay.length>0) {
//			nameDisplay.focus();
//		} else if(numberHidden.length>0) {
//			numberHidden.focus();
//		}
	};

	var settings = {
		title : "F7 Select",
		width : 350,
		height : 450,
		modal : true,
		position : {
			my : "center top",
			at : "center top",
			of : window
		},
		close : destroy,
		buttons : [ {
			id : "f7_ok",
			text : "OK",
			click : function() {
				applayValue();
				destroy();
				if(options.callbacks && typeof options.callbacks.ok=="function") {
					options.callbacks.ok(idValue,values);
				}
				doFocus();
			}
		}, {
			text : "Cancel",
			click : function() {
				destroy();
				if(options.callbacks && typeof options.callbacks.cancel=="function") {
					options.callbacks.cancel();
				}
				doFocus();
			}
		} ]
	};
	$.extend(settings, options.settings);

	f7Button.click(function() {
		var params = {"d":new Date()*1};
		if(idField.val()!="") {
			params.id = idField.val();
		}
		$.extend(params, options.params);
		if (!dialogDiv) {
			dialogDiv = $("<div style='display:none;'>").appendTo(document.body);
		}
		dialogDiv.load(url + "?" + $.param(params), function() {
			dialogDiv.dialog(settings);
		});
	});
};

Mnzn.f7.perm = function(name, options) {
	options = options || {};
	var url = CTX + CMSCP + "/core/role/choose_perm_tree.do";
	var settings = {title:"Perm Select",width:350,height:450};
	options.settings = $.extend(settings, options.settings);
	var names = ["Number"];
	var f7 = Mnzn.F7Single(url,name,names,name,options);
};





