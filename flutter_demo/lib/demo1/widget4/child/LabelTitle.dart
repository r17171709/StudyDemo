import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class LabelTitle extends StatefulWidget {
  final String title;

  LabelTitle(this.title);

  @override
  State<StatefulWidget> createState() {
    return new _LabelTitleState();
  }
}

class _LabelTitleState extends State<LabelTitle> {
  @override
  Widget build(BuildContext context) {
    return widget.title == null
        ? new Text("")
        : new Padding(
            padding: EdgeInsets.only(left: ScreenUtil().setWidth(72)),
            child: new Text(
              "${widget.title}",
              style: new TextStyle(
                  fontSize: ScreenUtil().setWidth(64),
                  fontWeight: FontWeight.bold),
            ),
          );
  }
}
