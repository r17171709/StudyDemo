import 'package:flutter/material.dart';

//  Axis scrollDirection              滚动方向
//  bool reverse = false,
//  ScrollController controller,
//  bool primary,
//  ScrollPhysics physics,
//  bool shrinkWrap = false,
//  EdgeInsetsGeometry padding,
//  int crossAxisCount,               交叉轴Item个数
//  double mainAxisSpacing            主轴间距
//  crossAxisSpacing                  交叉轴间距
//  childAspectRatio                  child宽高比
//  addAutomaticKeepAlives = true,
//  addRepaintBoundaries = true,
//  addSemanticIndexes = true,
//  cacheExtent,
//  children = const <Widget>[],
//  semanticChildCount,

// GridTile是GridView中的一种组件;包含header, child, footer三部份;

void main() {
  runApp(new MyGridViewAppDemo());
}

class MyGridViewAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyGridViewAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("GridViewDemo"),
        ),
        body: new Column(
          children: <Widget>[new GridViewDemo1()],
        ),
      ),
    );
  }
}

class GridViewDemo1 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Container(
      // 高度一定要设置
      height: 300,
      child: new GridView.count(
        crossAxisCount: 2,
        crossAxisSpacing: 10,
        mainAxisSpacing: 5,
        // 默认1:1的比例
        childAspectRatio: 2,
        children: <Widget>[
          new GridTile(
            child: new Container(
              child: new Text(
                "Child",
                style: new TextStyle(color: Colors.blue),
              ),
            ),
            header: new GridTileBar(
              title: new Text(
                "GridTileBar",
                style: new TextStyle(color: Colors.black),
              ),
              leading: new Icon(
                Icons.home,
                color: Colors.red,
              ),
              trailing: new Icon(
                Icons.home,
                color: Colors.red,
              ),
            ),
            footer: new Text("Footer", style: new TextStyle(color: Colors.yellow),),
          ),
          new Container(
            color: Colors.red,
            constraints: new BoxConstraints.expand(height: 50, width: 50),
          ),
          new Container(
            color: Colors.blue,
            constraints: new BoxConstraints.expand(height: 50, width: 50),
          ),
          new Container(
            color: Colors.pink,
            constraints: new BoxConstraints.expand(height: 50, width: 50),
          ),
          new Container(
            color: Colors.green,
            constraints: new BoxConstraints.expand(height: 50, width: 50),
          ),
        ],
      ),
    );
  }
}

class GridViewDemo2 extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _GridViewDemo2State();
  }
}

class _GridViewDemo2State extends State<GridViewDemo2> {
  @override
  Widget build(BuildContext context) {
    return new Container(
      height: 300,
      child: new GridView.builder(
        gridDelegate: new SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: 4, crossAxisSpacing: 5, mainAxisSpacing: 4),
        itemBuilder: (BuildContext context, int index) {
          return new Container(
            color: Colors.blue,
          );
        },
        itemCount: 8,
      ),
    );
  }
}
