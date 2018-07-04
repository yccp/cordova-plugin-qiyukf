# 七鱼客服 cordova 插件 

> 相关依赖
[cordova-plugin-cocoapod-support](https://www.npmjs.com/package/cordova-plugin-cocoapod-support)
```
cordova plugin add cordova-plugin-cocoapod-support --save
```
或
```
ionic cordova plugin add cordova-plugin-cocoapod-support
```

## 安装

```
cordova plugin add cordova-plugin-qiyukf --variable IOS_APP_NAME=你的IOS_APP_NAME --variable IOS_APP_KEY=你的IOS_APP_KEY --variable ANDROID_APP_NAME=你的ANDROID_APP_NAME --variable ANDROID_APP_KEY=你的ANDROID_APP_KEY --save
```
或
```
ionic cordova plugin add cordova-plugin-qiyukf --variable IOS_APP_NAME=你的IOS_APP_NAME --variable IOS_APP_KEY=你的IOS_APP_KEY --variable ANDROID_APP_NAME=你的ANDROID_APP_NAME --variable ANDROID_APP_KEY=你的ANDROID_APP_KEY
```

## 配置
> ios
```xml
<edit-config file="*-Info.plist" mode="merge" target="NSCameraUsageDescription">
    <string>XXX需要使用您的摄像头</string>
</edit-config>
<edit-config file="*-Info.plist" mode="merge" target="NSPhotoLibraryUsageDescription">
    <string>XXX需要访问您的相册</string>
</edit-config>
<edit-config file="*-Info.plist" mode="merge" target="NSLocationWhenInUseUsageDescription">
    <string>XXX需要访问您的地理位置</string>
</edit-config>
<edit-config file="*-Info.plist" mode="merge" target="NSPhotoLibraryAddUsageDescription">
    <string>XXX需要访问您的相册</string>
</edit-config>
<edit-config file="*-Info.plist" mode="merge" target="NSMicrophoneUsageDescription">
    <string>XXX需要访问您的麦克风</string>
</edit-config>
```

## 使用方法
```js
window.Qiyukf.open(
  () => {
    console.log('打开客服成功');
  },
  e => console.error(e)
);
```