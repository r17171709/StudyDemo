import 'package:flutter/material.dart';

//  将可能高度不受限制的child，调整到一个合适并且合理的尺寸

void main() {
  runApp(new MyIntrinsicHeightAppDemo());
}

class MyIntrinsicHeightAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyIntrinsicHeightAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("IntrinsicHeightDemo"),
        ),
        body: new IntrinsicHeightDemo(),
      ),
    );
  }
}

class IntrinsicHeightDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new IntrinsicHeight(
      child: new Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: <Widget>[
          new Container(
            color: Colors.red,
            width: 100.0,
            height: 50,
          ),
          new Container(
            color: Colors.green,
            width: 50.0,
          ),
          new Container(
            color: Colors.black,
            width: 100.0,
          )
        ],
      ),
    );
  }
}
