import 'package:flutter/material.dart';

//    Key key,                        组件唯一标识符
//    this.alignment,                 控制child的对齐方式
//    this.padding,
//    Color color,                    背景色
//    Decoration decoration,          绘制在child下层的装饰，不能与color同时使用
//    this.foregroundDecoration,      绘制在child上层的装饰
//    double width,
//    double height,
//    BoxConstraints constraints,     添加到child上额外的约束条件
//    this.margin,
//    this.transform,                 设置变换矩阵，类型为Matrix4
//    this.child,

void main() {
  runApp(new MyContainerAppDemo());
}

class MyContainerAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyContainerAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("ContainerDemo"),
        ),
        body: new ContainerDemo(),
      ),
    );
  }
}

// Container在没有子节点（child）的时候，会试图去变得足够大。
//class ContainerDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Container(
//      color: Colors.black,
//    );
//  }
//}

// 没有子节点的Container构造器中如果包含了width、height或者constraints，则会按照构造器中的参数以及父节点的限制，将自身调节到足够小
//class ContainerDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Container(
//      color: Colors.black,
//      height: 50.0,
//      width: 50.0,
//    );
//  }
//}

// 带子节点的Container，会根据子节点尺寸调节自身尺寸
class ContainerDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Container(
      color: Colors.black,
      child: new Container(
        decoration: new BoxDecoration(
            color: Colors.red,
            borderRadius: BorderRadius.all(Radius.circular(10.0)),
            gradient:
                new LinearGradient(colors: <Color>[Colors.green, Colors.blue]),
            boxShadow: <BoxShadow>[
              new BoxShadow(
                  color: Colors.grey,
                  offset: new Offset(4.0, 4.0),
                  blurRadius: 4.0)
            ]),
        height: 150.0,
        width: 150.0,
        alignment: AlignmentDirectional.center,
        child: new Text(
          "ContainerDemo",
          style: new TextStyle(color: Colors.blue),
        ),
      ),
    );
  }
}

// 带子节点的Container构造器中如果包含了width、height或者constraints，则会按照构造器中的参数来进行尺寸的调节
//class ContainerDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Container(
//      color: Colors.black,
//      height: 50.0,
//      width: 50.0,
//      child: new Container(
//        decoration: new BoxDecoration(
//            color: Colors.red,
//            borderRadius: BorderRadius.all(Radius.circular(10.0)),
//            gradient:
//                new LinearGradient(colors: <Color>[Colors.green, Colors.blue]),
//            boxShadow: <BoxShadow>[
//              new BoxShadow(
//                  color: Colors.grey,
//                  offset: new Offset(4.0, 4.0),
//                  blurRadius: 4.0)
//            ]),
//        height: 150.0,
//        width: 150.0,
//        alignment: AlignmentDirectional.center,
//        child: new Text(
//          "ContainerDemo",
//          style: new TextStyle(color: Colors.blue),
//        ),
//      ),
//    );
//  }
//}

// 简而言之，单身的时候，想怎样就怎样。有了孩子之后，一切围着孩子转，除非给孩子加限制，孩子才跟着你转

//class ContainerDemo extends StatefulWidget {
//  @override
//  State<StatefulWidget> createState() {
//    return new _ContainerDemoState();
//  }
//}
//
//class _ContainerDemoState extends State<ContainerDemo> {
//  Color _color = Colors.blue;
//
//  @override
//  Widget build(BuildContext context) {
//    print("build");
//    return new GestureDetector(
//      child: new Container(
////        transform: Matrix4.rotationY(30.0),
//        constraints: new BoxConstraints.tight(new Size(double.infinity, 50.0)),
//        decoration: new BoxDecoration(
//          color: _color,
//          borderRadius: BorderRadius.all(Radius.circular(8.0)),
//          border: Border.all(color: Colors.black, width: 1.0),
//        ),
//        child: new Text(
//          "点我吧",
//          style: new TextStyle(color: Colors.white, fontSize: 24.0),
//        ),
//        alignment: Alignment.center,
//        margin: EdgeInsets.all(8.0),
//      ),
//      onTapDown: (TapDownDetails details) {
//        setState(() {
//          _color = Colors.blue[700];
//        });
//      },
//      onTapUp: (TapUpDetails details) {
//        setState(() {
//          _color = Colors.blue;
//        });
//      },
//    );
//  }
//}
