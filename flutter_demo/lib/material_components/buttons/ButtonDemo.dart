import 'package:flutter/material.dart';

void main() {
  runApp(new MyButtonDemoApp());
}

class MyButtonDemoApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyButtonDemoApp",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(title: new Text("ButtonDemo")),
        floatingActionButton: new FloatingActionButtonDemo(),
        // FAB位置
        floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
        body: new ButtonDemo(),
      ),
    );
  }
}

class ButtonDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        new RaisedButtonDemo(),
        new RaisedButtonIconDemo(),
        new FlatButtonDemo(),
        new OutlineButtonDemo(),
        new IconButtonDemo(),
        new FloatingActionButtonExtendedDemo(),
        new PopupMenuButtonDemo(),
        new ButtonBarDemo()
      ],
    );
  }
}

// 浮起来的按钮
class RaisedButtonDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    RaisedButton button = new RaisedButton(
      // 通过设置onPressed的值来达到是否禁用RaisedButton的目的
      onPressed: () {},
      // 按钮文字颜色，如果没有设置字体自身的颜色时才会起作用
//          textColor: Colors.white,
      // 按钮默认颜色
      color: Colors.blue,
      //
      disabledTextColor: Colors.blue[100],
      // 选中后高亮模式下的颜色
      highlightColor: Colors.blue[700],
      // 水波纹颜色
      splashColor: Colors.white,
      // 如果没有设置字体自身的颜色或textColor时才会起作用，则此值代表RaisedButton中文字的颜色
      colorBrightness: Brightness.dark,
      // 按钮形状
      shape: new RoundedRectangleBorder(
          borderRadius: BorderRadius.all(Radius.circular(10))),
//      shape: new Border.all(color: Colors.red, width: 3),
      // 未高亮时候阴影的距离 同理还有disabledElevation、highlightElevation
      elevation: 10,
      // 选中与非选中监听
      onHighlightChanged: (bool value) {
        print(value);
      },
      child: new Text(
        "RaisedButtonDemo",
      ),
    );
    return new Padding(padding: EdgeInsets.all(10.0), child: button);
  }
}

// 带Icon的浮起来的按钮
class RaisedButtonIconDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new RaisedButton.icon(
        onPressed: () {},
        color: Colors.blue,
        highlightColor: Colors.blue[700],
        colorBrightness: Brightness.dark,
        shape: new RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(10))),
        icon: new Icon(Icons.add),
        label: new Text(
          "RaisedButtonIconDemo",
        ));
  }
}

// 扁平化的按钮
class FlatButtonDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new FlatButton(
      onPressed: () {
        print("FlatButtonDemo");
      },
      child: new Text("FlatButtonDemo"),
    );
  }
}

// 带边框的Button
class OutlineButtonDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new OutlineButton(
      onPressed: () {
        print("OutlineButtonDemo");
      },
      child: new Text("OutlineButtonDemo"),
    );
  }
}

// 图标按钮
class IconButtonDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new IconButton(icon: new Icon(Icons.print), onPressed: () {});
  }
}

// FAB
class FloatingActionButtonDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new FloatingActionButton(
      onPressed: () {
        Scaffold.of(context)
            .showSnackBar(new SnackBar(content: new Text("FAB")));
      },
      // 是否采用小一号的FAB(56逻辑像素跟40逻辑像素的区别)
      mini: false,
      // FAB的文字解释，FAB被长按时显示，也是无障碍功能
      tooltip: "Add",
      child: new Icon(Icons.add),
      // 前景色  如果没有设置Icon的颜色，那么前景色设置有效，即child的透明镂空颜色
      foregroundColor: Colors.red,
      // 背景色  如果没有设置背景色，默认以theme中的颜色展现
      backgroundColor: Colors.blue,
      // 未高亮时候阴影的距离 同理还有highlightElevation
      elevation: 6,
      highlightElevation: 10,
      // FAB形状
//      shape: new RoundedRectangleBorder(
//          borderRadius: BorderRadius.all(Radius.circular(10))),
    );
  }
}

// 带图标和文字的按钮
class FloatingActionButtonExtendedDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new FloatingActionButton.extended(
      onPressed: () {
        Scaffold.of(context)
            .showSnackBar(new SnackBar(content: new Text("FAB")));
      },
      tooltip: "Add",
      icon: new Icon(Icons.print),
      label: new Text("Add"),
      backgroundColor: Colors.blue,
      elevation: 6,
      highlightElevation: 10,
    );
  }
}

enum No { No1, No2, No3, No4 }

class PopupMenuButtonDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new PopupMenuButton(
      itemBuilder: (BuildContext context) {
        return <PopupMenuEntry<No>>[
          new PopupMenuItem(
            // 显示出的文字
            child: new Text("1"),
            // onSelected中的入参
            value: No.No1,
          ),
          new PopupMenuItem(
            child: new Text("2"),
            value: No.No2,
          ),
          new PopupMenuItem(
            child: new Text("3"),
            value: No.No3,
          ),
          new PopupMenuItem(
            child: new Text("4"),
            value: No.No4,
          )
        ];
      },
      // 选择某一项
      onSelected: (No value) {
        Scaffold.of(context)
            .showSnackBar(SnackBar(content: new Text(value.toString())));
      },
      // 显示出来的图标
      icon: new Icon(Icons.select_all),
      // 未选择某一项
      onCanceled: () {
        Scaffold.of(context)
            .showSnackBar(SnackBar(content: new Text("取消")));
      },
    );
  }
}

// 一组带边距的横排显示组件组合
class ButtonBarDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new ButtonBar(
      // 组件对齐方式
      alignment: MainAxisAlignment.center,
      // 组件横向铺满还是以children的实际尺寸展现
      mainAxisSize: MainAxisSize.min,
      children: <Widget>[
        new Text("Hello"),
        new Text("World"),
      ],
    );
  }
}
