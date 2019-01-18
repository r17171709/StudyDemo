import 'package:flutter/material.dart';

void main() {
  runApp(new MyCheckBoxAppDemo());
}

class MyCheckBoxAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyCheckBoxAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("CheckBoxDemo"),
        ),
        body: new CheckBoxDemo(),
      ),
    );
  }
}

class CheckBoxDemo extends StatefulWidget {
  bool check = false;

  @override
  State<StatefulWidget> createState() {
    return new _CheckBoxDemoState();
  }
}

class _CheckBoxDemoState extends State<CheckBoxDemo> {
  @override
  Widget build(BuildContext context) {
    return new Checkbox(
        value: widget.check,
        // 选中的颜色
        activeColor: Colors.blue,
        // 点击区域 padded：向四周扩展48px区域 shrinkWrap：控件区域
        materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
        onChanged: (bool value) {
          setState(() {
            widget.check = !widget.check;
            Scaffold.of(context).showSnackBar(
                new SnackBar(content: new Text(value.toString())));
          });
        });
  }
}
