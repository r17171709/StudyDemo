import 'package:flutter/material.dart';

void main() {
  runApp(new MyImageAppDemo());
}

class MyImageAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyImageDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new MyImageState(),
    );
  }
}

class MyImageState extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _MyImageState();
  }
}

class _MyImageState extends State<MyImageState> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("MyImage"),
      ),
      body: new Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          new NetworkImageDemo(),
          new AssetImageDemo(),
          new IconImageDemo(),
          new FitDemo(),
          new RepeatDemo()
        ],
      ),
    );
  }
}

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

class AssetImageDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new Image.asset(
        "images/ic_index_customer_nor.png",
        width: 50.0,
        height: 50.0,
      ),
    );
  }
}

class IconImageDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Icon(
      MyIcons.cactus,
      color: Colors.green,
      size: 50.0,
    );
  }
}

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
        width: 200.0,
        height: 50.0,
        repeat: ImageRepeat.repeatX,
      ),
    );
  }
}

class MyIcons {
  static const IconData cactus =
  // fontPackage命名要小心，命名不当不显示图像
      const IconData(0xe601, fontFamily: "iconfonts", matchTextDirection: true);
}
