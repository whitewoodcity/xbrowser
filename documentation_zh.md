# File

超级浏览器使用**XMLV**一种XML扩展作为其排版标准。所有展示的元素都应该被放入XMLV文件内。浏览器一旦完成资源读取便会展示其内容。XMLV文件跟普通浏览器所使用的HTML文件极为相似，包含有一个根节点以及title属性，以下便是一个XMLV的例子：

```xml
<xmlv title="Hello XMLV"/>
```

当前有两种类型的XMLV元素：
1) [名词元素](#noun) 
2) [动词元素](#verb)

## <a name="noun"></a>名词元素

名词元素用于定义以及描述页面成员。

当前有三种类型的名词元素：
1) [JSON](#json) 
2) [Preload](#preload)
2) [CSS](#css)

## <a name="json"></a>JSON

JSON元素用于描述放置于页面之上的组件。

```xml
<json>
{"type":"button","text":"i am a button"}
</json>
```
<img src="https://user-images.githubusercontent.com/5525436/31708141-c9d4901c-b3b3-11e7-8d78-c359da3ed3e9.png">
更多关于JSON文件格式的信息，请访问JSON网站：http://json.org

当前仅有三种JSON根元素，分别是：JsonObject, JsonArray and null:

1）若根元素为JSON对象，页面将会根据该对象提供的信息创建控件，并将其放置于页面正中。

```xml
<json>
{"type":"button","text":"i am a button"}
</json>
```
<img src="https://user-images.githubusercontent.com/5525436/31709531-e88194c0-b3b7-11e7-8f52-51543c2b42c1.png">

2）若根元素为JSON数组，则页面将会创建一个二维空间，并根据数组中的元素渲染控件，控件坐标将设置在xy坐标体系中。

```xml
<json>
[{"type":"button","text":"i am a button","x":100,"y":100}]
</json>
```
<img src="https://user-images.githubusercontent.com/5525436/31709530-e82dd20e-b3b7-11e7-8bc7-49a250c79b40.png">

3）若根元素为空，页面将会缺省创建一个id为canvas的画布对象。

```xml
<json/>
<script>
canvas.text("i am a canvas",100,100)
</script>
```
<img src="https://user-images.githubusercontent.com/5525436/31709529-e7d17c84-b3b7-11e7-87a6-cbedacb1858e.png">

控件列表

控件|类型|父控件|字符串属性|数字属性|布尔属性
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

控件|类型|父控件|字符串属性|JSON数组[String]属性
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

控件|类型|父控件|字符串属性|JSON数组[String]属性|布尔属性
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

控件|类型|父控件|字符串属性|数字属性|JSON数组[String]属性
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

FORM将会以如下HTTP请求体的方式提交，以JSON格式：

```json
{"textfield":"Textfield","table":[{"col":"001"}]}
```

控件|类型|父控件|字符串属性|数字属性|JSON数组[{String:Number}]属性
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

控件|类型|父控件|字符串属性|JSON数组[String/Number]属性|JSON数组{String:[Number]}属性
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

控件|类型|父控件|JSON对象{String:[{"x":Number,"y":Number,"z":Number}]}属性
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

Preload元素定义页面加载前需要下载的资源，当前仅支持以下三类资源：

wav|mp3,mp4|png,jpg,gif,bmp etc
:---:|:---:|:---:
[AudioClip](http://download.java.net/java/jdk9/jfxdocs/javafx/scene/media/AudioClip.html)|[Media](http://download.java.net/java/jdk9/jfxdocs/javafx/scene/media/Media.html)|[Image](http://download.java.net/java/jdk9/jfxdocs/javafx/scene/image/Image.html)

元素内容以多个key=value组合并以;分割的形式组成

```xml
<preload>
image001=http://w2v4.com/static/image001.png;
image002=http://w2v4.com/static/image002.png;
</preload>
```

浏览器将会下载并缓存这些资源。

<img src="https://user-images.githubusercontent.com/5525436/31669158-ec54025c-b319-11e7-8c5d-b185c5e20bb8.png"/>

资源可在[动词元素](#verb)中以key获取。

```xml
<script type="groovy">
Image image = image001
//or
Image image = preload.get('image001')
</script>
```

缺省情况下资源会被缓存起来，用户可设置new:/renew:/New:/Renew: 前缀强迫浏览器重新下载资源，无论资源是否已经被缓存。

```xml
<preload>
renew:image001=http://w2v4.com/static/image001.png;Renew:image002=http://w2v4.com/static/image002.png
</preload>
```

## <a name="css"></a>CSS

CSS是一种样式单语言，用于描述页面元素的展现形式，通常页面元素会在JSON中被定义，但CSS亦可用于定义浏览器设置的元素。

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

若一个控件拥有id，则可利用id制作特定的样式：

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

更多关于CSS文件格式信息，请参阅 [JavaFX CSS Reference Guide](http://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html).

## <a name="verb">动词元素

动词元素用于描述页面元素动作。

当前有两类动词元素：
1) [Script](#script) 
2) [Class](#class)

## <a name="script"></a>Script

放置在script元素中的是一段解释型（而非编译型）脚本语言。针对超级浏览器运行环境打造。其主要用途是制作在线程序，包括动画以及在线视频游戏等。当前超级浏览器支持3种脚本语言： [Javascript](https://developer.mozilla.org/en-US/docs/Web/JavaScript), [Ruby](http://jruby.org/) and [Groovy](http://groovy-lang.org/)。脚本语言类型可在type属性中设置。

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

### URL链接支持

脚本元素支持超链接属性，可在<script href=" ">中定义

```xml
<xmlv>
	<json/>
	<script href="www.abc.com/test.javascript" type="javascript"/>
</xmlv>
```

### 使用预定义元素

用户在使用脚本时，可操作之前在[JSON](#json)标签中定义的控件。

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

按钮每点击一次，向右移动10点位置。

<img width="362" alt="screen shot 2017-10-20 at 6 50 48 pm" src="https://user-images.githubusercontent.com/5525436/31817663-a90779be-b55a-11e7-8414-20a79bd46420.png">

### 属性绑定

控件属性可被绑定至另外的控件属性。

```xml
<xmlv>
    	<json>
		[{"type":"button","id":"button001","x":10,"y":10,"text":"i am a button"},
		{"type":"button","id":"button002","x":10,"y":50,"text":"i am a bound button"}]
	</json>
	<script type="javascript">
		button002.xproperty().bind(button001.xproperty())

		button001.action = function(event){
			button001.x = button001.x + 10
		}
	</script>
</xmlv>
```

'i am a bound button'按钮的x坐标属性被绑定至'i am a button'按钮x坐标属性，每当'i am a button'按钮向右移动，被绑定的按钮亦会向右移动。

<img width="382" alt="screen shot 2017-10-20 at 6 54 12 pm" src="https://user-images.githubusercontent.com/5525436/31817885-7b071da2-b55b-11e7-98f8-59f8b1058401.png">

### 执行

缺省状况下，脚本执行时长控制为10秒，10秒后系统将会尝试介入线程执行，12秒后系统将会强行打断线程执行。脚本执行时长建议控制在10秒以内，但有时浏览器需要超过10秒以上的执行时长，例如视频游戏。此时开发者应使用animation timer定时器以执行程序。

```xml
<xmlv>
    	<json/>
	<script type="javascript">
		x = 0
 		timer = app.timer
		timer.action = function (now) {
			canvas.clear()
			x++
			canvas.text('hello world',x,10)
		}
		timer.start()
	</script>
</xmlv>
```

控件|方法名|参数|返回类型|解释
:---:|:---:|:---|:---|:---
App|load|String url|void|Redirect page to the address:url.
App|send|Number port,String address, String message|void|Redirect page to the address:url.
App|listen|Number port|void|Listen on a **UDP** port, the result will be store in the buffer(byte[]) object.
App|listen|Number port, Number length|void|Listen on a **UDP** port with maximum buffer length.
App|buffer, getBuffer, get_buffer, getbuffer|-|Buffer|Get buffer object.
App|received|-|String|Get received buffer string.
App|received|String encoding|String|Get received buffer string with specific encoding.
App|flush|-|void|Create a new buffer.
App|play|Media media|Mediaplayer|Play the media, which is loaded and defined in the preload.
App|play|Media media, Number cycle|Mediaplayer|Play the media with cycle times.
App|play|Media media, Number cycle, Number[0,1] volume|Mediaplayer|Play the media with cycle times and the specific volume.
App|timer, getTimer, get_timer, gettimer|void|AnimationTimer|Get animation timer.
App|key, getKey, get_key, getkey|void|KeyEventHandler|Get key event handler.
App|mouse, getMouse, get_mouse, getmouse|void|MouseEventHandler|Get mouse event handler.
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
Timer|action|lambda(now->)|void|Defines an action with current time parameter which is invoked in each frame.
Timer|start|-|void|Starts the timer action.
Timer|stop|-|void|Stops the timer action.
Key|press|lambda(keycode->)|void|Defines an action which is invoked when key is pressed. 
Key|release|lambda(keycode->)|void|Defines an action which is invoked when key is released.
Mouse|press|lambda(x,y->)|void|Defines an action which is invoked when mouse left key is pressed. 
Mouse|release|lambda(x,y->)|void|Defines an action which is invoked when mouse left key is released.
Mouse|move|lambda(x,y->)|void|Defines an action which is invoked when mouse is moved.
Mouse|rightPress, right_press, rightpress|lambda(x,y->)|void|Defines an action which is invoked when mouse right key is pressed.
Mouse|rightRelease, right_release, rightrelease|lambda(x,y->)|void|Defines an action which is invoked when mouse right key is released.
Button|action|lambda(event->)|void|Defines an action when button is clicked.
Hyperlink|action|lambda(event->)|void|Defines an action when hyperlink is clicked.
Form|send|-|void|Sends a HTTP request with JSON body to the action address. The JSON body includes all children page elements.
Form|submit|-|void|Submits a HTTP request with Form body to the action address. The Form body includes all children page elements.

## <a name="class"></a>Class

超级浏览器亦支持编译型字节码格式。使用class元素及其url，name还有method属性，便可定义远程方法调用。

```xml
<xmlv>
	<class url="http://www.abc.com/" name="com.whitewoodcity.MyClass1" function="test"/>
	<class url="http://www.abc.com/" name="com.whitewoodcity.MyClass2" function="test"/>
</xmlv>
```

远程class文件的绝对地址为： www.abc.com/com/whitewoodcity/MyClass1.class 在该文件中需包含有以下方法：

```java
	public Object void test(){...}
```

开发者可使用编译型语言例如：Java，Ceylon，Kotlin，Scala等将源码编译并生成字节码文件。
