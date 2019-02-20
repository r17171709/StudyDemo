import 'package:flutter/material.dart';

void main() {
  runApp(new MyClipAppDemo());
}

class MyClipAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyClipOvalAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ClipOvalDemo"),
        ),
        body: new Column(
          children: <Widget>[
            new SizedBox.fromSize(
              size: new Size(60, 60),
              // 使用椭圆剪辑其子项的widget
              child: new ClipOval(
                child: new Image.network(
                  "http://c.hiphotos.baidu.com/image/pic/item/5bafa40f4bfbfbedc92f87b675f0f736aec31f80.jpg",
                  fit: BoxFit.cover,
                ),
              ),
            ),
            new SizedBox.fromSize(
              size: new Size(60, 100),
              child: new ClipOval(
                child: new Image.network(
                  "http://c.hiphotos.baidu.com/image/pic/item/5bafa40f4bfbfbedc92f87b675f0f736aec31f80.jpg",
                  fit: BoxFit.cover,
                ),
              ),
            ),
            new SizedBox.fromSize(
              size: new Size(60, 60),
              // 使用矩形剪辑其子项的widget
              child: new ClipRect(
                child: new Image.network(
                  "http://c.hiphotos.baidu.com/image/pic/item/5bafa40f4bfbfbedc92f87b675f0f736aec31f80.jpg",
                  fit: BoxFit.cover,
                ),
              ),
            ),
            new Container(
              height: 120,
              width: 120,
              color: Colors.red,
              // 使用路径剪辑其子项的窗口
              child: new ClipPath(
                clipper: new CustomerClipPath(),
                child: new Image.network(
                  "http://c.hiphotos.baidu.com/image/pic/item/5bafa40f4bfbfbedc92f87b675f0f736aec31f80.jpg",
                  fit: BoxFit.cover,
                ),
              ),
            ),
            new SizedBox.fromSize(
              size: new Size(60, 60),
              // 使用圆角矩形剪辑其子项的窗口widget
              child: new ClipRRect(
                borderRadius: BorderRadius.all(Radius.circular(5)),
                child: new Image.network(
                  "http://c.hiphotos.baidu.com/image/pic/item/5bafa40f4bfbfbedc92f87b675f0f736aec31f80.jpg",
                  fit: BoxFit.cover,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class CustomerClipPath extends CustomClipper<Path> {
  @override
  Path getClip(Size size) {
    Path path = new Path();
    path.lineTo(60, 0);
    path.lineTo(60, 60);
    path.lineTo(0, 60);
    path.close();
    return path;
  }

  @override
  bool shouldReclip(CustomClipper<Path> oldClipper) {
    return true;
  }
}
