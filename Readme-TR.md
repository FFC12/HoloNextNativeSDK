# HoloNext Native SDK
---
Eklenecek.

### Gradle
---
Başlamadan önce, uygulamanızın `build.gradle`'ına HoloNextNativeSDK'yi proje bağımlılığı olarak eklemeniz gerekiyor;
```
dependencies {
    ...
    implementation 'com.holonext:HoloNextNativeSDK:1.0.0'
}
```

### Kullanım
---
HoloNext Native SDK'yi iki farklı şekilde projenizde kullabilirsiniz.

###### 1 - Projenizin layout'una (XML dosyanıza) aşağıdaki gibi fragment olarak ekleyebilirsiniz;
---

```xml
<fragment
        android:id="@+id/hnarViewerFragment"
        android:name="com.holonext.holonextnativesdk.HoloNextArViewer"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:hnar_renderer="DEFAULT"
        app:hnar_toolset="BASIC"/>
```
Ardından activity'nizden fragment'a ulaşır ve API anahtarını ekledikten sonra `init` fonksiyonunu çağırabilirsiniz;
```java
...

//Id ile fragment'ı al
HoloNextArViewer holoNextArViewer = (HoloNextArViewer)getSupportFragmentManager().findFragmentById(R.id.ArViewFragment);

//Test api keyini HoloNext Api Key'i olarak ayarla.
holoNextArViewer.getHoloNextArConfig().setHnarApiKey(HoloNextArConfig.TestAPIKey);

//HoloNextArViewer'ı başlat.
try {
    holoNextArViewer.init(sceneRelationId);
} catch (HolonextSdkInitializeException e) {
    e.printStackTrace();
}
```

`sceneRelationId` her bir model için benzersiz bir tanımlayıcıdır.Bu id/id'ler HoloNextApi servisi tarafından sağlanmış olmalıdır.Bu konuyla ilgili olarak internet sitesine ulaşmak için : [HoloNextApi](https://holonext.azurewebsites.net).


###### 2 - Fragment'ı programatik olarak ArConfig ile birlikte oluşturmak.
---
Bu şekilde HoloNextArViewer'ı oluşturabilmek için `implementation 'com.google.ar.sceneform.ux:sceneform-ux:1.15.0'` bağımlılığını uygulamanın `build.gradle`'ına eklemelisiniz.Aksi takdirde hata ile karşılaşırsınız.

```java
/// Programatik olarak HoloNextArViewer fragment'ı oluşturma

//HoloNextArConfig instance et ve gerekli ayarlamaları ve API anahtarı ayarla.
HoloNextArConfig arConfig = new HoloNextArConfig();
arConfig.setHnarApiKey(HoloNextArConfig.TestAPIKey);
arConfig.setHnarRendererType(RendererType.CUSTOM);
arConfig.setHnarToolsetType(ToolsetType.BASIC);

//HoloNextArConfig objesini argüman olarak HoloNextArViewer constructor'ına ver.
HoloNextArViewer holoNextArViewer = new HoloNextArViewer(arConfig);
getSupportFragmentManager().beginTransaction()
        .add(android.R.id.content,holoNextArViewer).commit();

//Artık HoloNextArViewer'ı belirtilecek'sceneRelationId' ile başlatabilir ve kameranın açılmasını sağlayabiliriz.
try {
    holoNextArViewer.init(sceneRelationId);
} catch (HolonextSdkInitializeException e) {
    e.printStackTrace();
}
```

###### Daha fazla detay için , `samples` klasörü içerisindeki örnek uygulamalara bakabilir yada API referansı [dökümantasyon](https://holonext.azurewebsites.net)una gidebilirsiniz.

### Sınırlamalar
---
- Şuan için sadece varsayılan renderer kullanılabilir.
- Şuan için sadece basit araç takımı kullanılabilir.

#### Değişiklik Günlüğü
---
* 1.0.0
    - Kütüphanenin ilk versiyonu yayımlandı.

### Lisans
---
Eklenecek
