import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class SecondHouseContent extends StatefulWidget {
  final SecondHouseBean secondHouseBean;

  SecondHouseContent(this.secondHouseBean);

  @override
  State<StatefulWidget> createState() {
    return new _SecondHouseContentState();
  }
}

class _SecondHouseContentState extends State<SecondHouseContent> {
  @override
  Widget build(BuildContext context) {
    List<Widget> secondContents = new List();
    if (widget.secondHouseBean != null && widget.secondHouseBean.contentListBeans != null) {
      for (int i = 0; i < 3; i++) {
        Expanded expanded = new Expanded(
          child: new DecoratedBox(
            decoration: new BoxDecoration(
                borderRadius: BorderRadius.all(
                    Radius.circular(ScreenUtil().setWidth(8)))),
            child: new Stack(
              children: <Widget>[
                new Positioned(
                    child: new Image.network(
                  widget.secondHouseBean.contentListBeans[i].imgUrl,
                  width: ScreenUtil().setWidth(300),
                  height: ScreenUtil().setWidth(210),
                  fit: BoxFit.cover,
                )),
                new Divider(
                  height: ScreenUtil().setWidth(8),
                ),
                new Positioned(
                    height: ScreenUtil().setWidth(210),
                    left: ScreenUtil().setWidth(30),
                    child: new Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        new Text(
                          widget.secondHouseBean.contentListBeans[i].title,
                          style: new TextStyle(
                              color: Colors.white,
                              fontWeight: FontWeight.bold,
                              fontSize: ScreenUtil().setWidth(48)),
                        ),
                        new Text(
                            widget.secondHouseBean.contentListBeans[i].subtitle,
                            style: new TextStyle(
                                color: Colors.white,
                                fontSize: ScreenUtil().setWidth(40)))
                      ],
                    )),
              ],
            ),
          ),
          flex: 1,
        );
        secondContents.add(expanded);
        if (i != 3) {
          secondContents.add(new VerticalDivider(
            width: 8.0,
          ));
        }
      }
    }
    return new Padding(
      padding: EdgeInsets.only(
          left: ScreenUtil().setWidth(72),
          right: ScreenUtil().setWidth(72),
          top: ScreenUtil().setWidth(42)),
      child: new Flex(
        direction: Axis.horizontal,
        children: secondContents,
      ),
    );
  }
}

class SecondHouseBean {
  String title;
  List<RecommendListBean> recommendListBeans;
  List<ContentListBean> contentListBeans;
}

class ContentListBean {
  String id;
  String title;
  String subtitle;
  String actionUrl;
  String imgUrl;
}

class RecommendListBean {
  String houseCode;
  String title;
  String communityName;
  String priceStr;
  String priceUnit;
  String coverPic;
  String areaStr;
  List<ColorTags> colorTags;
}

class ColorTags {
  String desc;
  String color;
  int boldFont;
}
