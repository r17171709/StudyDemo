import 'package:flutter/material.dart';

// 单节点的自定义布局的控件。通过提供的delegate，可以实现控制节点的位置以及尺寸。

// 可以控制child的布局Constraints
// 可以控制child的位置
// 可以决定自身的尺寸，但是自身的自身必须不能依赖child的尺寸。

void main() {
  runApp(new MyCustomSingleChildLayoutAppDemo());
}

class MyCustomSingleChildLayoutAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyCustomSingleChildLayoutAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("CustomSingleChildLayoutDemo"),
        ),
        body: new CustomSingleChildLayoutDemo(),
      ),
    );
  }
}

class CustomSingleChildLayoutDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new CustomSingleChildLayout(
      //
      delegate: new MySingleChildLayoutDelegate(Size(100, 100)),
      child: new Container(
        height: 300,
        width: 300,
        color: Colors.red,
      ),
    );
  }
}

class MySingleChildLayoutDelegate extends SingleChildLayoutDelegate {
  Size size;

  MySingleChildLayoutDelegate(this.size);

  @override
  Size getSize(BoxConstraints constraints) {
    return size;
  }

  @override
  BoxConstraints getConstraintsForChild(BoxConstraints constraints) {
    return new BoxConstraints.tight(size);
  }

  @override
  Offset getPositionForChild(Size size, Size childSize) {
    return new Offset(20, 20);
  }

  @override
  bool shouldRelayout(MySingleChildLayoutDelegate oldDelegate) {
    return oldDelegate.size != size;
  }
}
