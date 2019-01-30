import 'package:flutter/material.dart';

//  用于给它的子组件强制加上一些约束

void main() {
  runApp(new MyConstrainedBoxAppDemo());
}

class MyConstrainedBoxAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyConstrainedBoxAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ConstrainedBoxDemo"),
        ),
        body: new ConstrainedBoxDemo(),
      ),
    );
  }
}

//class ConstrainedBoxDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new ConstrainedBox(
//      constraints: new BoxConstraints(minHeight: 100.0, minWidth: 100.0),
//      // 约束child最小尺寸为100,100
//      child: new Container(color: Colors.black, height: 30, width: 30,),
//    );
//  }
//}

//class ConstrainedBoxDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new ConstrainedBox(
//      // 约束child最大尺寸为100,100
//      constraints: new BoxConstraints(maxHeight: 100.0, maxWidth: 100.0),
//      child: new Container(color: Colors.black, height: 300, width: 300,),
//    );
//  }
//}

class ConstrainedBoxDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new ConstrainedBox(
      // 约束child尺寸只可以为100,100
      constraints: new BoxConstraints.tight(new Size(100, 100)),
      child: new Container(color: Colors.blue, height: 300, width: 300,),
    );
  }
}

//class ConstrainedBoxDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new UnconstrainedBox(
//      child: new Container(
//        color: Colors.blue,
//        height: 50.0,
//        width: 100.0,
//      ),
//    );
//  }
//}