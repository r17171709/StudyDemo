import 'package:flutter/material.dart';

// 横向方向排列，但是无法在横向方向进行滚动，与Column相对

//  MainAxisAlignment mainAxisAlignment              主轴方向对齐，Row的主轴方向是水平，Column的主轴是垂直方向
//                                                   spaceEvenly：将主轴方向空白区域均分，使得children之间空间相等，包括首尾children
//                                                   spaceAround：将主轴方向空白区域均分，使得children之间空间相等，但是首尾children的空白部分为一半
//                                                   spaceBetween：将主轴方向空白区域均分，使得children之间空间相等，但是首尾childre紧贴两端不留细逢
//                                                   start：将children放置在主轴起点方向
//                                                   end：将children放置在主轴末尾方向
//                                                   center：将children放置在主轴中间方向
//  MainAxisSize mainAxisSize                        主轴的长度。若为MainAxisSize.min则会缩小到横轴上children的尺寸总和，反之为最大宽度
//  CrossAxisAlignment crossAxisAlignment            次轴对齐方式，对齐的属性和mainAxisAlignment一样
//                                                   center/end/start: children在交叉轴上居中/末端/起点
//                                                   stretch：让children填满交叉轴方向
//                                                   baseline：在交叉轴方向，使得于这个baseline对齐
//  TextDirection textDirection                      排列方向
void main() {
  runApp(new MyRowAppDemo());
}

class MyRowAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyRowDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("RowDemo"),
        ),
        body: new RowDemo(),
      ),
    );
  }
}

class RowDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Row(
      mainAxisSize: MainAxisSize.max,
      mainAxisAlignment: MainAxisAlignment.center,
      textDirection: TextDirection.rtl,
      crossAxisAlignment: CrossAxisAlignment.end,
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
    );
  }
}

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

//class RowDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return new Row(
//      children: <Widget>[
//        // 直接使用Expanded
//        new Expanded(
//          child: new Container(
//            height: 100.0,
//            width: 100.0,
//            color: Colors.blue,
//          ),
//        ),
//      ],
//      // Expanded会使得该值失去作用
//      mainAxisSize: MainAxisSize.min,
//    );
//  }
//}

//class RowDemo extends StatelessWidget {
//  @override
//  Widget build(BuildContext context) {
//    return Row(
//      children: <Widget>[
//        const FlutterLogo(),
//        // 会出现溢出，Flutter以黑色黄色条纹做出警告。你可以选择使用Expanded或者Flexible来解决这个问题
////        const Text(
////            'Flutter\'s hot reload helps you quickly and easily experiment, build UIs, add features, and fix bug faster. Experience sub-second reload times, without losing state, on emulators, simulators, and hardware for iOS and Android.'),
//        new Expanded(
//          child: const Text(
//              'Flutter\'s hot reload helps you quickly and easily experiment, build UIs, add features, and fix bug faster. Experience sub-second reload times, without losing state, on emulators, simulators, and hardware for iOS and Android.'),
//        ),
//        new Flexible(
//          child: const Text(
//              'Flutter\'s hot reload helps you quickly and easily experiment, build UIs, add features, and fix bug faster. Experience sub-second reload times, without losing state, on emulators, simulators, and hardware for iOS and Android.'),
//        ),
//        const Icon(Icons.sentiment_very_satisfied),
//      ],
//    );
//  }
//}
