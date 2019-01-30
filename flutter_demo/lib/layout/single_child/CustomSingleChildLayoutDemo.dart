import 'package:flutter/material.dart';

// 提供了一个控制child布局的delegate，这个delegate可以控制这些因素：
// 可以控制child的布局Constraints
// 可以控制child的位置

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
