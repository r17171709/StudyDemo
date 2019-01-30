import 'package:flutter/material.dart';

void main() {
  runApp(new MyOffstageAppDemo());
}

class MyOffstageAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyOffstageAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("OffstageDemo"),
        ),
        body: new OffstageDemo(),
      ),
    );
  }
}

class OffstageDemo extends StatefulWidget {
  bool offstage = false;

  @override
  State<StatefulWidget> createState() {
    return new _OffstageDemoState();
  }
}

class _OffstageDemoState extends State<OffstageDemo> {
  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new Offstage(
          offstage: widget.offstage,
          child: new Text("OffstageDemo"),
        ),
        new RaisedButton(
          onPressed: () {
            setState(() {
              widget.offstage = !widget.offstage;
            });
          },
          child: new Text("Click"),
        )
      ],
    );
  }
}
