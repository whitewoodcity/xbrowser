# XBrowser

A super cool browser and not just a browser, the XBrowser could translate the json string into components, and also use different script languages e.g. Javascript, Ruby, Groovy and any other script languages to manipulate images and musics, thus the producers could make a dynamic web applications just like traditional web browser and also make some interesting games like other game engines.

Here is an example:
The client sends a HTTP request to an HTTP server which replies a xmlv file, just like traditional html or other markup languages:
```xml
<xmlv>
	<preload>
test.png=http://d.lanrentuku.com/down/png/0905/creature/Dolphin_256x256.png;
	</preload>
    <script type="js">
	timer = app.timer
	x = 0
	var img = preload.get('test.png')
    
	timer.action = function(now){
		x= x+1
		canvas.clear()
		canvas.image(img,x,0)
	}
	timer.start()
    </script>
</xmlv>
```
And the XBrowser received the xmlv file then render the page based on the xmlv file, here is what we get:
<img width="960" alt="1" src="https://user-images.githubusercontent.com/5525436/31162685-7ebdc1da-a8a4-11e7-9eac-8890e3146a4d.png">
The dolphin image will move to the right since in the script the x coordinate will increase gradually.
So cool right?

Please note the script could be written in other script langues e.g. Ruby, and here is the ruby translation of previous js script.
```xml
    <script type="ruby">
	timer = $app.timer
	x = 0
	img = $preload.get('test.png')
	canvas = $canvas
    
	timer.action = lambda{|now|
		x= x+1
		canvas.clear()
		canvas.image(img,x,0)
	}
	timer.start()
    </script>
```
also in groovy:
```xml
    <script type="groovy">
	timer = app.timer
	x = 0
	img = preload.get('test.png')
    
	timer.action = { now ->
		x= x+1
		canvas.clear()
		canvas.image(img,x,0)
	}
	timer.start()
    </script>
```
