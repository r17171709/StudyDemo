import 'package:flutter/material.dart';

void main() {
  runApp(new MyTabBarAppDemo());
}

class MyTabBarAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyTabBarAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("TabBarDemo"),
        ),
        body: new TabBarDemo(),
      ),
    );
  }
}

class TabBarDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _TabBarDemoState();
  }
}

class _TabBarDemoState extends State<TabBarDemo>
    with SingleTickerProviderStateMixin {
  TabController _tabController;

  @override
  void initState() {
    super.initState();
    _tabController = new TabController(length: 3, vsync: this);
    _tabController.addListener(() {});
  }

  @override
  void dispose() {
    super.dispose();
    _tabController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new TabBar(
          tabs: <Widget>[
            new Tab(
              text: "Home",
              icon: new Icon(Icons.home),
            ),
            new Tab(
              text: "Message",
              icon: new Icon(Icons.message),
            ),
            new Tab(
              text: "Mail",
              icon: new Icon(Icons.mail),
            ),
          ],
          controller: _tabController,
          labelColor: Colors.blue,
          unselectedLabelColor: Colors.grey,
          indicatorColor: Colors.blue[300],
        ),
        new Expanded(
            child: new TabBarView(children: <Widget>[
          new Center(
            child: new Text("Text1"),
          ),
          new Center(
            child: new Text("Text2"),
          ),
          new Center(
            child: new Text("Text3"),
          )
        ], controller: _tabController))
      ],
    );
  }
}
