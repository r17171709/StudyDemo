import 'package:flutter/material.dart';

void main() {
  runApp(new MyButtonDemoApp());
}

class MyButtonDemoApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyButtonDemoApp",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new MyButtonDemoState(),
    );
  }
}

class MyButtonDemoState extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _MyButtonDemoState();
  }
}

class _MyButtonDemoState extends State<MyButtonDemoState> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(title: new Text("MyButtonDemo")),
      body: new Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          new RaisedButtonDemo(),
          new FlatButtonDemo(),
          new OutlineButtonDemo(),
          new IconButtonDemo(),
          new CustomerButtonDemo()
        ],
      ),
    );
  }
}

class RaisedButtonDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
        padding: EdgeInsets.all(10.0),
        child: new RaisedButton(
          onPressed: () {
            print("RaisedButtonDemo");
          },
          child: new Text("RaisedButtonDemo"),
        ));
  }
}

class FlatButtonDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
        padding: EdgeInsets.all(10.0),
        child: new FlatButton(
          onPressed: () {
            print("RaisedButtonDemo");
          },
          child: new Text("RaisedButtonDemo"),
        ));
  }
}

class OutlineButtonDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
        padding: EdgeInsets.all(10.0),
        child: new OutlineButton(
          onPressed: () {
            print("RaisedButtonDemo");
          },
          child: new Text("RaisedButtonDemo"),
        ));
  }
}

class IconButtonDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new IconButton(icon: new Icon(Icons.print), onPressed: () {}),
    );
  }
}

class CustomerButtonDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new RaisedButton(
      onPressed: () {},
      child: new Text("Submit"),
      color: Colors.blue,
      highlightColor: Colors.blue[700],
      colorBrightness: Brightness.dark,
      splashColor: Colors.white,
      shape:
          new RoundedRectangleBorder(borderRadius: BorderRadius.circular(5.0)),
    );
  }
}
