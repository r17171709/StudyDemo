import 'package:flutter/material.dart';

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

class _GestureDemoState extends State<GestureDemo> {
  @override
  Widget build(BuildContext context) {
    return new Listener(
      child: new GestureDetector(
        child: new ConstrainedBox(
          constraints: new BoxConstraints.tight(new Size(300.0, 300.0)),
          child: new DecoratedBox(
            decoration: new BoxDecoration(
              color: Colors.red,
            ),
            child: new Align(
              child: new Text("GestureDemo"),
            ),
          ),
        ),
//      onTap: () {
//        print("点击");
//      },
//      onDoubleTap: () {
//        print("双击");
//      },
//      onLongPress: () {
//        print("长按");
//      },
        //
//      onPanStart: (DragStartDetails details) {
//        print("onPanStart ${details.globalPosition}");
//      },
//      onPanDown: (DragDownDetails details) {
//        print("onPanDown ${details.globalPosition}");
//      },
//      onPanEnd: (DragEndDetails details) {
//        print("onPanEnd ${details.velocity}");
//      },
//      onPanUpdate: (DragUpdateDetails details) {
//        print("onPanUpdate ${details.delta.dx}  ${details.delta.dy}");
//      },
//      onPanCancel: () {
//        print("onPanCancel");
//      },
        //
//      onVerticalDragDown: (DragDownDetails details) {
//        print("onVerticalDragDown ${details.globalPosition}");
//      },
//      onVerticalDragStart: (DragStartDetails details) {
//        print("onVerticalDragStart ${details.globalPosition}");
//      },
//      onVerticalDragUpdate: (DragUpdateDetails details) {
//        print("onVerticalDragUpdate ${details.globalPosition}");
//      },
//      onVerticalDragEnd: (DragEndDetails details) {
//        print("onVerticalDragEnd ${details.velocity}");
//      },
//      onVerticalDragCancel: () {
//        print("onVerticalDragCancel");
//      },
        //
        onScaleStart: (ScaleStartDetails details) {
          print("onScaleStart");
        },
        onScaleUpdate: (ScaleUpdateDetails details) {
          print("onScaleUpdate ${details.scale}");
        },
        onScaleEnd: (ScaleEndDetails details) {
          print("onScaleEnd");
        },
        onTapDown: (TapDownDetails details) {},
      ),
      onPointerDown: (PointerDownEvent event) {
        print("onPointerDown");
      },
      onPointerUp: (PointerUpEvent event) {
        print("onPointerUp");
      },
    );
  }
}
