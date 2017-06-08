
        var swfu;
		var UPLOADURL;
		var FLASHURL;
		var HIGH;
		var WIDTH;
		var GAMEID;
		var FILESIZE
		var FILETYPE;
		var TYPESDESCRIPTION;
		var UPLOADLIMIT;
		var QUEUELIMIT;
		var DEBUG;
		var UPLOADTARGET;
		var SAVEELEMENT;
		
		var BUTTON;
		
				
		function Initializes()
		{

//		    window.onload = function () {		  
			swfus = new SWFUpload({
				// Backend Settings
				upload_url: UPLOADURL,	// Relative to the SWF file
                post_params : {
                },

				// File Upload Settings
				file_size_limit : FILESIZE,	// 2MB
				file_types : FILETYPE,
				file_types_description : TYPESDESCRIPTION,
				file_upload_limit : UPLOADLIMIT,    // Zero means unlimited
				file_queue_limit : QUEUELIMIT,

				// Event Handler Settings - these functions as defined in Handlers.js
				//  The handlers are not part of SWFUpload but are part of my website and control how
				//  my website reacts to the SWFUpload events.
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,
				
				// Button settings
				button_image_url: "../static/images/XPButtonNoText_106x30.gif",	// Relative to the SWF file
				button_placeholder_id : "btnUploads",
				button_width: 106,
				button_height: 30,
				button_text: '<span class="button"></span>',
				button_text_style: '.button {}',
				button_text_top_padding: 1,
				button_text_left_padding: 5,

				// Flash Settings
				flash_url : FLASHURL,	// Relative to this file

				custom_settings : {
					upload_target : UPLOADTARGET,
					save_element : SAVEELEMENT
				},

				// Debug Settings
				debug: DEBUG
			    });
//			}
		}
		
		
