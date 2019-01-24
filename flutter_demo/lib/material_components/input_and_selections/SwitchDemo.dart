import 'package:flutter/material.dart';

// 切换按钮

//  this.activeColor,           当按钮状态通激活态时, 按钮的背景颜色
//  this.activeTrackColor,      当按钮状态处于激活态时, 按钮的背景图像
//  this.inactiveThumbColor,    当按钮状态处于激活态时, 滑轨的颜色
//  this.inactiveTrackColor,    当按钮处于非激活状态时, 按钮的背景颜色, 与activeColor正好状态相反
//  this.activeThumbImage,      当按钮状态处于非激活态时, 按钮的背景图像
//  this.inactiveThumbImage,    当按钮状态处于非激活态时, 滑轨的颜色

void main() {
  runApp(new MySwitchAppDemo());
}

class MySwitchAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MySwitchAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("SwitchDemo"),
        ),
        body: new SwitchDemo(),
      ),
    );
  }
}

class SwitchDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new SwitchDefaultDemo(),
        new SwitchDefaultDemo2(),
        new SwitchDefaultDemo3(),
        new SwitchDefaultDemo4(),
        new SwitchDefaultDemo5()
      ],
    );
  }
}

class SwitchDefaultDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new SwitchDefaultDemoState();
  }
}

class SwitchDefaultDemoState extends State<SwitchDefaultDemo> {
  bool isChecked = false;

  @override
  Widget build(BuildContext context) {
    return new Switch(
        value: isChecked,
        onChanged: (bool value) {
          setState(() {
            isChecked = value;
          });
        });
  }
}

class SwitchDefaultDemo2 extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new SwitchDefaultDemoState2();
  }
}

class SwitchDefaultDemoState2 extends State<SwitchDefaultDemo2> {
  bool isChecked = false;

  @override
  Widget build(BuildContext context) {
    return new Switch(
        activeColor: Colors.blue,
        inactiveThumbColor: Colors.green,
        value: isChecked,
        onChanged: (bool value) {
          setState(() {
            isChecked = value;
          });
        });
  }
}

class SwitchDefaultDemo3 extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new SwitchDefaultDemoState3();
  }
}

class SwitchDefaultDemoState3 extends State<SwitchDefaultDemo3> {
  bool isChecked = false;

  @override
  Widget build(BuildContext context) {
    return new Switch(
        value: isChecked,
        activeTrackColor: Colors.yellow,
        inactiveTrackColor: Colors.blue,
        onChanged: (bool value) {
          setState(() {
            isChecked = value;
          });
        });
  }
}

class SwitchDefaultDemo4 extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new SwitchDefaultDemoState4();
  }
}

class SwitchDefaultDemoState4 extends State<SwitchDefaultDemo4> {
  bool isChecked = false;

  @override
  Widget build(BuildContext context) {
    return new Switch(
        value: isChecked,
        activeThumbImage: new NetworkImage(
            'https://flutter.io/images/homepage/header-illustration.png'),
        inactiveThumbImage: new NetworkImage(
            "https://flutter.io/images/homepage/screenshot-2.png"),
        onChanged: (bool value) {
          setState(() {
            isChecked = value;
          });
        });
  }
}

class SwitchDefaultDemo5 extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new SwitchDefaultDemoState5();
  }
}

class SwitchDefaultDemoState5 extends State<SwitchDefaultDemo5> {
  bool isChecked = false;

  @override
  Widget build(BuildContext context) {
    return new SwitchListTile(
      value: isChecked,
      onChanged: (bool value) {
        setState(() {
          isChecked = value;
        });
      },
      // 主标题
      title: new Text("Title"),
      // 副标题
      subtitle: new Text("subtitle"),
    );
  }
}
