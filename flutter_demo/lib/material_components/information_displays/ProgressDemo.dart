import 'package:flutter/material.dart';

void main() {
  runApp(new MyProgressAppDemo());
}

class MyProgressAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyProgressAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ProgressDemo"),
        ),
        body: new Column(
          children: <Widget>[
            new LinearProgressIndicatorDemo1(),
            new LinearProgressIndicatorDemo2(),
            new CircularProgressIndicatorDemo1(),
            new CircularProgressIndicatorDemo2()
          ],
        ),
      ),
    );
  }
}

class LinearProgressIndicatorDemo1 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Container(
      margin: EdgeInsets.all(10),
      constraints: new BoxConstraints.expand(height: 5),
      child: new LinearProgressIndicator(
        value: 0.5,
      ),
    );
  }
}

class LinearProgressIndicatorDemo2 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Container(
      margin: EdgeInsets.all(10),
      constraints: new BoxConstraints.expand(height: 5),
      child: new LinearProgressIndicator(),
    );
  }
}

class CircularProgressIndicatorDemo1 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Container(
      margin: EdgeInsets.all(10),
      height: 50,
      width: 50,
      child: new CircularProgressIndicator(),
    );
  }
}

class CircularProgressIndicatorDemo2 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Stack(
      children: <Widget>[
        new Container(
          margin: EdgeInsets.all(10),
          height: 50,
          width: 50,
          child: new CircularProgressIndicator(
            valueColor: AlwaysStoppedAnimation(Colors.blue),
            value: 1,
          ),
        ),
        new Container(
          margin: EdgeInsets.all(10),
          height: 50,
          width: 50,
          child: new CircularProgressIndicator(
            valueColor: AlwaysStoppedAnimation(Colors.white),
            value: 0.1,
          ),
        ),
      ],
    );
  }
}
