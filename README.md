RxWifi
==========

This library has been built in order to help us in retrieving the different networks in range of
the device providing the result as an `Observable` instead of relying on usual Android's `Receiver`
paradigm.

**RxWifi is now available on Android-Arsenal**

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RxWifi-green.svg?style=true)](https://android-arsenal.com/details/1/3212)

Disclaimer
-----------
*RxWifi* is being developed an it is provided as is (please check the [license][license] for further
information)

Importing
==========
Gradle
---------
Add the `JitPack` repo to the root `build.gradle` file of your project:
```groovy
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}
```
Add the RxWifi library between the dependencies of your app
```groovy
    compile 'com.github.Ennova-IT:RxWifi:1.3.5'
```

Maven
--------
Add the `JitPack` repo to your build file
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```
Add the dependency
```xml
<dependency>
  <groupId>com.github.Ennova-IT</groupId>
  <artifactId>RxWifi</artifactId>
  <version>1.3.5</version>
</dependency>
```

Usage
=======
The main entry point of the library will provide you with an `Hot Observable` by simply invoking the
`static factory method` and passing the `Context` as input parameter.

```java
RxWifi.from(context)
```

Alternatively, you can request multiple scans of the available networks, so that you can leverage the network data changes.
This method will return a `List<ScanResult>` multiple `times` (once per iteration).
```java
RxWifi.from(context, times)
```

**Note**: this command can take up to 10 seconds per scan, so it is running (by default) on a proper `Scheduler` so that it does
not block the UI.

As a bonus, we added a converter from the `ScanResult` class to a [`WiFiNetwork`][wifinetwork] one so
that it is easier to show simpler information to the user

```java
ScanResultUtils.toWiFiNetwork(scanResult)
```    
Open points
=======
* force a new WiFi scanning
* further optimizations

[license]:https://github.com/Ennova-IT/RxWifi/blob/master/LICENSE.md
[wifinetwork]:https://github.com/Ennova-IT/RxWifi/blob/master/rxwifi/src/main/java/it/ennova/rxwifi/WiFiNetwork.java
