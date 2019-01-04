import 'package:flutter/material.dart';

void main() {
  runApp(new MyPointEventAppDemo());
}

class MyPointEventAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyPointEventAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("PointEventDemo"),
        ),
        body: new PointEventDemo(),
      ),
    );
  }
}

class PointEventDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _PointEventDemoState();
  }
}

//class _PointEventDemoState extends State<PointEventDemo> {
//  PointerEvent _event;
//
//  @override
//  Widget build(BuildContext context) {
//    return new Listener(
//      child: new SizedBox(
//        width: 200.0,
//        height: 200.0,
//        child: new Text(_event == null ? "测试1" : _event.toString()),
//      ),
//      onPointerDown: (PointerDownEvent event) {
//        setState(() {
//          this._event = event;
//        });
//      },
//      onPointerMove: (PointerMoveEvent event) {
//        setState(() {
//          this._event = event;
//        });
//      },
//      onPointerUp: (PointerUpEvent event) {
//        setState(() {
//          this._event = event;
//        });
//      },
//      onPointerCancel: (PointerCancelEvent event) {
//        setState(() {
//          this._event = event;
//        });
//      },
//    );
//  }
//}

class _PointEventDemoState extends State<PointEventDemo> {
  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        Listener(
          child: ConstrainedBox(
            constraints: BoxConstraints.tight(Size(300.0, 200.0)),
            child: DecoratedBox(decoration: BoxDecoration(color: Colors.blue)),
          ),
          onPointerDown: (event) => print("down0"),
        ),
        Listener(
          child: ConstrainedBox(
            constraints: BoxConstraints.tight(Size(200.0, 100.0)),
            child: Center(child: Text("左上角200*100范围内非文本区域点击")),
          ),
          onPointerDown: (event) => print("down1"),
          behavior: HitTestBehavior.translucent, //放开此行注释后可以"点透"
        )
      ],
    );
  }
}
