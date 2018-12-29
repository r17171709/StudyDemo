import 'package:flutter/material.dart';
import 'package:flutter_demo/demo1/widget4/child/LabelTitle.dart';
import 'package:flutter_demo/demo1/widget4/child/News.dart';
import 'package:flutter_demo/demo1/widget4/child/UtilsIcons.dart';
import 'package:flutter_demo/demo1/widget4/child/SecondHouseContent.dart';
import 'package:flutter_demo/demo1/widget4/child/SecondHouseAdapter.dart';
import 'package:flutter_demo/demo1/widget4/child/MoreSecondHouse.dart';
import 'package:flutter_demo/demo1/widget4/child/NetworkRequest.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

void main() {
  runApp(new MySliverAppDemo());
}

class MySliverAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MySliverAppDemo",
      theme: new ThemeData(primarySwatch: Colors.blue),
      home: new Scaffold(
        body: new SliverDemo(),
      ),
    );
  }
}

class SliverDemo extends StatefulWidget {
  List<IconsList> iconsList;
  SecondHouseBean secondHouseBean;
  NewsBean newsBean;

  @override
  State<StatefulWidget> createState() {
    return new _SliverDemoState();
  }
}

class _SliverDemoState extends State<SliverDemo> {
  @override
  void initState() {
    super.initState();
    getTitleData();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.instance = ScreenUtil(width: 1080, height: 1920)..init(context);

    return new CustomScrollView(
      slivers: <Widget>[
        new SliverAppBar(
          pinned: true,
          expandedHeight: 200.0,
          flexibleSpace: new FlexibleSpaceBar(
            background: new Image.network(
              "http://h.hiphotos.baidu.com/image/pic/item/03087bf40ad162d92dc06e711cdfa9ec8a13cdb5.jpg",
              fit: BoxFit.cover,
            ),
          ),
        ),
        new SliverToBoxAdapter(
          child: new Divider(
            height: ScreenUtil().setWidth(42),
            color: Colors.white,
          ),
        ),
        getIconGrid(widget.iconsList),
        new SliverToBoxAdapter(
          child: widget.newsBean == null
              ? new Divider(
                  height: 0,
                )
              : new Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    new Divider(
                      height: ScreenUtil().setWidth(42),
                      color: Colors.white,
                    ),
                    new LabelTitle(widget.newsBean.title),
                  ],
                ),
        ),
        new News(widget.newsBean),
        new SliverToBoxAdapter(
          child: new Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              new Divider(
                height: ScreenUtil().setWidth(42),
                color: Colors.white,
              ),
              widget.secondHouseBean == null
                  ? new Divider(
                      height: 0,
                    )
                  : new LabelTitle(widget.secondHouseBean.title),
              new SecondHouseContent(widget.secondHouseBean),
              new Divider(
                height: ScreenUtil().setWidth(42),
                color: Colors.white,
              ),
              new Padding(
                padding:
                    EdgeInsets.symmetric(horizontal: ScreenUtil().setWidth(72)),
                child: new Divider(
                  height: ScreenUtil().setWidth(2),
                  color: Color(0xFFE4E6F0),
                ),
              ),
            ],
          ),
        ),
        new SliverList(
          delegate: new SliverChildBuilderDelegate(
              (BuildContext context, int index) {
            return new HouseAdapter(
                widget.secondHouseBean.recommendListBeans[index]);
          },
              childCount: widget.secondHouseBean == null
                  ? 0
                  : widget.secondHouseBean.recommendListBeans.length),
        ),
        new SliverToBoxAdapter(
          child: widget.secondHouseBean != null
              ? new MoreSecondHouse()
              : new Divider(
                  height: 0,
                ),
        )
      ],
    );
  }

  void getTitleData() {
    getNetworData((List<IconsList> iconsList, SecondHouseBean secondHouseBean,
        NewsBean newsBean) {
      widget.iconsList = iconsList;
      widget.secondHouseBean = secondHouseBean;
      widget.newsBean = newsBean;

      setState(() {});
    });
  }
}
