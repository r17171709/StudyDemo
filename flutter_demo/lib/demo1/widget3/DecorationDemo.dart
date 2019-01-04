import 'package:flutter/material.dart';

//    this.color,                       背景色
//    this.image,                       背景图片
//    this.border,                      边界颜色、宽度等
//    this.borderRadius,                边界圆角大小
//    this.boxShadow,                   阴影
//    this.gradient,                    过度效果。会覆盖color
//    this.backgroundBlendMode,
//    this.shape = BoxShape.rectangle,  边界形状。若设置成BoxShape.circle，则不能与borderRadius同时使用

void main() {
  runApp(new MyDecorationAppDemo());
}

class MyDecorationAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyDecorationAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("DecorationDemo"),
        ),
        body: new DecorationDemo(),
      ),
    );
  }
}

//class DecorationDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    Container container = new Container(
//        padding: EdgeInsets.all(10.0),
//        child: new DecoratedBox(
//            decoration: new BoxDecoration(
//                boxShadow: <BoxShadow>[
//                  new BoxShadow(
//                      color: Colors.grey,
//                      offset: new Offset(4.0, 4.0),
//                      blurRadius: 4.0)
//                ],
//                gradient: new LinearGradient(
//                    colors: <Color>[Colors.red, Colors.yellow]),
//                shape: BoxShape.circle),
//            child: new Container(width: 100, height: 100)));
//    return container;
//  }
//}

class DecorationDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    Container container = new Container(
        padding: EdgeInsets.all(10.0),
        child: new DecoratedBox(
            decoration: new BoxDecoration(
              borderRadius: BorderRadius.all(Radius.circular(10.0)),
              border: Border.all(
                color: Colors.black,
                width: 4.0,
              ),
              color: Colors.blue,
              boxShadow: <BoxShadow>[
                new BoxShadow(
                    color: Colors.grey,
                    offset: new Offset(4.0, 4.0),
                    blurRadius: 4.0)
              ],
              gradient: new LinearGradient(
                  colors: <Color>[Colors.red, Colors.yellow]),
            ),
            child: new Container(width: 100, height: 100)));
    return container;
  }
}