# File

XBrowser uses **XMLV** a XML extension as its layout standard. All display elements should be included in the XMLV file. Once the resource has been retrieved the web browser will display it. A XMLV file is pretty much similar to HTML file used by normal browsers. It included a root element xmlv and title attribute, following is an example of XMLV file:

```xml
<xmlv title="Hello XMLV"/>
```

There are two types of xmlv elements: nouns elements and verbs elements.

## Noun Elements

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
[{"type":"button","text":"i am a button"}]
</json>
```
<imgsrc="https://user-images.githubusercontent.com/5525436/31709530-e82dd20e-b3b7-11e7-8bc7-49a250c79b40.png">
3) If the root element is empty, the page will by default place a canvas with id:canvas.
```xml
<json/>
<script>
canvas.text("i am a canvas",100,100)
</script>
```
<img src="https://user-images.githubusercontent.com/5525436/31709529-e7d17c84-b3b7-11e7-87a6-cbedacb1858e.png">

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
## <a name="class"></a>Class
