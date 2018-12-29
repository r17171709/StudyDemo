import 'package:flutter/material.dart';
import 'package:flutter_demo/demo1/widget4/GridViewDemo.dart'
    show IconsList, GridAdapter;
export 'package:flutter_demo/demo1/widget4/GridViewDemo.dart'
    show IconsList, GridAdapter;
import 'package:flutter_screenutil/flutter_screenutil.dart';

Widget getIconGrid(List<IconsList> iconsList) {
  return iconsList == null
      ? new SliverToBoxAdapter(
          child: new Divider(
            height: 0.0,
          ),
        )
      : new SliverPadding(
          padding: EdgeInsets.symmetric(horizontal: ScreenUtil().setWidth(72)),
          sliver: new SliverGrid(
            delegate: new SliverChildBuilderDelegate(
                (BuildContext context, int index) {
              return new GridAdapter(iconsList[index]);
            }, childCount: iconsList.length),
            gridDelegate: new SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 5,
              childAspectRatio: 1,
              mainAxisSpacing: ScreenUtil().setWidth(8),
            ),
          ),
        );
}
