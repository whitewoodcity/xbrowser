# File

XBrowser uses **XMLV** a XML extension as its layout standard. All display elements should be included in the XMLV file. Once the resource has been retrieved the web browser will display it. A XMLV file is pretty much similar to HTML file used by normal browsers. It included a root element xmlv and title attribute, following is an example of XMLV file:

```xml
<xmlv title="Hello XMLV"/>
```

There are two types of xmlv elements:
1) [Noun elements](#noun) 
2) [Verb elements](#verb)

## <a name="noun"></a>Noun Elements

Noun elements are used to identify and describe page elements.

There are three types of noun elements:
1) [JSON](#json) 
2) [Preload](#preload)
2) [CSS](#css)

## <a name="json"></a>JSON

JSON element describes components placed on the page. 

```xml
<json>
{"type":"button","text":"i am a button"}
</json>
```
<img src="https://user-images.githubusercontent.com/5525436/31708141-c9d4901c-b3b3-11e7-8d78-c359da3ed3e9.png">
For more about JSON file format please refer to JSON website: http://json.org

There are only 3 types of JSON root element: JsonObject, JsonArray and null:

1) If the root element is a JsonObject, the page will create components based on the information provided and place at the central middle of the page.
```xml
<json>
{"type":"button","text":"i am a button"}
</json>
```
<img src="https://user-images.githubusercontent.com/5525436/31709531-e88194c0-b3b7-11e7-8f52-51543c2b42c1.png">
2) If the root element is a JsonArray, the page will create a two-dimensional space and render the components based on the JsonObject within the JsonArray, the component coordinates can be specified with x,y layout.

```xml
<json>
[{"type":"button","text":"i am a button","x":100,"y":100}]
</json>
```
<img src="https://user-images.githubusercontent.com/5525436/31709530-e82dd20e-b3b7-11e7-8bc7-49a250c79b40.png">
3) If the root element is empty, the page will by default place a canvas with id:canvas.

```xml
<json/>
<script>
canvas.text("i am a canvas",100,100)
</script>
```
<img src="https://user-images.githubusercontent.com/5525436/31709529-e7d17c84-b3b7-11e7-87a6-cbedacb1858e.png">

Component List

Component|type|parent|string properties|number properties|bool properties
:---:|:---:|:---:|:---|:---|:---
Node|-|-|id|rotate,opacity|disable,visible
Cavans|canvas|Node|-|width,height|-
Control|-|Node|name,value|x,y,width,height|-
Chart|-|Node|title,titleside,legendside|x,y,width,height|-
View|-|Node|-|x,y,width,height|-
ImageView|img,image,imageview|View|url,image|-|-
Label|label|Control|text|-|-
Button|button|Control|text|-|-
Hyperlink|hyperlink|Control|text|-|-
TextField|textfield|Control|text|-|-

Component|type|parent|string properties|JsonArray[String] properties
:---:|:---:|:---:|:---|:---
ChoiceBox|choicebox|Control|value|item,items|-
```json
{
"type":"choicebox",
"id":"choicebox001",
"x":100,"y":250,
"items":["USA","Japan","China"],
"value":"China"
}
```

Component|type|parent|string properties|JsonArray[String] properties|bool properties
:---:|:---:|:---:|:---|:---|:---
TableView|table,tableview|Control|-|header,headers,column,<br/>columns,value,values|editable

```json
{
"type":"table","id":"table001",
"x":0,"y":0,
"headers":["USA",{"Asia":["Japan","China"]}],
"values":[[1,2,3],{"USA":3,"China":2,"Japan":1}]
}
```
<img src="https://user-images.githubusercontent.com/5525436/31762602-4cfd1b80-b481-11e7-9bc9-75fc3b740417.png">

```json
{
	"type": "table", "id": "table001",
	"x": 0,"y": 0,
	"headers": [
		"USA",
		{"Asia": [{"name": "Japan","type": "checkbox"}, 
			  {"name": "China","type": "combobox","items": ["1", "2"]},
			  {"name": "Korea","type": "choicebox","items": ["3", "4"]}]}],
	"values": [[1, true, 1, 3], 
		   {"USA": 3,"Japan": false,"Korea": 3,"China": 2}
	]
}
```
<img src="https://user-images.githubusercontent.com/5525436/31762603-4e49215a-b481-11e7-87c6-797d48874084.png">

Component|type|parent|string properties|number properties|JsonArray[String] properties
:---:|:---:|:---:|:---|:---|:---
Form|form|Control|text,action,method|-|children

```xml
<xmlv>
<json>
[
{"type":"label","id":"label001","name":"label001","x":100,"y":50,"text":"Label"},
{"type":"textfield","id":"textfield001","name":"textfield","x":200,"y":50,"text":"Textfield"},
{"type":"hyperlink","id":"hyperlink001","x":400,"y":50,"text":"Hyperlink"},
{"type":"button","id":"button001","x":500,"y":50,"text":"Button"},
{"type":"table","id":"table001","name":"table","x":600,"y":50,"header":["col"],"value":[["001"]],"width":100},
{"type":"form","id":"form001","children":["textfield001","table001"],"method":"post","action":"www.mycom.com/postform"}
]
</json>
<script type="groovy">
button001.action = { event ->
	form001.send()
}
</script>
</xmlv>
```
<img src="https://user-images.githubusercontent.com/5525436/31774833-7b702658-b4ac-11e7-8fbc-c35c73c8e96f.png">
The form will be sent with following http request body in JSON format:

```json
{"textfield":"Textfield","table":[{"col":"001"}]}
```

Component|type|parent|string properties|number properties|JsonArray[{String:Number}] properties
:---:|:---:|:---:|:---|:---|:---
PieChart|piechart|Chart|-|-|data

```json
{
	"type":"piechart","id":"piechart001",
	"title":"Pie Chart","titleside":"Bottom","legendside":"top",
	"data":{"USA":10,"Japan":10,"China":10}
}
```

<img src="https://user-images.githubusercontent.com/5525436/31805775-19b844a4-b529-11e7-82c1-938c381042ba.png">

Component|type|parent|string properties|JsonArray[String/Number] properties|JsonObject{String:[Number]} properties
:---:|:---:|:---:|:---|:---|:---
XYChart|-|Chart|xlabel,ylabel|xaxis|data
LineChart|linechart|XYChart|-|-|-
BarChart|barchart|XYChart|-|-|-
ScatterChart|scatterchart|XYChart|-|-|-
AreaChart|areachart|XYChart|-|-|-

```json
{
	"type":"linechart","id":"linechart001",
	"title":"Line Chart","titleside":"left","legendside":"right",
	"xaxis":["Jan","Feb","Mar","Apr","May"],
	"data":{"USA":[10,20,30,40,50],"Japan":[50,40,30,20,10],"China":[30,20,10,20,30]}
}
```

<img src="https://user-images.githubusercontent.com/5525436/31811261-8126b290-b544-11e7-91bf-e6542629ca93.png">

Component|type|parent|JsonObject{String:[{"x":Number,"y":Number,"z":Number}]} properties
:---:|:---:|:---:|:---
BubbleChart|bubblechart|XYChart|data

```json
{
	"type":"bubblechart","id":"bubblechart001",
	"title":"Bubble Chart","titleside":"left","legendside":"right",
	"data":{"USA":[{"x":10,"y":10,"z":3}],
		"Japan":[{"x":20,"y":10,"z":1},{"x":15,"y":15,"z":2}],
		"China":[{"x":10,"y":20,"z":2},{"x":20,"y":20,"z":1}]}
}
```

<img src="https://user-images.githubusercontent.com/5525436/31811829-945f61de-b546-11e7-9b76-4b761c96eb8d.png">

## <a name="preload"></a>Preload

Preload element describes resources to be loaded before renderring the page. Only three types of resources are supported:

wav|mp3,mp4|png,jpg,gif,bmp etc
:---:|:---:|:---:
[AudioClip](http://download.java.net/java/jdk9/jfxdocs/javafx/scene/media/AudioClip.html)|[Media](http://download.java.net/java/jdk9/jfxdocs/javafx/scene/media/Media.html)|[Image](http://download.java.net/java/jdk9/jfxdocs/javafx/scene/image/Image.html)

The element content is defined as several key=value pairs separated by semicolon; 

```xml
<preload>
image001=http://w2v4.com/static/image001.png;
image002=http://w2v4.com/static/image002.png;
</preload>
```
The browser will download and cache resources beforehand.
<img src="https://user-images.githubusercontent.com/5525436/31669158-ec54025c-b319-11e7-8c5d-b185c5e20bb8.png"/>

The resources could be retrieved in the [verb elements](#verb) by its key. 
```xml
<script type="groovy">
Image image = image001
//or
Image image = preload.get('image001')
</script>
```
By default the resources is cached, users could set new:/renew:/New:/Renew: prefix to force the browser download the resources no mather the resources is cached or not.
```xml
<preload>
renew:image001=http://w2v4.com/static/image001.png;Renew:image002=http://w2v4.com/static/image002.png
</preload>
```

## <a name="css"></a>CSS

CSS is a style sheet language used for describing the presentation of a page elements usually written in JSON, but also apply to pae elements placed by the browser.

```xml
    <css>
.progress-bar > .bar {
    -fx-background-color: linear-gradient(
        from 0px .75em to .75em 0px,
        repeat,
        -fx-accent 0%,
        -fx-accent 49%,
        derive(-fx-accent, 30%) 50%,
        derive(-fx-accent, 30%) 99%
    );
}
    </css>
```

<img src="https://user-images.githubusercontent.com/5525436/31812615-2b06a12c-b549-11e7-8697-2dd6592b48b9.png">

If a component is associated with an id then it could be easily styled in css with the same id name:

```xml
    <json>
    [{
        "id":"button001",
        "type":"button",
	"text":"button001",
	"x":100,"y":100
     },{
        "id":"button002",
        "type":"button",
        "text":"button002",
	"x":200,"y":100
    }]
    </json>
    <css>
        #button001 {-fx-background-color : red}
        #button002 {-fx-background-color : green}
    </css>
```

For more about CSS file format please refer to [JavaFX CSS Reference Guide](http://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html).

## <a name="verb">Verb Elements

Verb elements are used to describe page element actions.

There are two types of noun elements:
1) [Script](#script) 
2) [Class](#class)

## <a name="script"></a>Script

Text in script element is an interpreted(rather than compiled) piece of programming language that supports scripts: programs written for XBrowser run-time environment that automate the execution of tasks. It is used to make pages interactive and provide online programs, including animations and video games. XBrowser currently three scripting languages: [Javascript](https://developer.mozilla.org/en-US/docs/Web/JavaScript), [Ruby](http://jruby.org/) and [Groovy](http://groovy-lang.org/). The script lanugae type could be specified in the type attribute.

```xml
<xmlv>
    	<json/>
	<script type="javascript">
		x = 0
		timer = app.timer
		timer.action = function (now) {
			canvas.clear()
			x++
			canvas.text('hello world',x,x)
		}
		timer.start()
	</script>
</xmlv>
```

By using scripts, developers could manipulate the components defined in the [JSON](#json) tag element.

```xml
<xmlv>
    	<json>
		[{"type":"button","id":"button001","x":10,"y":10,"text":"i am a button"}]
	</json>
	<script type="javascript">
		button001.action = function(event){//button001 is the component id, which would be used as the object reference in scripts
			button001.x = button001.x + 10
		}
	</script>
</xmlv>
```

When user click on the button, the button moves right for 10 points.

<img width="362" alt="screen shot 2017-10-20 at 6 50 48 pm" src="https://user-images.githubusercontent.com/5525436/31817663-a90779be-b55a-11e7-8414-20a79bd46420.png">

Component|method name|parameters|return value|comment
:---:|:---:|:---|:---|:---
Canvas|clear|-|void|Clear whole canvas.
Canvas|text|String text|void|Draws a text at 0, 0 position.
Canvas|text|String text, Number x, y|void|Draws a text at x, y position.
Canvas|text|String text, Number x, y, maxwidth|void|Draws a text at x, y position with maxwidth.
Canvas|image|Image image, Number x, y|void|Draws an image at the given x, y position for the upper left of the image.
Canvas|image|Image image, Number x, y, width, height|void|Draws an image into the given destination rectangle of the canvas.
Canvas|image|Image image, Number sx, sy, swidth, sheight, dx, dy, dwidth, dheight|void|Draws the specified **source** rectangle of the given image to the given **destination** rectangle of the Canvas.
Canvas|line|Number sx,sy,ex,ey|void|Draws a line from sx,sy to ex,dy.
Canvas|reset|-|void|Reset paint.
Canvas|alpha|Number[0,1] alpha|void|Sets the alpha of paint.
Canvas|color|String color|void|Sets paint color with [web color string](https://en.wikipedia.org/wiki/Web_colors).
Canvas|rgb|Number[0,255] red, green, blue|void|Sets the color of paint with rgb value.
Canvas|argb|Number[0,1] alpha, Number[0,255] red, green, blue|void|Sets the color of paint with argb value.
Canvas|hsb|Number[0,360] hue, Number[0,1] saturation, brightness|void|Sets the color of paint with hsb value.
Canvas|rect|Number x, y, width, height|void|Strokes a rectangle with current paint.
Canvas|rect|Number x, y, width, height, Bool filled|void|Fills or strokes a rectangle with current paint.
Canvas|oval|Number x, y, width, height|void|Strokes an oval with current paint.
Canvas|oval|Number x, y, width, height, Bool filled|void|Fills or strokes an oval with current paint.
Canvas|square|Number topleftx, toplefty, side|void|Strokes a square with current paint.
Canvas|square|Number topleftx, toplefty, side, Bool filled|void|Fills or strokes a square with current paint.
Canvas|circle|Number centrex, centrey, radius|void|Strokes a circle with current paint.
Canvas|circle|Number centrex, centrey, radius, Bool filled|void|Fills or strokes a circle with current paint.
Canvas|rotateImage, rotate_image, rotateimage|Image image, Number tlx, tly, width, height, angle, px, py|void|Draws an image on a graphics context. The image is drawn at (tlx, tly) rotated by angle pivoted around the point (px, py).

## <a name="class"></a>Class
