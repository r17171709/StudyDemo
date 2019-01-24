import 'package:flutter/material.dart';

// 复选框。当其状态发生变化时会调用onChanged回调。

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
        body: new Row(
          children: <Widget>[
            new CheckBoxDemo(),
            new SizedBox(
              child: new CheckboxListTileDemo(),
              width: 200,
            )
          ],
        ),
      ),
    );
  }
}

class CheckBoxDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _CheckBoxDemoState();
  }
}

class _CheckBoxDemoState extends State<CheckBoxDemo> {
  bool check = false;

  @override
  Widget build(BuildContext context) {
    return new Checkbox(
        value: check,
        // 选中的颜色
        activeColor: Colors.blue,
        // 点击区域 padded：向四周扩展48px区域 shrinkWrap：控件区域
        materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
        onChanged: (bool value) {
          setState(() {
            check = !check;
            Scaffold.of(context).showSnackBar(
                new SnackBar(content: new Text(value.toString())));
          });
        });
  }
}

class CheckboxListTileDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _CheckboxListTileDemoState();
  }
}

class _CheckboxListTileDemoState extends State<CheckboxListTileDemo> {
  bool check = false;

  @override
  Widget build(BuildContext context) {
    return new CheckboxListTile(
        value: check,
        // 文字是否对齐图标高度
        dense: false,
        // 默认文字是否高亮
        selected: true,
        // 文字是否三行显示
        isThreeLine: false,
        // 将控件放在何处相对于文本。leading：按钮显示在文字后面，platform、trailing：按钮显示在文字前面
        controlAffinity: ListTileControlAffinity.leading,
        onChanged: (bool value) {
          setState(() {
            check = value;
          });
        },
        // 主标题
        title: new Text("Title"),
        // 副标题
        subtitle: new Text("subtitle"),
        // 选中复选框的颜色
        activeColor: Colors.blue,
        // 额外的图标
        secondary: Icon(Icons.archive));
  }
}
