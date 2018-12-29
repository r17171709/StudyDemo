import 'package:flutter/material.dart';

void main() {
  runApp(new MyFlexDemo());
}

class MyFlexDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyFlexDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("FlexDemo"),
        ),
        body: new FlexDemoState(),
      ),
    );
  }
}

class FlexDemoState extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new Flex(
          direction: Axis.horizontal,
          children: <Widget>[
            new Expanded(
              child: new Container(
                color: Colors.red,
                height: 50.0,
              ),
              flex: 2,
            ),
            new Expanded(
              child: new Container(
                color: Colors.blue,
                height: 50.0,
              ),
              flex: 1,
            )
          ],
        ),
        new Padding(padding: EdgeInsets.only(top: 20.0)),
        new SizedBox(
          height: 300,
          child: new Flex(
            direction: Axis.vertical,
            children: <Widget>[
              new Expanded(
                child: new Container(
                  color: Colors.green,
                ),
                flex: 2,
              ),
              new Spacer(),
              new Expanded(
                child: new Container(
                  color: Colors.orange,
                ),
                flex: 1,
              ),
            ],
          ),
        )
      ],
    );
  }
}
