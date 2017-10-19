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
Chart|-|Node|title|x,y,width,height|-
View|-|Node|-|x,y,width,height|-
ImageView|img,image,imageview|View|url,image|-|-
Label|label|Control|text|-|-
Button|button|Control|text|-|-
Hyperlink|hyperlink|Control|text|-|-
TextField|textfield|Control|text|-|-
Form|form|Control|text,action,method|-|-

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
"values":[[1,2,3],[3,2,1]]
}
```
<img src="https://user-images.githubusercontent.com/5525436/31760236-a5c71a52-b479-11e7-93ec-960e743d00f7.png">

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

## <a name="verb">Verb Elements

## <a name="script"></a>Script


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
