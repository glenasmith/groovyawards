/*
Copyright (c) 2008 DeenSoft.com - You are free to use this file wherever you wish but please do let us know at blog.deensoft.com 
so that we can showcase it. You can even post your bugs on our blog and we will fix them asap.

This code is being release under open-terms. Use at your own risk. Give us feedback. Help us fix bugs and implement new features. :)

contact@deensoft.com

------------------------------------------------------------------------------------------------------------------------------

Class: ProtoFlow

Description: 
ProtoFlow v0.6 is a very early preview release that simulates Apples CoverFlow effect using Prototype/Scriptaculous lib.

ChangeLog: 

March 19, 2008
* Fixed issues with IE
* Fixed major bug with images and links...


Initial:

* Added Reflection 
* Fixed Captions


(code)
var myFlow = new ProtoFlow(
					$('myElem'), 
					{
						captions: 'captionsList'
					}
);
(end)


*/
var ProtoFlow = Class.create({
  /*
	Function: initialize

	Description: Constructor for ProtoFlow Class. 

	Parameters:

	elem {Mixed} the HTML object that ProtoFlow is initialize from. This can be either an HTML Object or ID to an HTML object
	opt {Object} config Object
  */
  initialize: function(elem, opt) {
	opt = opt || {}
	this.options = {
		startIndex: 2,
		interval: 60,
		slider: true, 
		flex: 110,
		captions: false,
		autoplay: false,
		autoplayInterval: 5,
		useReflection: false,
		enableOnClickScroll: false
	},
	Object.extend(this.options, opt);
	this.useCaptions = this.options.captions; //initially we don't wanna use captions unless turned on?

	
	this.elem = $(elem);
	if(!this.elem) return;

	//console.log(this.elem.select('img'));
	this.elem.setStyle({overflow: "hidden", position: "relative"});
	this.imageStack = this.elem.select('img'); //this.elem.childElements();

	if(this.options.captions != false)
	{
		this.captions = this.imageStack.pluck("alt");
		//this.captions = this.captionsHolder.childElements();		
		//this.captions.each( ( function(elem, i){this.captions[i] = elem.innerHTML;} ).bind(this));
		this.captionsCount = this.captions.size();
	}
	
	if(this.options.useReflection)
	{
		this.imageStack.each(function(elem){Reflection.add(elem, { opacity: 2/3 });}.bind(this));
		this.stack = this.elem.childElements();
	}
	else
	{
		this.stack = this.imageStack;
	}
	this.stackCount = (this.stack).size();



	if(this.useCaptions)
	{
		this.captionHolder = new Element('div');
		this.captionHolder.className = "captionHolder";
		this.captionHolder.setStyle({
			width: "100%",
			textAlign: "center",
			position: 'absolute',
			left: "0px",
			top: (Element.getHeight(this.elem) - 80) + "px"
		});
		this.elem.appendChild(this.captionHolder);
	}

	
	
	this.currPos = this.options.startIndex - 1;
	this.currIndex = this.currPos;
	/* slider */
	if(this.options.slider)
	{
		this.sliderContainer = new Element('div');
		this.sliderContainer.setStyle({
			width: '200px',
			height: '10px',
			position: 'absolute',
			top: (Element.getHeight(this.elem) - 30) + "px",
			left: (Element.getWidth(this.elem) / 2  - (137/2)) + "px"
		});
		
		this.sliderTrack = new Element('div');
		this.sliderTrack.className = "sliderTrack";

		this.sliderHandle = new Element('div');
		this.sliderHandle.className = "sliderHandle";
		
		this.sliderTrack.appendChild(this.sliderHandle);
		this.sliderContainer.appendChild(this.sliderTrack);

		this.elem.appendChild(this.sliderContainer);

		this.slider = new Control.Slider(this.sliderHandle, this.sliderTrack, {
			range: $R(0, this.getStackCount() - 1),
			sliderValue: this.getCurrentPos(), // won't work if set to 0 due to a bug(?) in script.aculo.us
			onSlide: this.handleSlider.bind(this),
			onChange: this.handleSlider.bind(this)
		});
	}



	this.timer = 0;
	 
	
	/* sets up click listener on all the elements in the stack */
	this.stack.each(
		function(elem) {
			elem.identify();
			//console.log(elem);
			//elem.observe("mouseover", function(){this.setStyle({"border": "2px solid yellow;"});});
			if(this.options.enableOnClickScroll) elem.observe('click', this.handleClick.bind(this));
		}.bind(this)
	);

	if(this.options.enableOnClickScroll) //lets go through all the <a> tags and disable them..
	{
		this.disableLinks();
	}
	
	this.goTo(this.currPos);
	
	this.autoplayer = null;
	if(this.options.autoplay)
	{
		this.autoplayer = new PeriodicalExecuter(this.autoPlay.bind(this), this.options.autoplayInterval);
	}

	Event.observe(window, 'resize', this.handleWindowResize.bind(this));
  },
  disableLinks: function()
  {
	  var allLinks = this.elem.select("a");
	  //console.log(allLinks);
	  allLinks.each(function(a) {a.setAttribute('href', '#');});
  },
  autoPlay: function(){
	  if((this.currIndex + 2) > this.stackCount)
	  {
		  this.currIndex = 0;
	  }
	  this.currIndex = this.currIndex + 1
	  this.goTo(this.currIndex);
  },
  handleWindowResize: function(event)
  {
  },
  handleWheel: function(event)
  {
	v = Event.wheel(event);
	this.goTo(this.currIndex + v);
	this.slider.setValue(this.currIndex + v);
  },
  handleSliderChange: function(v) {
	
	this.goTo(v);
  },
  handleSlider: function(v){
	if(v) this.goTo(v);
  },
  handleClick: function(e)
  {
	elem = Event.element(e);
	
	if(this.options.useReflection) { elem = elem.up('div'); }
	//console.log(elem);
	//if(!elem.getAttribute("index")) { elem = elem.select('img'); }
	v = elem.getAttribute("index");

	this.currIndex = v;
	this.goTo(v);
	this.updateSlider(v);
	//Event.stop(e);
  },
  getCurrentPos: function()
  {
	return this.currPos;
  },
  goTo: function(index)
  {
	this.slideTo(index * this.options.flex * -1);
	//this.currPos = Math.round(index);
	if(this.useCaptions) {
		this.captionHolder.innerHTML = this.captions[Math.round(index)];
	}
  },
  updateSlider: function(index)
  {
	if(this.options.slider) this.slider.setValue(index);
  },
  step: function()
  {
	if(this.target < this.currPos - 1 || this.target > this.currPos + 1)
	{
		this.moveTo(this.currPos + (this.target-this.currPos)/5);
		window.setTimeout(this.step.bind(this), this.options.interval);
		this.timer = 1;
	}
	else
	{
		this.timer = 0;

	}
  },
  slideTo: function(x)
  {
	this.target = x;
	
	if(this.timer == 0)
	{
		window.setTimeout(this.step.bind(this), this.options.interval);
		this.timer = 1;
	}

	
  },

  moveTo: function(currentPos)
  {
	 var x = currentPos;
	 this.currPos = currentPos;
	 var width = Element.getWidth(this.elem);
	 var height = Element.getHeight(this.elem);

	 var top = this.elem.offsetTop;
	 var size = width * 0.5;
	 var biggest = height;
	 var zIndex = this.stackCount;
	 this.stack.each(
					function(elem, index) {
						
						Element.absolutize(elem);
						elem.setAttribute("index", index);
						
						elem.down(1).setAttribute('index', index);
						var z = Math.sqrt(10000 + x * x * 1) + 100;
						var xs = x / z * size + size;
						elem.setStyle({
							left: (xs - 40 / z * biggest) + "px",
							top: (30 / z * size + 0) + "px",
							width:(100 / z * biggest) + "px",
							height: (110.25 / z * biggest) + "px",
							textAlign: "center"
						});
						//console.log(elem);
						elem.style.zIndex = zIndex;
						
						if(x < 0)
							zIndex++;
						else 
							zIndex--;
						x += this.options.flex;

					}.bind(this)
				);
  },
  /*
	Function: getStackCount

	Description: returns the count of elements in the stack 

	Parameters:

	None
  */
  getStackCount: function()
  {
	return this.stackCount;
  }
});