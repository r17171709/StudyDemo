import 'package:flutter/material.dart';

void main() {
  runApp(new MyWrapAppDemo());
}

class MyWrapAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyWrapAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("WrapAppDemo"),
        ),
        body: new WrapDemo(),
      ),
    );
  }
}

class WrapDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Wrap(
      runSpacing: 8.0,
      spacing: 8.0,
      alignment: WrapAlignment.center,
      children: <Widget>[
        new Container(
          color: Colors.red,
          height: 50,
          width: 100,
        ),
        new Container(
          color: Colors.orange,
          height: 50,
          width: 50,
        ),
        new Container(
          color: Colors.yellow,
          height: 50,
          width: 150,
        ),
        new Container(
          color: Colors.green,
          height: 50,
          width: 100,
        ),
        new Container(
          color: Colors.lightBlue,
          height: 100,
          width: 50,
        ),
        new Container(
          color: Colors.blue,
          height: 50,
          width: 100,
        ),
        new Container(
          color: Colors.purple,
          height: 50,
          width: 200,
        )
      ],
    );
  }
}
