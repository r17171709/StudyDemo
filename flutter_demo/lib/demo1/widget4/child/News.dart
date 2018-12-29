import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class News extends StatefulWidget {
  final NewsBean newsBean;

  News(this.newsBean);

  @override
  State<StatefulWidget> createState() {
    return new NewsState();
  }
}

class NewsState extends State<News> {
  @override
  Widget build(BuildContext context) {
    if (widget.newsBean == null) {
      return new SliverToBoxAdapter(
        child: new Divider(
          height: 0.0,
        ),
      );
    }
    return new SliverPadding(
      padding: EdgeInsets.only(
          left: ScreenUtil().setWidth(72),
          right: ScreenUtil().setWidth(72),
          top: ScreenUtil().setWidth(42)),
      sliver: new SliverStaggeredGrid.count(
        crossAxisCount: 3,
        mainAxisSpacing: ScreenUtil().setWidth(18),
        crossAxisSpacing: ScreenUtil().setWidth(18),
        children: tiles(widget.newsBean.newsListbeans),
        staggeredTiles: _staggeredTiles,
      ),
    );
  }
}

List<StaggeredTile> _staggeredTiles = const <StaggeredTile>[
  const StaggeredTile.count(1, 2),
  const StaggeredTile.count(2, 1),
  const StaggeredTile.count(1, 1),
  const StaggeredTile.count(1, 1),
];

List<Widget> tiles(List<NewsListBean> lists) {
  List<Widget> widgets = new List<Widget>();
  lists.forEach((NewsListBean newsListBean) {
    widgets.add(new NewsAdapter(newsListBean));
  });
  return widgets;
}

class NewsAdapter extends StatefulWidget {
  final NewsListBean _newsListBean;

  NewsAdapter(this._newsListBean);

  @override
  State<StatefulWidget> createState() {
    return new _NewsAdapterState();
  }
}

class _NewsAdapterState extends State<NewsAdapter> {
  @override
  Widget build(BuildContext context) {
    return new Stack(
      children: <Widget>[
        new Image.network(
          widget._newsListBean.imgUrl,
          fit: BoxFit.cover,
        ),
        new Opacity(
          opacity: 0.5,
          child: new Container(
            color: Color(0xFF000000),
          ),
        ),
        new Container(
          child: new Padding(
            padding: EdgeInsets.only(
                bottom: ScreenUtil().setWidth(30),
                left: ScreenUtil().setWidth(30)),
            child: new Column(
              mainAxisAlignment: MainAxisAlignment.end,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                new Text(
                  "${widget._newsListBean.title}",
                  style: new TextStyle(
                      color: Colors.white,
                      fontSize: ScreenUtil().setSp(40),
                      fontWeight: FontWeight.bold),
                ),
                new Text(
                  "${widget._newsListBean.subtitle}",
                  style: new TextStyle(
                      color: Colors.white, fontSize: ScreenUtil().setSp(32)),
                )
              ],
            ),
          ),
        )
      ],
    );
  }
}

class NewsBean {
  String title;
  List<NewsListBean> newsListbeans;
}

class NewsListBean {
  String title;
  String subtitle;
  String imgUrl;
  String actionUrl;
}
