import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart' show rootBundle;
import 'dart:ui' as ui;

// 使用dart:ui.Image来显示图片的widget

void main() {
  runApp(new MyRawImageAppDemo());
}

class MyRawImageAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyRawImageAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("RawImageDemo"),
        ),
        body: new RawImageDemo(),
      ),
    );
  }
}

class RawImageDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _RawImageDemoState();
  }
}

class _RawImageDemoState extends State<RawImageDemo> {
  ui.Image _image;

  Future<ui.Image> _loadImageByAsset(String asset) async {
    ByteData data = await DefaultAssetBundle.of(context).load(asset);
//    ByteData data = await rootBundle.load(asset);
    ui.Codec codec = await ui.instantiateImageCodec(data.buffer.asUint8List());
    ui.FrameInfo fi = await codec.getNextFrame();
    return fi.image;
  }

  @override
  void initState() {
    super.initState();
    _loadImageByAsset("images/ic_index_customer_nor.png")
        .then((ui.Image onValue) {
      setState(() {
        _image = onValue;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return _image == null
        ? new Container()
        : new RawImage(
            image: _image,
          );
  }
}
