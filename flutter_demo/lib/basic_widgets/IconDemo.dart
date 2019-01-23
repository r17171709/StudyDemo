import 'package:flutter/material.dart';

void main() {
  runApp(new MyIconAppDemo());
}

class MyIconAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyIconAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
//        appBar: new AppBar(
//          iconTheme: new IconThemeData(color: Colors.yellow),
//          actions: <Widget>[new Icon(Icons.add), new Icon(Icons.print)],
//          title: new Text("IconDemo"),
//        ),
        appBar: new AppBar(
          actions: <Widget>[
            new IconTheme(
                // IconThemeData用于设置图标的颜色，不透明度和大小
                data: new IconThemeData(
                    color: Colors.white, opacity: 0.5, size: 50),
                child: new Icon(Icons.add)),
            new IconTheme(
                data: new IconThemeData(color: Colors.yellow),
                child: new Icon(Icons.print))
          ],
          title: new Text("IconDemo"),
        ),
        body: new Column(
          children: <Widget>[
            new IconButtonDemo(),
            new IconDemo(),
            new ImageIconDemo(),
            new CustomerIconImageDemo()
          ],
        ),
      ),
    );
  }
}

class IconButtonDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new IconButton(icon: new Icon(Icons.add), onPressed: () {});
  }
}

class IconDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Icon(
      Icons.add,
      color: Colors.blue,
    );
  }
}

class ImageIconDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new ImageIcon(new AssetImage("images/ic_index_customer_nor.png"));
  }
}

// 自定义Icon图片
class CustomerIconImageDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Icon(
      MyIcons.cactus,
      color: Colors.green,
      size: 50.0,
    );
  }
}

class MyIcons {
  static const IconData cactus =
  // fontPackage命名要小心，命名不当不显示图像
  const IconData(0xe601, fontFamily: "iconfonts", matchTextDirection: true);
}


