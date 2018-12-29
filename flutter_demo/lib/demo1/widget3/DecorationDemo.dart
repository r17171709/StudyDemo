import 'package:flutter/material.dart';

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

class DecorationDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    Container container = new Container(
        padding: EdgeInsets.all(10.0),
        child: new DecoratedBox(
            decoration: new BoxDecoration(
              borderRadius: BorderRadius.all(Radius.circular(10.0)),
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
