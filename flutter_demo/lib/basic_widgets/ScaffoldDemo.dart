import 'package:flutter/material.dart';
import 'package:flutter_demo/material_components/app_structure_and_navigation/BottomNavigationBarDemo.dart';

// Scaffold实现了基本的Material Design布局结构，比如抽屉（Drawers）、SnackBars、以及BottomSheets。

void main() {
  runApp(new MyScaffoldAppDemo());
}

class MyScaffoldAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyScaffoldAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new ScaffoldDemo(),
    );
  }
}

class ScaffoldDemo extends StatefulWidget {
  final GlobalKey<ScaffoldState> _globalKey = new GlobalKey<ScaffoldState>();

  @override
  State<StatefulWidget> createState() {
    return new _ScaffoldDemoState();
  }
}

class _ScaffoldDemoState extends State<ScaffoldDemo> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      key: widget._globalKey,
      appBar: new AppBar(
        title: new Text("ScaffoldDemo"),
      ),
      floatingActionButton: new FloatingActionButton(
        onPressed: () {
          widget._globalKey.currentState.openDrawer();
        },
        child: new Icon(Icons.add),
      ),
      bottomNavigationBar: new BottomNavigationBarDemo(),
      drawer: new Drawer(
        // 使用Column包裹一层，这样尺寸就不会不受约束了
        child: new Column(
          children: <Widget>[
            new Image.network(
              "http://g.hiphotos.baidu.com/image/h%3D300/sign=5cdfa7cca5d3fd1f2909a43a004f25ce/d833c895d143ad4b0f4772228f025aafa50f06e2.jpg",
              fit: BoxFit.cover,
              height: 200,
              width: double.infinity,
            )
          ],
        ),
      ),
      body: new Center(
        child: new Container(
          color: Colors.blue,
        ),
      ),
    );
  }
}
