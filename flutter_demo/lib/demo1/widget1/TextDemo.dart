import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(new MyTextDemoApp());
}

class MyTextDemoApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyTextDemoApp",
      theme: ThemeData(
        primarySwatch: Colors.red,
      ),
      home: new MyTextDemoState(),
    );
  }
}

class MyTextDemoState extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _MyTextDemoState();
  }
}

class _MyTextDemoState extends State<MyTextDemoState> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("TextDemo"),
      ),
      body: new Column(
        children: <Widget>[
          new TextAlignDemo(),
          new TextMaxLineDemo(),
          new TextScaleFactorDemo(),
          new TextStyleDemo(),
          new TextSpanDemo(),
          new DefaultTextStyleDemo()
        ],
      ),
    );
  }
}

class TextAlignDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new Text("TextAlign" * 16, textAlign: TextAlign.center),
    );
  }
}

class TextMaxLineDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new Text(
        "TextMaxLineDemo" * 6,
        maxLines: 1,
        overflow: TextOverflow.ellipsis,
      ),
    );
  }
}

class TextScaleFactorDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new Text(
        "TextScaleFactor",
        textScaleFactor: 1.5,
      ),
    );
  }
}

class TextStyleDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new Text(
        "TextStyleDemo",
        style: new TextStyle(
            color: Colors.red,
            fontSize: 16.0,
            background: new Paint()..color = Colors.yellow,
            decoration: TextDecoration.underline,
            decorationStyle: TextDecorationStyle.dashed),
      ),
    );
  }
}

class TextSpanDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
        padding: EdgeInsets.all(10.0),
        child: new Text.rich(
          new TextSpan(
              style: new TextStyle(
                  color: Colors.blue,
                  fontSize: 16.0,
                  background: new Paint()..color = Colors.yellow),
              children: <TextSpan>[
                new TextSpan(
                    text: "TextSpanDemo1",
                    recognizer: new TapGestureRecognizer()
                      ..onTap = () {
                        print("TextSpanDemo1");
                      }),
                new TextSpan(
                    text: "TextSpanDemo2",
                    style: new TextStyle(
                      color: Colors.black,
                      fontSize: 12.0,
                    ))
              ]),
        ));
  }
}

class DefaultTextStyleDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.all(10.0),
      child: new DefaultTextStyle(
          style: new TextStyle(
              color: Colors.blue,
              fontSize: 16.0,
              background: new Paint()..color = Colors.yellow),
          child: Column(
            children: <Widget>[
              new Text("DefaultTextStyleDemo1"),
              new Text("DefaultTextStyleDemo2"),
              new Text(
                "DefaultTextStyleDemo3",
                style: new TextStyle(color: Colors.green),
              )
            ],
          )),
    );
  }
}
