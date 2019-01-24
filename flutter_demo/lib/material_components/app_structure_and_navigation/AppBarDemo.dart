import 'package:flutter/material.dart';

void main() {
  runApp(new MyAppBarAppDemo());
}

class MyAppBarAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyAppBarAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        body: new AppBarDemo(),
      ),
    );
  }
}

enum Country { CHINA, USA, UK, RUSSIA }

class AppBarDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _AppBarDemoState();
  }
}

class _AppBarDemoState extends State<AppBarDemo>
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
    AppBar appBar = new AppBar(
      title: new Text("AppBarDemo"),
      leading: new Icon(Icons.arrow_back),
      backgroundColor: Colors.blue,
      centerTitle: true,
      actions: <Widget>[
        new IconButton(icon: new Icon(Icons.add), onPressed: () {}),
        new IconButton(icon: new Icon(Icons.home), onPressed: () {}),
        new PopupMenuButton(
          itemBuilder: (BuildContext context) {
            return <PopupMenuEntry<Country>>[
              new PopupMenuItem(
                child: new Text("CHINA"),
                value: Country.CHINA,
              ),
              new PopupMenuItem(
                child: new Text("USA"),
                value: Country.USA,
              ),
              new PopupMenuItem(
                child: new Text("UK"),
                value: Country.UK,
              ),
              new PopupMenuItem(
                child: new Text("RUSSIA"),
                value: Country.RUSSIA,
              )
            ];
          },
          onSelected: (Country country) {},
        )
      ],
      bottom: new TabBar(
        tabs: <Tab>[
          new Tab(
            text: "No.1",
          ),
          new Tab(
            text: "No.2",
          ),
          new Tab(
            text: "No.3",
          )
        ],
        controller: _tabController,
      ),
    );
    return new Column(
      children: <Widget>[
        new Container(
          height: appBar.preferredSize.height,
          child: appBar,
        )
      ],
    );
  }
}
