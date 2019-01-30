import 'package:flutter/material.dart';

//    this.alignment,
//    this.minWidth,   允许child的最小宽度。如果child宽度小于这个值，则按照最小宽度进行显示
//    this.maxWidth,   允许child的最大宽度。如果无论child宽度大于还是小于这个值，均按照最小宽度进行显示
//    this.minHeight,  允许child的最小高度。如果child高度小于这个值，则按照最小高度进行显示
//    this.maxHeight,  允许child的最大高度。如果无论child高度大于还是小于这个值，均按照最小高度进行显示

void main() {
  runApp(new MyOverflowBoxAppDemo());
}

class MyOverflowBoxAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyOverflowBoxAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("OverflowBoxDemo"),
        ),
        body: new OverflowBoxDemo(),
      ),
    );
  }
}

//class OverflowBoxDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Container(
//      color: Colors.black,
//      height: 200,
//      width: 200,
//      child: new OverflowBox(
//        minHeight: 150,
//        minWidth: 150,
//        alignment: Alignment.center,
//        child: new Container(
//          height: 100,
//          width: 100,
//          color: Color(0x33FF00FF),
//        ),
//      ),
//    );
//  }
//}

class OverflowBoxDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Container(
      color: Colors.black,
      height: 200,
      width: 200,
      child: new OverflowBox(
        maxHeight: 350,
        maxWidth: 350,
        alignment: Alignment.center,
        child: new Container(
          height: 250,
          width: 250,
          color: Color(0x33FF00FF),
        ),
      ),
    );
  }
}

//class OverflowBoxDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Container(
//      color: Colors.black,
//      height: 200,
//      width: 200,
//      child: new OverflowBox(
//        maxHeight: 350,
//        maxWidth: 350,
//        alignment: Alignment.center,
//        child: new Container(
//          height: 450,
//          width: 450,
//          color: Color(0x33FF00FF),
//        ),
//      ),
//    );
//  }
//}