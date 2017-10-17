# XBrowser

XBrowser(Codename BlueShark) is a hybrid client for internet surfing, data visualization, business intelligence and online gaming etc. internet applications.

[Quick Start](# Quick Start)

## Quick Start

Here is a quick start for building an online application in 5 minutes.

1) Download install file and install XBrowser in your computer then start the application you just installed.

<img width="960" alt="1" src="https://user-images.githubusercontent.com/5525436/31541744-542c9800-afd5-11e7-9949-288ee753a1fb.png">

2) Type in www.wikipedia.org then enter, you will see the Wikipedia website.

<img width="965" alt="1" src="https://user-images.githubusercontent.com/5525436/31616809-4cb2e91e-b253-11e7-88a7-41192f1f00a5.png">

3) Create a test.xmlv and type in following text: 

```xml
<xmlv>
	<json/>
	<script>
		canvas.text('Hello BlueShark',100,100)
	</script>
</xmlv>
```

save it(Ctrl+S/Cmd+S on mac). Then open the XBrowser and use the shortcut(Ctrl+L/Cmd+L on mac) to initiate file chooser and then choose the test.xmlv file you just created.
<img width="960" alt="1" src="https://user-images.githubusercontent.com/5525436/31615994-071f58d0-b251-11e7-912b-beb6ec3d5e0b.png">

4) Expand json to:

```xml
<xmlv>
	<json>
		[{"type":"button","id":"button001","x":10,"y":10,"text":"A normal button"}]
	</json>
	<script/>
</xmlv>
```

save it, reopen the file:
<img width="960" alt="1" src="https://user-images.githubusercontent.com/5525436/31616450-5d92fce8-b252-11e7-8088-82a42b8fbf76.png">

5) Expand both json and script tags:

```xml
<xmlv>
	<json>
		[{"type":"button","id":"button001","x":10,"y":10,"text":"A normal button"}]
	</json>
	<script>
		timer = app.timer
		timer.action = { now -> 
			button001.x += 1
		}
		timer.start()
	</script>
</xmlv>
```
Save and reopen the file, the animation is out there.

