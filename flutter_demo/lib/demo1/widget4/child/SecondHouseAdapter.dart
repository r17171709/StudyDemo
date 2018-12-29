import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'SecondHouseContent.dart';
import 'package:hex/hex.dart';

class HouseAdapter extends StatefulWidget {
  final RecommendListBean _houseInfoBean;

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
      List<int> color = HEX.decode(colorTag.color);
      labels.add(new Container(
        margin: EdgeInsets.only(right: ScreenUtil().setWidth(15)),
        padding: EdgeInsets.symmetric(
            vertical: ScreenUtil().setWidth(8),
            horizontal: ScreenUtil().setWidth(12)),
        decoration: new BoxDecoration(
          borderRadius: BorderRadius.all(Radius.circular(4)),
          color: Color.fromARGB(255, 237, 240, 243),
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
          ScreenUtil().setWidth(0)),
      child: new Column(
        children: <Widget>[
          new Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              new DecoratedBox(
                decoration: new BoxDecoration(
                    borderRadius: BorderRadius.all(
                        Radius.circular(ScreenUtil().setWidth(8)))),
                child: new Image.network(
                  widget._houseInfoBean.coverPic,
                  height: ScreenUtil().setWidth(240),
                  width: ScreenUtil().setWidth(315),
                  fit: BoxFit.cover,
                ),
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
                      widget._houseInfoBean.areaStr,
                      style: new TextStyle(
                          color: Color(0xFF101D37),
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
                              fontSize: ScreenUtil().setSp(40),
                              color: Colors.red),
                        ),
                        new VerticalDivider(
                          width: ScreenUtil().setWidth(24),
                          color: Colors.red,
                        ),
                        new Text(
                          widget._houseInfoBean.communityName,
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
          new Padding(
            padding: EdgeInsets.only(top: ScreenUtil().setWidth(60)),
            child: new Divider(
              height: ScreenUtil().setWidth(2),
              color: Color(0xFFE4E6F0),
            ),
          )
        ],
      ),
    );
  }
}
