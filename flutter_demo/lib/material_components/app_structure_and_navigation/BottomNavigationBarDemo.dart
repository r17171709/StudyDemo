import 'package:flutter/material.dart';

void main() {
  runApp(new MyBottomNavigationBarAppDemo());
}

class MyBottomNavigationBarAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyBottomNavigationBarAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("BottomNavigationBarDemo"),
        ),
        body: new Container(
          color: Colors.blue,
        ),
        bottomNavigationBar: new BottomNavigationBarDemo(),
        floatingActionButton: new FloatingActionButton(
          onPressed: () {},
          child: new Icon(Icons.add),
        ),
        floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      ),
    );
  }
}

class BottomNavigationBarDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _BottomNavigationBarDemoState();
  }
}

class _BottomNavigationBarDemoState extends State<BottomNavigationBarDemo> {
  int currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return new Theme(
        data: Theme.of(context).copyWith(
          // 此处为BottomNavigationBar的背景色
            canvasColor: Colors.white,
            textTheme: Theme.of(context).textTheme.copyWith(
              caption: new TextStyle(
                // 未被选中的文字颜色
                  color: Colors.grey),
            )),
        child: new BottomNavigationBar(
          items: <BottomNavigationBarItem>[
            new BottomNavigationBarItem(
                icon: new Icon(Icons.home), title: new Text("首页")),
            new BottomNavigationBarItem(
                icon: new Icon(Icons.local_activity), title: new Text("活动")),
            new BottomNavigationBarItem(
                icon: new Icon(Icons.message), title: new Text("消息")),
            new BottomNavigationBarItem(
                icon: new Icon(Icons.person), title: new Text("个人"))
          ],
          onTap: (index) {
            setState(() {
              currentIndex = index;
            });
          },
          // 选中的颜色
          fixedColor: Colors.blue,
          // BottomNavigationBar的展现样式。一定要设置
          type: BottomNavigationBarType.fixed,
          // 默认选中页
          currentIndex: currentIndex,
          // 图片尺寸
          iconSize: 24,
        ));
  }
}
