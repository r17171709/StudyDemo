import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter_demo/demo1/widget6/EventBus.dart';

void main() {
  runApp(new MyRichTextAppDemo());
}

class MyRichTextAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyRichTextAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("RichTextDemo"),
        ),
        body: new RichTextDemo(),
      ),
    );
  }
}

class RichTextDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _RichTextDemoState();
  }
}

class _RichTextDemoState extends State<RichTextDemo> {
  final TapGestureRecognizer _detector = new TapGestureRecognizer();
  Color _color = Colors.black;

  @override
  Widget build(BuildContext context) {
    new EventBus().add("changeColor", (String args) {
      setState(() {
        _color = Colors.green;
      });
    });
    return new SizedBox(
      width: 200.0,
      height: 200.0,
      child: new Text.rich(new TextSpan(children: <TextSpan>[
        new TextSpan(text: "Hello"),
        new TextSpan(
            text: "点击变色",
            style: new TextStyle(color: _color),
            recognizer: _detector
              ..onTap = () {
                new EventBus().post("changeColor", "hi");
              }),
        new TextSpan(text: "Hello Flutter"),
      ])),
    );
  }
}
