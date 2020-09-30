# HoloNext Native SDK
---
WIP

### Gradle
---
Before you start, must add the HoloNextNativeSDK as dependency to your app `build.gradle`;
```
dependencies {
    ...
    implementation 'com.holonext:HoloNextNativeSDK:1.0.0'
}
```

### Usage
---
You can use HoloNext Native SDK in your project in two different ways.

###### 1 - You can add it to your layout as follows ;

```xml
<fragment
        android:id="@+id/hnarViewerFragment"
        android:name="com.holonext.holonextnativesdk.HoloNextArViewer"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:hnar_renderer="DEFAULT"
        app:hnar_toolset="BASIC"/>
```
Then access fragment from the activity and call the init function after you set the API key;

```java
...

//Get the fragment by id
HoloNextArViewer holoNextArViewer = (HoloNextArViewer)getSupportFragmentManager().findFragmentById(R.id.ArViewFragment);

//Set HoloNext Api Key by test api key.
holoNextArViewer.getHoloNextArConfig().setHnarApiKey(HoloNextArConfig.TestAPIKey);

//Initialize HoloNextArViewer
try {
    holoNextArViewer.init(sceneRelationId);
} catch (HolonextSdkInitializeException e) {
    e.printStackTrace();
}
```

`sceneRelationId` is an unique identifier of each model. These/this id(s) must be provided by HoloNextApi.Go to the [HoloNextApi](https://holonext.azurewebsites.net) website.


###### 2 - You can create fragment programmatically by creating ArConfig data.

You must to add `implementation 'com.google.ar.sceneform.ux:sceneform-ux:1.15.0'` as dependency to App's build.gradle.Otherwise you will encounter an error.

```java
/// Programmatically create HoloNextArViewer fragment

//Create HoloNextArConfig and set the options and API key.
HoloNextArConfig arConfig = new HoloNextArConfig();
arConfig.setHnarApiKey(HoloNextArConfig.TestAPIKey);
arConfig.setHnarRendererType(RendererType.CUSTOM);
arConfig.setHnarToolsetType(ToolsetType.BASIC);

//Pass HoloNextArConfig instance as argument to the HoloNextArViewer.
HoloNextArViewer holoNextArViewer = new HoloNextArViewer(arConfig);
getSupportFragmentManager().beginTransaction()
        .add(android.R.id.content,holoNextArViewer).commit();

//Now , initialize HoloNextArViewer by 'sceneRelationId' and open the camera.
try {
    holoNextArViewer.init(sceneRelationId);
} catch (HolonextSdkInitializeException e) {
    e.printStackTrace();
}
```

###### For more details , you can check sample applications in `samples` folder

### Limitations
---
- The library able to use only default renderer for now.
- The library able to use only basic toolset for now.

#### Changelog
---
* 1.0.0
    - Released first version of library.

### License
---
WIP
