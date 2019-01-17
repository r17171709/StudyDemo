import 'package:flutter/material.dart';

//  this.maxWidth
//  this.maxHeight
//  将child限制在其设定的最大宽高中。但是这个限定是有条件的，当LimitedBox外部没有约束的时候，child的宽度也不会受到这个最大宽度的限制

void main() {
  runApp(new MyLimitedBoxAppDemo());
}

class MyLimitedBoxAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyLimitedBoxAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("LimitedBoxDemo"),
        ),
        body: new LimitedBoxDemo(),
      ),
    );
  }
}

// 当其为Column的时候，maxHeight高度设置有效，maxWidth设置无效
class LimitedBoxDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        LimitedBox(
          maxHeight: 200,
          maxWidth: 100.0,
          child: Container(
            color: Colors.blue,
            width: 250.0,
          ),
        )
      ],
    );
  }
}

class ConstrainedBoxDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new ConstrainedBox(
      constraints: new BoxConstraints.tight(new Size(100, 100)),
      child: new Container(
        height: 300,
        width: 300,
        color: Colors.red,
      ),
    );
  }
}
