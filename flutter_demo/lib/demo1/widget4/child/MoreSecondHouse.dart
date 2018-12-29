import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class MoreSecondHouse extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _MoreSecondHouseState();
  }
}

class _MoreSecondHouseState extends State<MoreSecondHouse> {
  @override
  Widget build(BuildContext context) {
    return new Padding(
      padding: EdgeInsets.only(
          left: ScreenUtil().setWidth(72),
          right: ScreenUtil().setWidth(72),
          top: ScreenUtil().setWidth(48)),
      child: new Container(
        height: ScreenUtil().setWidth(144),
        child: new FlatButton(
          onPressed: () {},
          child: new Text(
            "更多二手房",
            style: new TextStyle(
                color: Color(0xFF3072F6), fontSize: ScreenUtil().setWidth(48)),
          ),
          color: Color(0xFFf5f8ff),
        ),
      ),
    );
  }
}
