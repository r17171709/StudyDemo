import 'dart:io';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart' show rootBundle;
import 'package:path_provider/path_provider.dart';

// 一般通过如下三种方式加载图片
// new Image
// new Image.asset    从本地asset读取
// new Image.network  从网络读取
// new Image.file     从手机文件系统读取
// new Image.memory   将给定的Uint8List缓冲区解码为图像的widget，甚至我们也可以利用的来实现将Base64编码的图片展示出来（利用 Uint8List.fromtList 构造函数）
void main() {
  runApp(new MyImageAppDemo());
}

class MyImageAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyImageAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ImageDemo"),
        ),
        body: new ImageDemo(),
      ),
    );
  }
}

class ImageDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new SingleChildScrollView(
      child: new Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          new NetworkImageDemo(),
          new Padding(padding: EdgeInsets.all(10)),
          new NetworkImageDemo2(),
          new Padding(padding: EdgeInsets.all(10)),
          new AssetImageDemo(),
          new Padding(padding: EdgeInsets.all(10)),
          new FileImageDemo(),
          new Padding(padding: EdgeInsets.all(10)),
          new MemoryImageDemo(),
          new Padding(padding: EdgeInsets.all(10)),
          new FitDemo(),
          new Padding(padding: EdgeInsets.all(10)),
          new RepeatDemo()
        ],
      ),
    );
  }
}

// 网络图片
class NetworkImageDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new Image.network(
        "https://avatars2.githubusercontent.com/u/20411648?s=460&v=4",
        width: 50.0,
        height: 50.0,
      ),
    );
  }
}

// 网络图片。使用ImageProvider
class NetworkImageDemo2 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new Image(
        image: new NetworkImage(
            "https://avatars2.githubusercontent.com/u/20411648?s=460&v=4"),
        height: 50,
        width: 50,
      ),
    );
  }
}

// 本地图片
class AssetImageDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new Image.asset(
        "images/ic_index_customer_nor.png",
        // 缩小倍数
        scale: 0.5,
      ),
    );
  }
}

// 如果涉及到本地存储卡图片加载，需要使用到path_provider组件
class FileImageDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _FileImageDemoState();
  }
}

class _FileImageDemoState extends State<FileImageDemo> {
  String path = "";

  @override
  Widget build(BuildContext context) {
    getExternalStorageDirectory().then((Directory directory) {
      setState(() {
        path = "${directory.path}/long.jpg";
      });
    });
    Image image = path == ""
        ? new Image.asset(
            "images/ic_index_customer_nor.png",
            width: 50,
            height: 50,
          )
        : new Image.file(
            new File(path),
            width: 50,
            height: 50,
          );
    return image;
  }
}

// 从内存中读取图片
class MemoryImageDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new MemoryImageDemoState();
  }
}

class MemoryImageDemoState extends State<MemoryImageDemo> {
  Uint8List bytes;

  @override
  void initState() {
    super.initState();
    rootBundle
        .load('images/ic_index_customer_nor.png')
        .then((ByteData byteData) {
      setState(() {
        bytes = byteData.buffer.asUint8List();
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return bytes == null
        ? new Container(width: 0.0, height: 0.0)
        : new Image.memory(
            bytes,
            width: 50,
            height: 50,
          );
  }
}

// 剪裁样式
class FitDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new Wrap(
        spacing: 8.0,
        runSpacing: 4.0,
        runAlignment: WrapAlignment.start,
        children: <Widget>[
          new Image.network(
            "https://avatars2.githubusercontent.com/u/20411648?s=460&v=4",
            width: 100.0,
            height: 50.0,
            fit: BoxFit.cover,
          ),
          new Image.network(
            "https://avatars2.githubusercontent.com/u/20411648?s=460&v=4",
            width: 100.0,
            height: 50.0,
            fit: BoxFit.contain,
          ),
          new Image.network(
            "https://avatars2.githubusercontent.com/u/20411648?s=460&v=4",
            width: 100.0,
            height: 50.0,
            fit: BoxFit.none,
          ),
          new Image.network(
            "https://avatars2.githubusercontent.com/u/20411648?s=460&v=4",
            width: 100.0,
            height: 50.0,
            fit: BoxFit.fill,
          ),
          new Image.network(
            "https://avatars2.githubusercontent.com/u/20411648?s=460&v=4",
            width: 100.0,
            height: 50.0,
            fit: BoxFit.fitWidth,
          ),
          new Image.network(
            "https://avatars2.githubusercontent.com/u/20411648?s=460&v=4",
            width: 100.0,
            height: 50.0,
            fit: BoxFit.fitHeight,
          ),
          new Image.network(
            "https://avatars2.githubusercontent.com/u/20411648?s=460&v=4",
            width: 100.0,
            height: 50.0,
            fit: BoxFit.scaleDown,
          )
        ],
      ),
    );
  }
}

class RepeatDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new Image.network(
        "https://avatars2.githubusercontent.com/u/20411648?s=460&v=4",
        width: 50.0,
        height: 100.0,
        repeat: ImageRepeat.repeat,
      ),
    );
  }
}