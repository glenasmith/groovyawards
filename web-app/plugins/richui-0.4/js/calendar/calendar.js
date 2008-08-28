/*
 * This code is based on the following blog entry http://blog.davglass.com/files/yui/cal2/ 
 * with some modifications.
 * 
 * 
 * 
 */
function Calendar() {
	
	var cal;
	var displayContainer;
	var format;
	var locale;
	var month;
	var selectedDates;
	var selectHandler;
	var deselectHandler;
	
	this.setDisplayContainer = function(container){
    	displayContainer = container;
    }
	
	this.setFormat = function(dateFormat){
		format = dateFormat;
	}
	
	this.setLocale = function(dateLocale){
		locale = dateLocale;
	}
	
	this.setMonth = function(date){
		month = date;
	}
	
	this.setSelectedDates = function(dates){
		selectedDates = dates;
	}
	
	this.setSelectHandler = function(handler){
		selectHandler = handler;
	}
	
	this.setDeselectHandler = function(handler){
		deselectHandler = handler;
	}
	
	this.init = function() {
		cal = new YAHOO.widget.Calendar("cal", displayContainer);

		//Multiselect
		cal.cfg.setProperty("MULTI_SELECT", true);
		
		//Navigator
		//Navigation configuration
        var navConfig = {
			strings : {
				month: "Monat auswaehlen",
			    year: "Enter Year",
			    submit: "OK",
			    cancel: "Cancel",
			    invalidYear: "Please enter a valid year"
			},
			monthFormat: YAHOO.widget.Calendar.LONG,
			initialFocus: "year"
		};			
		cal.cfg.setProperty("NAVIGATOR", navConfig);

		if(format == "dd.MM.yyyy"){
			//Correct formats for Germany: dd.MM.yyyy, dd.MM, MM.yyyy
			cal.cfg.setProperty("DATE_FIELD_DELIMITER", ".");
			cal.cfg.setProperty("MDY_DAY_POSITION", 1);
			cal.cfg.setProperty("MDY_MONTH_POSITION", 2);
			cal.cfg.setProperty("MDY_YEAR_POSITION", 3);
			cal.cfg.setProperty("MD_DAY_POSITION", 1);
			cal.cfg.setProperty("MD_MONTH_POSITION", 2);
			
			if(locale == "de"){
				//Date labels for German locale
				cal.cfg.setProperty("MONTHS_SHORT",   ["Jan", "Feb", "M\u00E4r", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"]);
				cal.cfg.setProperty("MONTHS_LONG",    ["Januar", "Februar", "M\u00E4rz", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"]);
				cal.cfg.setProperty("WEEKDAYS_1CHAR", ["S", "M", "D", "M", "D", "F", "S"]);
				cal.cfg.setProperty("WEEKDAYS_SHORT", ["So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"]);
				cal.cfg.setProperty("WEEKDAYS_MEDIUM",["Son", "Mon", "Die", "Mit", "Don", "Fre", "Sam"]);
				cal.cfg.setProperty("WEEKDAYS_LONG",  ["Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"]);
			}
		}
    	
		//Select event handling
		selectHandler = function(type,args,obj) {
			var selected = args[0];
			//alert("Selected: " + this._toDate(selected[0]));
			
			var date = args[0] + "";
			date = date.split(",");
			//cal.deselect(date[2] + "." + date[1] + "." + date[0]);
			//cal.render();

			
			new Ajax.Updater('bla','/pim/calendar/select',{asynchronous:true,evalScripts:true,parameters:'year=' + date[0] + '&month=' + date[1] + '&day=' + date[2]});
			
			//alert("Hallo");
			
			//document.location.href = "about:blank";
		};
		
		//Deselect event handling
		deselectHandler = function(type, args, obj) {
			var selected = args[0];
			//alert("Deselected: " + selected); //this._toDate(selected[0]));
			//var date = args.split(",");
			var date = args[0] + "";
			date = date.split(",");
			
			//alrt("Hallo: " + date[2] + "." + date[1] + "." + date[0]);
			new Ajax.Updater('bla','/pim/calendar/deselect',{asynchronous:true,evalScripts:true,parameters:'year=' + date[0] + '&month=' + date[1] + '&day=' + date[2]});
			
			//cal.select(date[2] + "." + date[1] + "." + date[0]);
			//cal.select(args[0]);
			cal.render();
		};

		
		if(selectHandler != null){
			cal.selectEvent.subscribe(selectHandler, cal, true);	
		}
		
		if(deselectHandler != null){
			cal.deselectEvent.subscribe(deselectHandler, cal, true);			
		}		
		
			
		//Select month
		if(month == null){
			month = new Date();
		}
		cal.cfg.setProperty("PAGEDATE", month);
			
		//Selected dates
		selectedDates = "20.02.2008,22.02.2008,25.02.2008-28.02.2008";
		cal.cfg.setProperty("selected", selectedDates, false);
			
		cal.render();
	}

}