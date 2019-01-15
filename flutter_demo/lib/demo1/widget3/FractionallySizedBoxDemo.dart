import 'package:flutter/material.dart';

//    this.alignment         对齐方式
//    this.widthFactor,      宽度因子。父控件宽度乘以这个值，就是最后的宽度
//    this.heightFactor,     高度因子。父控件高度乘以这个值，就是最后的高度
//    其中widthFactor和heightFactor都有一个规则：如果不为null，那么实际的最大宽高度则为child的宽高乘以这个因子；如果为null，那么child的宽高则会尽量充满整个区域

void main() {
  runApp(new MyFractionallySizedBoxAppDemo());
}

class MyFractionallySizedBoxAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyFractionallySizedBoxAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("FractionallySizedBoxDemo"),
        ),
        body: new FractionallySizedBoxDemo(),
      ),
    );
  }
}

class FractionallySizedBoxDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Container(
      color: Colors.blue,
      height: 300.0,
      width: 300.0,
      child: new FractionallySizedBox(
        alignment: Alignment.topLeft,
        widthFactor: 1.1,
        heightFactor: 0.1,
        child: new Container(
          color: Colors.black,
          height: 200,
          width: 200,
        ),
      ),
    );
  }
}
