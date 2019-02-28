import 'package:flutter/material.dart';

void main() {
  runApp(new MyPageViewAppDemo());
}

class MyPageViewAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyPageViewAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("PageViewDemo"),
        ),
        body: new PageViewDemo(),
      ),
    );
  }
}

class PageViewDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _PageViewDemoState();
  }
}

class _PageViewDemoState extends State<PageViewDemo> {
  PageController _pageController;

  @override
  void initState() {
    super.initState();

    _pageController = new PageController(initialPage: 1);
  }

  @override
  Widget build(BuildContext context) {
    return new LayoutBuilder(
        builder: (BuildContext context, BoxConstraints constraints) {
      return new NotificationListener(
        child: PageView.custom(
          childrenDelegate:
              SliverChildBuilderDelegate((BuildContext context, int index) {
            return new Container(
              color: Colors.green,
              child: new Center(
                child: new Column(
                  mainAxisSize: MainAxisSize.min,
                  children: <Widget>[
                    new Text("$index"),
                    new Transform(
                      transform: Matrix4.translationValues(
                          constraints.maxWidth /
                              2 *
                              (index - _pageController.page),
                          0,
                          0),
                      child: new Text(
                        "快速",
                        style: new TextStyle(fontSize: 20),
                      ),
                    )
                  ],
                ),
              ),
            );
          }, childCount: 3),
          controller: _pageController,
          // 滑动到边缘弹性
          physics: new BouncingScrollPhysics(),
        ),
        onNotification: (ScrollNotification notification) {
          setState(() {});
          return false;
        },
      );
    });

//    return new PageView(
//      children: <Widget>[
//        new Container(
//          color: Colors.blue,
//          child: new Center(
//            child: new Text("1"),
//          ),
//        ),
//        new Container(
//          color: Colors.green,
//          child: new Center(
//            child: new Text("2"),
//          ),
//        ),
//        new Container(
//          color: Colors.yellow,
//          child: new Center(
//            child: new Text("3"),
//          ),
//        )
//      ],
//      onPageChanged: (value) {
//        print(value);
//      },
//    );
  }
}
