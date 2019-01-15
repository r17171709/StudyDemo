import 'package:flutter/material.dart';

void main() {
  runApp(new MyConstrainedBoxAppDemo());
}

class MyConstrainedBoxAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyConstrainedBoxAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ConstrainedBoxDemo"),
        ),
        body: new ConstrainedBoxDemo(),
      ),
    );
  }
}

//class ConstrainedBoxDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new ConstrainedBox(
//      constraints: new BoxConstraints(minHeight: 100.0, minWidth: 100.0),
//    );
//  }
//}

class ConstrainedBoxDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new UnconstrainedBox(
      child: new Container(
        color: Colors.blue,
        height: 50.0,
        width: 100.0,
      ),
    );
  }
}

//class ConstrainedBoxDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new ConstrainedBox(
//      constraints: new BoxConstraints(minHeight: 100.0, minWidth: 100.0),
//      child: new Container(
//        color: Colors.blue,
//        height: 50.0,
//        width: 10.0,
//      ),
//    );
//  }
//}

//class SizedBoxDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new SizedBox(
//      height: 300.0,
//      width: 200.0,
//      child: new Container(
//        color: Colors.blue,
//        height: 50.0,
//        width: 10.0,
//      ),
//    );
//  }
//}