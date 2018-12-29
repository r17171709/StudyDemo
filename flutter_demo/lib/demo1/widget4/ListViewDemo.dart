import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:hex/hex.dart';

void main() {
  runApp(new MyListViewDemoApp());
}

class MyListViewDemoApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyListViewDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
          appBar: new AppBar(
            title: new Text("ListViewDemo"),
          ),
          body: new ListViewDemoState()),
    );
  }
}

class ListViewDemoState extends StatefulWidget {
  final List<HouseInfoBean> arrayList = new List();

  @override
  State<StatefulWidget> createState() {
    return new _ListViewDemoState();
  }
}

//class _ListViewDemoState extends State<ListViewDemoState> {
//  @override
//  Widget build(BuildContext context) {
//    return new ListView.builder(
//      itemBuilder: (BuildContext context, int index) {
//        if (index % 2 == 0) {
//          return new Container(
//            height: 40.0,
//            child: new Flex(
//              direction: Axis.horizontal,
//              children: <Widget>[new Expanded(child: new Text("测试$index"))],
//            ),
//          );
//        } else {
//          return new Divider(
//            height: 1.0,
//            color: Colors.grey,
//          );
//        }
//      },
//      itemCount: 100,
//    );
//  }
//}

//class _ListViewDemoState extends State<ListViewDemoState> {
//  @override
//  Widget build(BuildContext context) {
//    return new ListView.separated(
//        itemBuilder: (BuildContext context, int index) {
//          return new Container(
//            height: 40.0,
//            child: new Flex(
//              direction: Axis.horizontal,
//              children: <Widget>[new Expanded(child: new Text("测试$index"))],
//            ),
//          );
//        },
//        separatorBuilder: (BuildContext context, int index) {
//          return new Divider(
//            height: 1.0,
//            color: Colors.grey,
//          );
//        },
//        itemCount: 100);
//  }
//}

class _ListViewDemoState extends State<ListViewDemoState> {
  @override
  void initState() {
    super.initState();
    _getWebResult();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.instance = ScreenUtil(width: 1080, height: 1920)..init(context);
    return new ListView.separated(
        itemBuilder: (BuildContext context, int index) {
          return new Container(
            child: new HouseAdapter(widget.arrayList[index]),
          );
        },
        separatorBuilder: (BuildContext context, int index) {
          return new Container(
            margin: EdgeInsets.symmetric(horizontal: ScreenUtil().setWidth(72)),
            child: new Divider(
              height: 1.0,
              color: Colors.grey,
            ),
          );
        },
        itemCount: widget.arrayList.length);
  }

  void _getWebResult() {
    Dio dio = new Dio();
    dio
        .get("http://www.mocky.io/v2/5c2439b93000008b007a5f34")
        .then((Response<dynamic> onValue) {
      if (onValue.statusCode == 200) {
        var data = onValue.data["data"];
        var list = data["list"];
        for (var houseInfo in list) {
          if (houseInfo["cardType"] != "house") {
            continue;
          }
          HouseInfoBean houseInfoBean = new HouseInfoBean();
          List<ColorTags> colorTags = new List();
          for (var colortag in houseInfo["colorTags"]) {
            colorTags.add(ColorTags(
                desc: colortag["desc"],
                color: colortag["color"],
                bgColor: colortag["bgColor"],
                boldFont: colortag["boldFont"]));
          }
          houseInfoBean.colorTags = colorTags;
          houseInfoBean.title = houseInfo["title"];
          houseInfoBean.desc = houseInfo["desc"];
          houseInfoBean.priceStr = houseInfo["priceStr"];
          houseInfoBean.priceUnit = houseInfo["priceUnit"];
          houseInfoBean.unitPriceStr = houseInfo["unitPriceStr"];
          houseInfoBean.coverPic = houseInfo["coverPic"];
          widget.arrayList.add(houseInfoBean);
        }
        setState(() {});
      }
    });
  }
}

class HouseInfoBean {
  String title;
  String desc;
  String priceStr;
  String priceUnit;
  String unitPriceStr;
  String coverPic;
  List<ColorTags> colorTags;

  HouseInfoBean(
      {this.title,
      this.desc,
      this.priceStr,
      this.priceUnit,
      this.unitPriceStr,
      this.coverPic,
      this.colorTags});
}

class ColorTags {
  String desc;
  String color;
  String bgColor;
  int boldFont;

  ColorTags({this.desc, this.color, this.bgColor, this.boldFont});
}

class HouseAdapter extends StatefulWidget {
  final HouseInfoBean _houseInfoBean;

  HouseAdapter(this._houseInfoBean);

  @override
  State<StatefulWidget> createState() {
    return new _HouseAdapterState();
  }
}

class _HouseAdapterState extends State<HouseAdapter> {
  @override
  Widget build(BuildContext context) {
    List<Widget> labels = new List();
    widget._houseInfoBean.colorTags.forEach((ColorTags colorTag) {
      List<int> bgColor;
      if (colorTag.bgColor != null) {
        bgColor = HEX.decode(colorTag.bgColor);
      } else {
        bgColor = new List();
        bgColor.add(237);
        bgColor.add(240);
        bgColor.add(243);
      }
      List<int> color = HEX.decode(colorTag.color);
      labels.add(new Container(
        margin: EdgeInsets.only(right: ScreenUtil().setWidth(15)),
        padding: EdgeInsets.symmetric(
            vertical: ScreenUtil().setWidth(8),
            horizontal: ScreenUtil().setWidth(12)),
        decoration: new BoxDecoration(
          borderRadius: BorderRadius.all(Radius.circular(4)),
          color: Color.fromARGB(255, bgColor[0], bgColor[1], bgColor[2]),
        ),
        child: new Text(
          colorTag.desc,
          style: new TextStyle(
              color: Color.fromARGB(255, color[0], color[1], color[2]),
              fontSize: ScreenUtil().setSp(32),
              fontWeight:
                  colorTag.boldFont == 1 ? FontWeight.bold : FontWeight.normal),
        ),
      ));
    });
    return new Container(
      padding: EdgeInsets.fromLTRB(
          ScreenUtil().setWidth(72),
          ScreenUtil().setWidth(60),
          ScreenUtil().setWidth(72),
          ScreenUtil().setWidth(60)),
      child: new Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          new Image.network(
            widget._houseInfoBean.coverPic,
            height: ScreenUtil().setWidth(219),
            width: ScreenUtil().setWidth(315),
            fit: BoxFit.cover,
          ),
          new VerticalDivider(
            width: ScreenUtil().setWidth(36),
          ),
          Expanded(
            child: new Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                new Text(
                  widget._houseInfoBean.title,
                  softWrap: true,
                  style: new TextStyle(
                      color: Color(0xFF222222),
                      fontWeight: FontWeight.bold,
                      fontSize: ScreenUtil().setSp(48)),
                ),
                new Divider(
                  height: ScreenUtil().setWidth(8),
                  color: Colors.white,
                ),
                new Text(
                  widget._houseInfoBean.desc,
                  style: new TextStyle(
                      color: Color(0xFF222222),
                      fontSize: ScreenUtil().setSp(32)),
                ),
                new Divider(
                  height: ScreenUtil().setWidth(8),
                  color: Colors.white,
                ),
                new Row(
                  children: labels,
                ),
                new Divider(
                  height: ScreenUtil().setWidth(8),
                  color: Colors.white,
                ),
                new Row(
                  crossAxisAlignment: CrossAxisAlignment.end,
                  children: <Widget>[
                    new Text(
                      "${widget._houseInfoBean.priceStr}${widget._houseInfoBean.priceUnit}",
                      style: new TextStyle(
                          fontSize: ScreenUtil().setSp(40), color: Colors.red),
                    ),
                    new VerticalDivider(
                      width: ScreenUtil().setWidth(24),
                    ),
                    new Text(
                      widget._houseInfoBean.unitPriceStr,
                      style: new TextStyle(
                          fontSize: ScreenUtil().setSp(32),
                          color: Color(0xFF9399A5)),
                    )
                  ],
                )
              ],
            ),
          )
        ],
      ),
    );
  }
}
