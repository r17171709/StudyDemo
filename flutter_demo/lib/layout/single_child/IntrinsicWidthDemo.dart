import 'package:flutter/material.dart';

//  this.stepWidth   宽度步进。宽度将会是stepWidth的倍数。为null的时候，child的宽度是child的最小宽度
//  this.stepHeight  高度步进。高度将会是stepHeight的倍数。为null的时候，高度取最大高度。

void main() {
  runApp(new MyIntrinsicWidthAppDemo());
}

class MyIntrinsicWidthAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyIntrinsicWidthAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("IntrinsicWidthDemo"),
        ),
        body: new IntrinsicWidthDemo(),
      ),
    );
  }
}

//class IntrinsicWidthDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Container(
//      color: Colors.green,
//      // 250*max
//      child: new IntrinsicWidth(
//        child: new Column(
//          children: <Widget>[
//            new Container(
//              height: 100,
//              width: 150,
//              color: Colors.red,
//            ),
//            new Container(
//              height: 100,
//              width: 250,
//              color: Colors.black,
//            ),
//            new Container(
//              height: 100,
//              width: 200,
//              color: Colors.blue,
//            )
//          ],
//        ),
//      ),
//    );
//  }
//}

class IntrinsicWidthDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Container(
      color: Colors.green,
      // 250*400
      child: new IntrinsicWidth(
        stepHeight: 100,
        child: new Column(
          children: <Widget>[
            new Container(
              height: 150,
              width: 150,
              color: Colors.red,
            ),
            new Container(
              height: 100,
              width: 250,
              color: Colors.black,
            ),
            new Container(
              height: 100,
              width: 200,
              color: Colors.blue,
            )
          ],
        ),
      ),
    );
  }
}

//class IntrinsicWidthDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Container(
//      color: Colors.green,
//      // 300*max
//      child: new IntrinsicWidth(
//        stepWidth: 100,
//        child: new Column(
//          children: <Widget>[
//            new Container(
//              height: 100,
//              width: 150,
//              color: Colors.red,
//            ),
//            new Container(
//              height: 100,
//              width: 250,
//              color: Colors.black,
//            ),
//            new Container(
//              height: 100,
//              width: 200,
//              color: Colors.blue,
//            )
//          ],
//        ),
//      ),
//    );
//  }
//}

//class IntrinsicWidthDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Container(
//      color: Colors.green,
//      // 300*360
//      child: new IntrinsicWidth(
//        stepHeight: 120,
//        stepWidth: 100,
//        child: new Column(
//          children: <Widget>[
//            new Container(
//              height: 100,
//              width: 150,
//              color: Colors.red,
//            ),
//            new Container(
//              height: 100,
//              width: 250,
//              color: Colors.black,
//            ),
//            new Container(
//              height: 100,
//              width: 200,
//              color: Colors.blue,
//            )
//          ],
//        ),
//      ),
//    );
//  }
//}
