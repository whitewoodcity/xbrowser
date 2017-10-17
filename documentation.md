# File

XBrowser uses **XMLV** a XML extension as its layout standard. All display elements should be included in the XMLV file. Once the resource has been retrieved the web browser will display it. A XMLV file is pretty much similar to HTML file used by normal browsers. It included a root element xmlv and title attribute, following is an example of XMLV file:

```xml
<xmlv title="Hello XMLV"/>
```

There are two types of xmlv elements: nouns elements and verbs elements.

## Noun Elements

Noun elements are used to identify and describe page elements.

## <a name="preload"></a>Preload

Preload element describes resources to be loaded before renderring the page. Only three types of resources are supported:
wav|mp3,mp4|png,jpg,gif etc.
-|-|-
[AudioClip](http://download.java.net/java/jdk9/jfxdocs/javafx/scene/media/AudioClip.html)| [Media](http://download.java.net/java/jdk9/jfxdocs/javafx/scene/media/Media.html)|[Image](http://download.java.net/java/jdk9/jfxdocs/javafx/scene/image/Image.html)
The element content is defined as several key=value pairs separated by semicolon; 

```xml
<preload>
image001=http://w2v4.com/static/image001.png;image002=http://w2v4.com/static/image002.png
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

## <a name="json"></a>JSON
## <a name="css"></a>CSS

## <a name="verb">Verb Elements

## <a name="script"></a>Script
## <a name="class"></a>Class
