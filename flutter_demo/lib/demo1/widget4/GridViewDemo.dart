import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:dio/dio.dart';

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
        body: new GridViewDemo(),
      ),
    );
  }
}

class GridViewDemo extends StatefulWidget {
  final List<IconsList> iconsList = new List();

  @override
  State<StatefulWidget> createState() {
    return new _GridViewDemoState();
  }
}

class _GridViewDemoState extends State<GridViewDemo> {
  @override
  void initState() {
    super.initState();
    getTitleData();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.instance = ScreenUtil(width: 1080, height: 1920)..init(context);
    return new Column(
      children: <Widget>[
        new Divider(
          height: ScreenUtil().setWidth(51),
          color: Colors.white,
        ),
        new Expanded(
            child: new GridView.builder(
          gridDelegate: new SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 5,
              childAspectRatio: 1,
              mainAxisSpacing: ScreenUtil().setWidth(8)),
          itemCount: widget.iconsList.length,
          itemBuilder: (BuildContext context, int index) {
            return new GridAdapter(widget.iconsList[index]);
          },
        )),
        new Divider(
          height: ScreenUtil().setWidth(51),
          color: Colors.white,
        ),
      ],
    );
  }

  void getTitleData() {
    Dio dio = new Dio();
    dio
        .get("http://www.mocky.io/v2/5c2572d9300000780067f50b")
        .then((Response<dynamic> onValue) {
      if (onValue.statusCode == 200) {
        var data = onValue.data["data"];
        var moduleList = data = data["moduleList"];
        var icons = moduleList[0];
        var iconsList = icons["list"];
        for (var list in iconsList) {
          IconsList iconsList = new IconsList();
          iconsList.id = list["id"];
          iconsList.title = list["title"];
          iconsList.imgUrl = list["imgUrl"];
          iconsList.actionUrl = list["actionUrl"];
          iconsList.itemKey = list["itemKey"];
          widget.iconsList.add(iconsList);
        }
        setState(() {});
      }
    });
  }
}

class GridAdapter extends StatefulWidget {
  final IconsList _iconsList;

  GridAdapter(this._iconsList);

  @override
  State<StatefulWidget> createState() {
    return new _GridAdapterStates();
  }
}

class _GridAdapterStates extends State<GridAdapter> {
  @override
  Widget build(BuildContext context) {
    return new Container(
      alignment: Alignment.center,
      child: new Column(
        children: <Widget>[
          new Image.network(
            widget._iconsList.imgUrl,
            fit: BoxFit.cover,
            height: ScreenUtil().setWidth(123),
            width: ScreenUtil().setWidth(123),
          ),
          new Divider(
            height: ScreenUtil().setWidth(8),
            color: Colors.white,
          ),
          new Text(
            widget._iconsList.title,
          )
        ],
      ),
    );
  }
}

class IconsList {
  String id;
  String title;
  String imgUrl;
  String actionUrl;
  String itemKey;
}
