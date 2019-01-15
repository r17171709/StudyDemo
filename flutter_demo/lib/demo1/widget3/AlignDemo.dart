import 'package:flutter/material.dart';

//  this.alignment       对齐方式，系统预设了9种对齐方式，也可以自定义Alignment(0.4,0.5)，其中第一个参数-1.0是左边对齐，1.0是右边对齐，第二个参数，-1.0是顶部对齐，1.0是底部对齐
//  this.widthFactor,    宽是子组件的倍数。Align的宽度就是child的宽度乘以这个值
//  this.heightFactor,   高是子组件的倍数。Align的高度就是child的高度乘以这个值
//  当widthFactor和heightFactor为null的时候，当其没有限制条件的时候，Align会尽量的扩展自己的尺寸；当有限制条件的时候，会根据限制条件调整到相应的尺寸
//  当widthFactor或者heightFactor不为null的时候，Aligin会根据factor属性，扩展自己的尺寸，例如设置widthFactor为2.0的时候，那么，Align的宽度将会是child的两倍。
//  Center继承自Align，只不过是将alignment设置为Alignment.center，其他属性例如widthFactor、heightFactor，布局行为，都与Align完全一样

void main() {
  runApp(new MyAlignAppDemo());
}

class MyAlignAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyAlignAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("AlignDemo"),
        ),
        body: new AlignDemo(),
      ),
    );
  }
}

class AlignDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _AlignDemoState();
  }
}

//class _AlignDemoState extends State<AlignDemo> {
//  @override
//  Widget build(BuildContext context) {
//    return new Container(
//      color: Colors.green,
//      child: new Align(
//        widthFactor: 2.0,
//        heightFactor: 2.0,
//        alignment: Alignment.center,
//        child: new Text("AlignDemo"),
//      ),
//    );
//  }
//}

class _AlignDemoState extends State<AlignDemo> {
  @override
  Widget build(BuildContext context) {
    return new Container(
      height: 200.0,
      width: 200.0,
      color: Colors.green,
      child: new Align(
        widthFactor: 3.0,
        heightFactor: 3.0,
        alignment: new Alignment(0.5, 0.5),
        child: new Text("AlignDemo"),
      ),
    );
  }
}