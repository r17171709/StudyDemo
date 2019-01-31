import 'package:flutter/material.dart';

// 之前介绍过CustomSingleChildLayout，他们都是通过delegate去实现自定义布局，只不过这次是多节点的自定义布局的控件。通过提供的delegate，可以实现控制节点的位置以及尺寸。

// 可以决定每个child的布局约束条件；
// 可以决定每个child的位置；
// 可以决定自身的尺寸，但是自身的自身必须不能依赖child的尺寸。

void main() {
  runApp(new MyCustomMultiChildLayoutAppDemo());
}

class MyCustomMultiChildLayoutAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MYCustomMultiChildLayoutAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("CustomMultiChildLayoutDemo"),
        ),
        body: new CustomMultiChildLayout(
          delegate: new Delegate(),
          children: <Widget>[
            new LayoutId(
              child: new Container(
                height: 100,
                width: 100,
                color: Colors.yellow,
              ),
              id: Delegate.id1,
            ),
            new LayoutId(
                id: Delegate.id2,
                child: new Container(
                  height: 200,
                  width: 200,
                  color: Colors.blue,
                ))
          ],
        ),
      ),
    );
  }
}

class Delegate extends MultiChildLayoutDelegate {
  static const String id1 = "id1";
  static const String id2 = "id2";

  @override
  void performLayout(Size size) {
    BoxConstraints constraints = new BoxConstraints(maxWidth: size.width);

    Size id1Size = layoutChild(id1, constraints);
    positionChild(id1, new Offset(0, 0));

    double height = id1Size.height;
    layoutChild(id2, constraints);
    positionChild(id2, new Offset(0, height));
  }

  @override
  bool shouldRelayout(MultiChildLayoutDelegate oldDelegate) {
    return false;
  }
}
