import 'package:flutter/material.dart';

void main() {
  runApp(new MyExpandedAppDemo());
}

class MyExpandedAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyExpandedAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ExpandedDemo"),
        ),
        body: new ExpandedDemo(),
      ),
    );
  }
}

class ExpandedDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new Row(
          children: <Widget>[
            new Text("没有Expanded"),
          ],
        ),
        new Row(
          children: <Widget>[
            new Expanded(
                child: new Text(
              "有Expanded",
              textAlign: TextAlign.center,
            ))
          ],
        ),
        new Row(
          children: <Widget>[
            new Text(
              "没有Expanded",
              textAlign: TextAlign.center,
            ),
            new Expanded(
              child: new Text(
                "有Expanded",
                textAlign: TextAlign.center,
              ),
            ),
            new Text(
              "没有Expanded",
              textAlign: TextAlign.center,
            )
          ],
        ),
        new Flex(
          direction: Axis.horizontal,
          children: <Widget>[
            new Expanded(
              child: new Container(
                color: Colors.blue,
                constraints: new BoxConstraints.expand(
                    width: double.infinity, height: 30),
              ),
              flex: 1,
            ),
            new Expanded(
              child: new Container(
                color: Colors.red,
                constraints: new BoxConstraints.expand(
                    width: double.infinity, height: 30),
              ),
              flex: 3,
            )
          ],
        )
      ],
    );
  }
}
