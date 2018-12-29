import 'package:flutter/material.dart';

void main() {
  runApp(new MyRowDemo());
}

class MyRowDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyRowDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("RowDemo"),
        ),
        body: new RowDemoState(),
      ),
    );
  }
}

class RowDemoState extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _RowDemoState();
  }
}

//class _RowDemoState extends State<RowDemoState> {
//  @override
//  Widget build(BuildContext context) {
//    return new Row(
//      mainAxisAlignment: MainAxisAlignment.center,
//      children: <Widget>[
//        new Container(
//          height: 100.0,
//          width: 100.0,
//          color: Colors.blue,
//        )
//      ],
//    );
//  }
//}

//class _RowDemoState extends State<RowDemoState> {
//  @override
//  Widget build(BuildContext context) {
//    return new Row(
//      mainAxisSize: MainAxisSize.min,
//      mainAxisAlignment: MainAxisAlignment.center,
//      children: <Widget>[
//        new Container(
//          height: 100.0,
//          width: 100.0,
//          color: Colors.blue,
//        )
//      ],
//    );
//  }
//}

//class _RowDemoState extends State<RowDemoState> {
//  @override
//  Widget build(BuildContext context) {
//    return new Row(
//      mainAxisSize: MainAxisSize.min,
//      crossAxisAlignment: CrossAxisAlignment.start,
//      children: <Widget>[
//        new Container(
//          height: 100.0,
//          width: 100.0,
//          color: Colors.blue,
//        ),
//        new Container(
//          height: 50.0,
//          width: 50.0,
//          color: Colors.yellow,
//        )
//      ],
//    );
//  }
//}

//class _RowDemoState extends State<RowDemoState> {
//  @override
//  Widget build(BuildContext context) {
//    return new Row(
//      mainAxisSize: MainAxisSize.min,
//      crossAxisAlignment: CrossAxisAlignment.start,
//      verticalDirection: VerticalDirection.up,
//      children: <Widget>[
//        new Container(
//          height: 100.0,
//          width: 100.0,
//          color: Colors.blue,
//        ),
//        new Container(
//          height: 50.0,
//          width: 50.0,
//          color: Colors.yellow,
//        )
//      ],
//    );
//  }
//}

//class _RowDemoState extends State<RowDemoState> {
//  @override
//  Widget build(BuildContext context) {
//    return new Row(
//      mainAxisSize: MainAxisSize.max,
//      mainAxisAlignment: MainAxisAlignment.start,
//      children: <Widget>[
//        // 如果Row里面嵌套Row，或者Column里面再嵌套Column，那么只有对最外面的Row或Column会占用尽可能大的空间，里面Row或Column所占用的空间为实际大小
//        new Row(
//          mainAxisSize: MainAxisSize.max,
//          mainAxisAlignment: MainAxisAlignment.center,
//          children: <Widget>[
//            new Container(
//              height: 100.0,
//              width: 100.0,
//              color: Colors.blue,
//            ),
//            new Container(
//              height: 50.0,
//              width: 50.0,
//              color: Colors.yellow,
//            )
//          ],
//        )
//      ],
//    );
//  }
//}

class _RowDemoState extends State<RowDemoState> {
  @override
  Widget build(BuildContext context) {
    return new Row(
      mainAxisSize: MainAxisSize.max,
      mainAxisAlignment: MainAxisAlignment.start,
      children: <Widget>[
        // 如果要让里面的Colum占满外部Colum，可以使用Expanded
        new Expanded(
            child: new Row(
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            new Container(
              height: 100.0,
              width: 100.0,
              color: Colors.blue,
            ),
            new Container(
              height: 50.0,
              width: 50.0,
              color: Colors.yellow,
            )
          ],
        ))
      ],
    );
  }
}
