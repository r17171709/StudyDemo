import 'package:flutter/material.dart';

void main() {
  runApp(new MyStackAppDemo());
}

class MyStackAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyStackAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("StackDemo"),
        ),
        body: new StackDemo(),
      ),
    );
  }
}

class StackDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new ConstrainedBox(
      constraints: BoxConstraints.expand(),
      child: new Stack(
        children: <Widget>[
          new Container(
            color: Colors.red,
            height: 50,
            width: 100,
          ),
          new Positioned(
            child: new Container(
              color: Colors.orange,
              height: 50,
              width: 100,
            ),
            left: 50.0,
          ),
          new Positioned(
            child: new Container(
              color: Colors.yellow,
              height: 50,
              width: 50,
            ),
            top: 50.0,
          )
        ],
        fit: StackFit.expand,
      ),
    );
  }
}
