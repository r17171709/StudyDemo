import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

//    this.onTapDown,
//    this.onTapUp,
//    this.onTap,                   点击
//    this.onTapCancel,
//    this.onDoubleTap,             双击
//    this.onLongPress,             长按
//    this.onLongPressUp,
//    this.onVerticalDragDown,
//    this.onVerticalDragStart,
//    this.onVerticalDragUpdate,    单一垂直方向拖动事件
//    this.onVerticalDragEnd,
//    this.onVerticalDragCancel,
//    this.onHorizontalDragDown,
//    this.onHorizontalDragStart,
//    this.onHorizontalDragUpdate,  单一水平方向拖动事件
//    this.onHorizontalDragEnd,
//    this.onHorizontalDragCancel,
//    this.onPanDown,               任意方向行为上滑动、拖动手指按下时
//    this.onPanStart,
//    this.onPanUpdate,             任意方向行为上手指滑动时
//    this.onPanEnd,                任意方向行为上手指松开时
//    this.onPanCancel,
//    this.onScaleStart,
//    this.onScaleUpdate,           缩放时
//    this.onScaleEnd,

void main() {
  runApp(new MyGestureAppDemo());
}

class MyGestureAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyGestureAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("GestureDemo"),
        ),
        body: new GestureDemo(),
      ),
    );
  }
}

class GestureDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _GestureDemoState();
  }
}

//class _GestureDemoState extends State<GestureDemo> {
//  @override
//  Widget build(BuildContext context) {
//    return new GestureDetector(
//      child: new Container(
//        color: Colors.red,
//        constraints: new BoxConstraints.tight(new Size(300, 300)),
//      ),
//      onTap: () {
//        print("onTap");
//      },
//      onTapDown: (TapDownDetails details) {
//        print("onTapDown");
//      },
//      onTapUp: (TapUpDetails details) {
//        print("onTapUp");
//      },
//      onTapCancel: () {
//        print("onTapCancel");
//      },
//    );
//  }
//}

//class _GestureDemoState extends State<GestureDemo> {
//  @override
//  Widget build(BuildContext context) {
//    return new GestureDetector(
//      child: new Container(
//        color: Colors.red,
//        constraints: new BoxConstraints.tight(new Size(300, 300)),
//      ),
//      onDoubleTap: () {
//        print("onDoubleTap");
//      },
//    );
//  }
//}

//class _GestureDemoState extends State<GestureDemo> {
//  @override
//  Widget build(BuildContext context) {
//    return new GestureDetector(
//      child: new Container(
//        color: Colors.red,
//        constraints: new BoxConstraints.tight(new Size(300, 300)),
//      ),
//      onLongPress: () {
//        print("onLongPress");
//      },
//      onLongPressUp: () {
//        print("onLongPressUp");
//      },
//    );
//  }
//}

//class _GestureDemoState extends State<GestureDemo> {
//  @override
//  Widget build(BuildContext context) {
//    return new GestureDetector(
//      child: new Container(
//        color: Colors.red,
//        constraints: new BoxConstraints.tight(new Size(300, 300)),
//      ),
//      onVerticalDragDown: (DragDownDetails details) {
//        print("onVerticalDragDown");
//      },
//      onVerticalDragStart: (DragStartDetails details) {
//        print("onVerticalDragStart");
//      },
//      onVerticalDragUpdate: (DragUpdateDetails details) {
//        print("onVerticalDragUpdate");
//      },
//      onVerticalDragEnd: (DragEndDetails details) {
//        print("onVerticalDragEnd");
//      },
//      onVerticalDragCancel: () {
//        print("onVerticalDragCancel");
//      },
//    );
//  }
//}

//class _GestureDemoState extends State<GestureDemo> {
//  @override
//  Widget build(BuildContext context) {
//    return new GestureDetector(
//      child: new Container(
//        color: Colors.red,
//        constraints: new BoxConstraints.tight(new Size(300, 300)),
//      ),
//      onHorizontalDragDown: (DragDownDetails details) {
//        print("onHorizontalDragDown");
//      },
//      onHorizontalDragStart: (DragStartDetails details) {
//        print("onHorizontalDragStart");
//      },
//      onHorizontalDragUpdate: (DragUpdateDetails details) {
//        print("onHorizontalDragUpdate");
//      },
//      onHorizontalDragEnd: (DragEndDetails details) {
//        print("onHorizontalDragEnd");
//      },
//      onHorizontalDragCancel: () {
//        print("onHorizontalDragCancel");
//      },
//    );
//  }
//}

//class _GestureDemoState extends State<GestureDemo> {
//  @override
//  Widget build(BuildContext context) {
//    return new GestureDetector(
//      child: new Container(
//        color: Colors.red,
//        constraints: new BoxConstraints.tight(new Size(300, 300)),
//      ),
//      onPanDown: (DragDownDetails details) {
//        print("onPanDown");
//      },
//      onPanStart: (DragStartDetails details) {
//        print("onPanStart");
//      },
//      onPanUpdate: (DragUpdateDetails details) {
//        print("onPanUpdate");
//      },
//      onPanEnd: (DragEndDetails details) {
//        print("onPanEnd");
//      },
//      onPanCancel: () {
//        print("onPanCancel");
//      },
//    );
//  }
//}

//class _GestureDemoState extends State<GestureDemo> {
//  TapGestureRecognizer _recognizer = new TapGestureRecognizer();
//
//  @override
//  void dispose() {
//    super.dispose();
//    _recognizer.dispose();
//  }
//
//  @override
//  Widget build(BuildContext context) {
//    return new Text.rich(
//      new TextSpan(
//          style: new TextStyle(
//            color: Colors.blue,
//          ),
//          children: <TextSpan>[
//            new TextSpan(
//                text: "TextSpan",
//                recognizer: _recognizer
//                  ..onTap = () {
//                    print("onTap");
//                  })
//          ]),
//    );
//  }
//}

// 不会造成手势冲突
class _GestureDemoState extends State<GestureDemo> {
  @override
  Widget build(BuildContext context) {
    return new Listener(
      onPointerDown: (PointerDownEvent event) {
        print("onPointerDown");
      },
      onPointerMove: (PointerMoveEvent event) {
        print("onPointerMove");
      },
      onPointerUp: (PointerUpEvent event) {
        print("onPointerUp");
      },
      onPointerCancel: (PointerCancelEvent event) {
        print("onPointerCancel");
      },
      child: new GestureDetector(
        onHorizontalDragDown: (DragDownDetails details) {
          print("onHorizontalDragDown");
        },
        onHorizontalDragStart: (DragStartDetails details) {
          print("onHorizontalDragStart");
        },
        onHorizontalDragUpdate: (DragUpdateDetails details) {
          print("onHorizontalDragUpdate");
        },
        onHorizontalDragEnd: (DragEndDetails details) {
          print("onHorizontalDragEnd");
        },
        onHorizontalDragCancel: () {
          print("onHorizontalDragCancel");
        },
        child: new Container(
          color: Colors.red,
          constraints: new BoxConstraints.tight(new Size(300, 300)),
        ),
      ),
    );
  }
}
