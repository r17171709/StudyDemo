import 'package:flutter/material.dart';
import 'package:flutter_demo/asset_images_icons/RawImageDemo.dart';

// 包含应用程序可以使用的资源，如图像和字符串。对这些资源的访问是异步，所以他们可以来自网络（例如，从NetworkAssetBundle）或从本地文件系统，并且这并不会挂起用户界面。
// 例如可以使用AssetBundle来访问assets
// 加载文件使用loadString()，图片可以使用load()
// 可以通过package:flutter/services.dart中的rootBundle对象访问assets。但是还是建议使用AssetBundle传入当前的BuildContext来访问：DefaultAssetBundle.of(context)
// 在widget上下文外，没法使用AssetBundle的时候可以使用rootBundle来加载

void main() {
  runApp(new MyRawImageAppDemo());
}