import 'package:flutter/material.dart';
import 'package:flutter_demo/async/beans.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:hex/hex.dart';

// Tab图片选择切换
Widget tabImage(bool choice, String text, VoidCallback onTap) {
  return Expanded(
    child: Container(
      height: ScreenUtil().setHeight(66),
      decoration: BoxDecoration(
          color: choice ? Colors.blue : Colors.transparent,
          borderRadius: BorderRadius.all(Radius.circular(20))),
      child: InkWell(
        child: Center(
          child: Text(
            text,
            style: TextStyle(
                color: choice ? Colors.white : Colors.black,
                fontSize: ScreenUtil().setSp(32)),
          ),
        ),
        onTap: onTap,
      ),
    ),
    flex: 1,
  );
}

// Tab顶部按键
Widget tabIcon(String name, int left) {
  return Container(
    child: Image.asset(
      name,
      width: ScreenUtil().setWidth(66),
      height: ScreenUtil().setWidth(66),
    ),
    margin: EdgeInsets.fromLTRB(
        ScreenUtil().setWidth(left), ScreenUtil().setHeight(120), 0, 0),
  );
}

// 房源标签
Widget houseTags(List<ColorTags> colorTags) {
  List<Widget> allWidgets = <Widget>[];

  colorTags.forEach((ColorTags onData) {
    List<int> bgColor = onData.bgColor != null
        ? HEX.decode(onData.bgColor)
        : <int>[237, 240, 243];

    List<int> color = HEX.decode(onData.color);

    Widget widget = Container(
      margin: EdgeInsets.only(right: ScreenUtil().setWidth(15)),
      decoration: BoxDecoration(
          color: Color.fromARGB(255, bgColor[0], bgColor[1], bgColor[2]),
          borderRadius:
              BorderRadius.all(Radius.circular(ScreenUtil().setWidth(10)))),
      height: ScreenUtil().setHeight(50),
      child: Center(
        child: Padding(
          padding: EdgeInsets.symmetric(horizontal: ScreenUtil().setWidth(15)),
          child: Text(
            onData.desc,
            style: TextStyle(
                color: Color.fromARGB(255, color[0], color[1], color[2]),
                fontSize: ScreenUtil().setSp(30),
                fontWeight:
                    onData.boldFont == 1 ? FontWeight.bold : FontWeight.normal),
          ),
        ),
      ),
    );
    allWidgets.add(widget);
  });

  return Padding(
    padding: EdgeInsets.fromLTRB(ScreenUtil().setWidth(72),
        ScreenUtil().setWidth(54), ScreenUtil().setWidth(72), 0),
    child: Row(
      children: allWidgets,
    ),
  );
}

// 标题
Widget houseTitle(String title) {
  return Padding(
      padding: EdgeInsets.fromLTRB(ScreenUtil().setWidth(72),
          ScreenUtil().setWidth(28), ScreenUtil().setWidth(72), 0),
      child: Text(
        title,
        maxLines: 2,
        style: TextStyle(
          color: Colors.black,
          fontSize: ScreenUtil().setSp(68),
        ),
      ));
}

Widget basicList(List<InfoList> basicList) {
  List<Widget> widgets = [];
  for (int i = 0; i < basicList.length; i++) {
    var widget = Expanded(
      child: Container(
        height: ScreenUtil().setHeight(153),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text(
              basicList[i].value,
              style: TextStyle(
                color: Color.fromARGB(255, 250, 87, 65),
                fontSize: ScreenUtil().setSp(48),
              ),
            ),
            Container(
              height: ScreenUtil().setHeight(3),
            ),
            Text(
              basicList[i].name,
              style: TextStyle(
                  color: Color.fromARGB(255, 153, 153, 153),
                  fontSize: ScreenUtil().setSp(30)),
            )
          ],
        ),
      ),
      flex: 1,
    );

    var diveder = Container(
        margin: EdgeInsets.only(right: ScreenUtil().setWidth(52)),
        height: ScreenUtil().setHeight(113),
        width: ScreenUtil().setWidth(3),
        color: Color.fromARGB(255, 228, 230, 240));

    widgets.add(widget);
    if (i != basicList.length - 1) {
      widgets.add(diveder);
    }
  }
  return Padding(
    padding: EdgeInsets.fromLTRB(ScreenUtil().setWidth(72),
        ScreenUtil().setWidth(54), ScreenUtil().setWidth(72), 0),
    child: Flex(
      direction: Axis.horizontal,
      children: widgets,
    ),
  );
}

// VR宣传
Widget vrInfo(LiveVr liveVr) {
  return Padding(
    padding: EdgeInsets.fromLTRB(
        ScreenUtil().setWidth(72), 0, ScreenUtil().setWidth(72), 0),
    child: Container(
      height: ScreenUtil().setHeight(144),
      color: Color.fromARGB(255, 248, 248, 248),
      alignment: Alignment.centerLeft,
      child: Row(
        children: <Widget>[
          Padding(
            padding: EdgeInsets.only(left: ScreenUtil().setWidth(36)),
            child: Image.asset(
              "images/icon_second_house_vrlook.png",
              width: ScreenUtil().setWidth(54),
              height: ScreenUtil().setWidth(54),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: ScreenUtil().setWidth(18)),
            child: Text(
              liveVr.title,
              style: TextStyle(
                color: Color.fromARGB(255, 16, 29, 55),
                fontSize: ScreenUtil().setSp(40),
              ),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: ScreenUtil().setWidth(12)),
            child: Text(
              liveVr.subTitle,
              style: TextStyle(
                  color: Color.fromARGB(255, 153, 153, 153),
                  fontSize: ScreenUtil().setSp(36)),
            ),
          ),
          Expanded(
              child: Container(
            margin: EdgeInsets.only(right: ScreenUtil().setWidth(36)),
            alignment: Alignment.centerRight,
            child: DecoratedBox(
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.all(Radius.circular(3)),
                  border: Border.all(
                      color: Color.fromARGB(255, 204, 204, 204),
                      width: ScreenUtil().setWidth(3))),
              child: Padding(
                  padding: EdgeInsets.symmetric(
                      horizontal: ScreenUtil().setWidth(36),
                      vertical: ScreenUtil().setHeight(24)),
                  child: Text(
                    liveVr.buttontitle,
                    style: TextStyle(
                      color: Color.fromARGB(255, 34, 34, 34),
                      fontSize: ScreenUtil().setSp(36),
                    ),
                  )),
            ),
          ))
        ],
      ),
    ),
  );
}

// 好房标题展示
Widget appHaoFang(AppHaofang appHaoFang) {
  return Padding(
    padding: EdgeInsets.fromLTRB(ScreenUtil().setWidth(72),
        ScreenUtil().setWidth(45), ScreenUtil().setWidth(72), 0),
    child: Container(
      height: ScreenUtil().setHeight(171),
      child: Stack(
        children: <Widget>[
          Image.asset(
            "images/icon_secondhouse_detail_goodhouse.png",
            width: double.infinity,
            height: ScreenUtil().setHeight(136),
            fit: BoxFit.cover,
          ),
          Container(
              height: ScreenUtil().setHeight(136),
              child: Align(
                alignment: AlignmentDirectional.centerStart,
                child: Row(
                  children: <Widget>[
                    Image.network(
                      appHaoFang.icon,
                      width: ScreenUtil().setWidth(270),
                      height: ScreenUtil().setHeight(81),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: ScreenUtil().setWidth(9)),
                      child: Text(
                        appHaoFang.title,
                        style: TextStyle(
                            color: Color.fromARGB(255, 255, 238, 195),
                            fontSize: ScreenUtil().setSp(36),
                            fontWeight: FontWeight.bold),
                      ),
                    )
                  ],
                ),
              )),
          Positioned(
            child: Container(
              margin: EdgeInsets.only(top: ScreenUtil().setHeight(30)),
              decoration: BoxDecoration(
                  shape: BoxShape.circle,
                  border: Border.all(
                      color: Color.fromARGB(255, 209, 212, 217),
                      width: ScreenUtil().setHeight(3))),
              child: CircleAvatar(
                backgroundColor: Colors.white,
                radius: ScreenUtil().setHeight(68),
                child: Icon(
                  Icons.search,
                  color: Colors.black,
                ),
              ),
            ),
            right: ScreenUtil().setWidth(60),
          )
        ],
      ),
    ),
  );
}

Widget realHouseLogo() {
  return Padding(
    padding: EdgeInsets.fromLTRB(ScreenUtil().setWidth(72),
        ScreenUtil().setWidth(45), ScreenUtil().setWidth(72), 0),
    child: Row(
      children: <Widget>[
        Image.asset(
          "images/icon_real_house_logo.png",
          width: ScreenUtil().setWidth(54),
          height: ScreenUtil().setWidth(54),
        ),
        Padding(
          padding: EdgeInsets.only(left: ScreenUtil().setWidth(27)),
          child: Text(
            "真实房源，假一赔百元",
            style: TextStyle(
                color: Color.fromARGB(255, 34, 34, 34),
                fontSize: ScreenUtil().setSp(36)),
          ),
        )
      ],
    ),
  );
}

// 房源信息展示
// 需要注意的地方是shrinkWrap以及childAspectRatio的比例
// 解决GridView与ListView冲突 physics: NeverScrollableScrollPhysics()
Widget info(List<InfoList> infoList) {
  return Padding(
    padding: EdgeInsets.fromLTRB(ScreenUtil().setWidth(72),
        ScreenUtil().setWidth(60), ScreenUtil().setWidth(72), 0),
    child: GridView.builder(
      gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 2,
        childAspectRatio: 6,
      ),
      shrinkWrap: true,
      physics: NeverScrollableScrollPhysics(),
      itemBuilder: (BuildContext context, int index) {
        return Container(
          child: Row(
            children: <Widget>[
              Text(
                infoList[index].name,
                style: TextStyle(
                    color: Color.fromARGB(255, 153, 153, 153),
                    fontSize: ScreenUtil().setSp(40)),
              ),
              Text(
                infoList[index].value,
                style: TextStyle(
                    color: Color.fromARGB(255, 34, 34, 34),
                    fontSize: ScreenUtil().setSp(40)),
              ),
            ],
          ),
        );
      },
      itemCount: infoList.length,
    ),
  );
}

// 其他基本信息
Widget otherInfo(List<InfoJumpList> infoJumpLists) {
  List<Widget> widgets = [];
  infoJumpLists.forEach((InfoJumpList onData) {
    widgets.add(Container(
      width: double.infinity,
      height: ScreenUtil().setHeight(90),
      child: Row(
        children: <Widget>[
          Text(
            onData.name,
            style: TextStyle(
                color: Color.fromARGB(255, 153, 153, 153),
                fontSize: ScreenUtil().setSp(42)),
          ),
          Text(
            onData.value,
            style: TextStyle(
              color: Color.fromARGB(255, 34, 34, 34),
              fontSize: ScreenUtil().setSp(42),
            ),
          ),
          Expanded(
              child: Opacity(
            opacity: onData.actionUrl == null ? 0 : 1,
            child: Align(
              alignment: AlignmentDirectional.centerEnd,
              child: Image.asset(
                "images/details_enter.png",
                width: ScreenUtil().setWidth(28),
                height: ScreenUtil().setHeight(48),
              ),
            ),
          ))
        ],
      ),
    ));
  });
  return Padding(
    padding: EdgeInsets.fromLTRB(
        ScreenUtil().setWidth(72), 0, ScreenUtil().setWidth(72), 0),
    child: Column(
      children: widgets,
    ),
  );
}

// 咨询首付
Widget downpayment(Downpayment downpayment) {
  return Container(
    height: ScreenUtil().setHeight(148),
    margin: EdgeInsets.fromLTRB(
        ScreenUtil().setWidth(72),
        ScreenUtil().setWidth(60),
        ScreenUtil().setWidth(72),
        ScreenUtil().setWidth(60)),
    color: Color.fromARGB(255, 244, 248, 255),
    child: Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Image.asset(
          "images/icon_seconddetail_downpay_im.png",
          width: ScreenUtil().setWidth(72),
          height: ScreenUtil().setHeight(72),
        ),
        Padding(
          padding: EdgeInsets.only(
            left: ScreenUtil().setWidth(30),
          ),
          child: Text(
            downpayment.value,
            style: TextStyle(
                color: Color.fromARGB(255, 48, 114, 246),
                fontSize: ScreenUtil().setSp(42)),
          ),
        )
      ],
    ),
  );
}

// 推荐经纪人
Widget agentsRecoment(List<Agent> agents) {
  List<Widget> widgets = [];

  // 添加子标题
  widgets.add(innerTitle("推荐经纪人"));

  // 添加子模块
  agents.forEach((Agent onData) {
    List<int> brandInfoBgColor = onData.brandInfo != null
        ? HEX.decode(onData.brandInfo.bgColor)
        : <int>[237, 240, 243];
    List<int> brandInfoTextColor = onData.brandInfo != null
        ? HEX.decode(onData.brandInfo.textColor)
        : <int>[237, 240, 243];

    List<int> titleInfoTextColor = onData.titleInfo != null
        ? HEX.decode(onData.titleInfo.textColor)
        : <int>[237, 240, 243];

    List<int> descInfoTextColor = onData.descInfo != null
        ? HEX.decode(onData.descInfo.textColor)
        : <int>[237, 240, 243];

    widgets.add(Container(
        margin: EdgeInsets.only(
            left: ScreenUtil().setWidth(72), right: ScreenUtil().setWidth(72)),
        height: ScreenUtil().setHeight(180),
        child: Stack(
          children: <Widget>[
            Positioned(
                top: ScreenUtil().setHeight(18),
                child: CircleAvatar(
                  backgroundImage: NetworkImage(
                    "${onData.photoUrl}",
                  ),
                  radius: ScreenUtil().setWidth(60),
                )),
            Positioned(
                left: ScreenUtil().setHeight(150),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    Row(
                      children: <Widget>[
                        Text(
                          onData.name,
                          style: TextStyle(
                              color: Color.fromARGB(255, 34, 34, 34),
                              fontSize: ScreenUtil().setSp(42)),
                        ),
                        Container(
                          margin:
                              EdgeInsets.only(left: ScreenUtil().setWidth(15)),
                          padding: EdgeInsets.symmetric(
                              horizontal: ScreenUtil().setWidth(5),
                              vertical: ScreenUtil().setWidth(2)),
                          color: Color.fromARGB(255, brandInfoBgColor[0],
                              brandInfoBgColor[1], brandInfoBgColor[2]),
                          child: Text(
                            onData.brandInfo.text,
                            style: TextStyle(
                                color: Color.fromARGB(
                                    255,
                                    brandInfoTextColor[0],
                                    brandInfoTextColor[1],
                                    brandInfoTextColor[2]),
                                fontSize: ScreenUtil().setSp(30)),
                          ),
                        ),
                        Container(
                          margin:
                              EdgeInsets.only(left: ScreenUtil().setWidth(27)),
                          child: Image.asset(
                            "images/icon_employment_card.png",
                            width: ScreenUtil().setWidth(60),
                            height: ScreenUtil().setWidth(45),
                          ),
                        )
                      ],
                    ),
                    Container(
                      margin: EdgeInsets.only(top: ScreenUtil().setHeight(9)),
                      child: Text(
                        onData.titleInfo != null ? onData.titleInfo.text : "",
                        style: TextStyle(
                            color: Color.fromARGB(255, titleInfoTextColor[0],
                                titleInfoTextColor[1], titleInfoTextColor[2]),
                            fontSize: ScreenUtil().setSp(33)),
                      ),
                    ),
                    Container(
                      margin: EdgeInsets.only(top: ScreenUtil().setHeight(9)),
                      child: Text(
                        onData.descInfo != null ? onData.descInfo.text : "",
                        style: TextStyle(
                            color: Color.fromARGB(255, descInfoTextColor[0],
                                descInfoTextColor[1], descInfoTextColor[2]),
                            fontSize: ScreenUtil().setSp(33)),
                      ),
                    )
                  ],
                )),
            Positioned(
              child: Row(
                children: <Widget>[
                  Image.asset(
                    "images/agent_card_im.png",
                    width: ScreenUtil().setWidth(120),
                    height: ScreenUtil().setHeight(120),
                  ),
                  Padding(
                    padding: EdgeInsets.only(left: ScreenUtil().setWidth(75)),
                    child: Image.asset(
                      "images/agent_card_call.png",
                      width: ScreenUtil().setWidth(120),
                      height: ScreenUtil().setHeight(120),
                    ),
                  )
                ],
              ),
              left: ScreenUtil().setWidth(618),
            )
          ],
        )));

    widgets.add(Container(
      height: ScreenUtil().setHeight(50),
      width: double.infinity,
    ));
  });
  return Column(children: widgets);
}

// 户型格局
Widget frameCell(FrameCell frameCell) {
  List<Widget> textSpans = [];
  frameCell.cellInfo.forEach((String onData) {
    textSpans.add(
      Container(
          margin: EdgeInsets.only(
            bottom: ScreenUtil().setHeight(9),
          ),
          child: Text.rich(
            TextSpan(
                style: TextStyle(
                    color: Color.fromARGB(255, 153, 153, 153),
                    fontSize: ScreenUtil().setSp(42)),
                children: [
                  TextSpan(text: onData.substring(0, onData.indexOf("："))),
                  TextSpan(
                      style: TextStyle(
                          color: Color.fromARGB(255, 34, 34, 34),
                          fontSize: ScreenUtil().setSp(42)),
                      text:
                          onData.substring(onData.indexOf("："), onData.length)),
                ]),
            maxLines: 1,
            overflow: TextOverflow.ellipsis,
          )),
    );
  });

  return Column(
    children: <Widget>[
      innerTitle(frameCell.name),
      Container(
          margin: EdgeInsets.symmetric(horizontal: ScreenUtil().setWidth(72)),
          child: Row(
            children: <Widget>[
              Expanded(
                child: Container(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: textSpans,
                  ),
                ),
              ),
              Image.network(
                frameCell.picture,
                width: ScreenUtil().setWidth(240),
                height: ScreenUtil().setHeight(240),
                fit: BoxFit.cover,
              )
            ],
          )),
      Container(
        width: double.infinity,
        height: ScreenUtil().setHeight(50),
      )
    ],
  );
}

// 位置及周边配套
Widget positionSurrounding(PositionSurrounding positionSurrounding) {
  return Column(
    crossAxisAlignment: CrossAxisAlignment.start,
    children: <Widget>[
      innerTitle(positionSurrounding.title),
      Container(
        margin: EdgeInsets.symmetric(horizontal: ScreenUtil().setWidth(72)),
        child: Text.rich(
          TextSpan(
              style: TextStyle(
                  color: Color.fromARGB(255, 153, 153, 153),
                  fontSize: ScreenUtil().setSp(42)),
              children: <TextSpan>[
                TextSpan(text: positionSurrounding.position.name),
                TextSpan(
                    style: TextStyle(
                        color: Color.fromARGB(255, 34, 34, 34),
                        fontSize: ScreenUtil().setSp(42)),
                    text: positionSurrounding.position.value),
              ]),
        ),
      ),
      Container(
        margin: EdgeInsets.fromLTRB(
            ScreenUtil().setWidth(72),
            ScreenUtil().setWidth(27),
            ScreenUtil().setWidth(72),
            ScreenUtil().setWidth(39)),
        child: Text.rich(TextSpan(
            style: TextStyle(
                color: Color.fromARGB(255, 153, 153, 153),
                fontSize: ScreenUtil().setSp(42)),
            children: <TextSpan>[
              TextSpan(text: positionSurrounding.school.name),
              TextSpan(
                  style: TextStyle(
                      color: Color.fromARGB(255, 48, 114, 246),
                      fontSize: ScreenUtil().setSp(42)),
                  text: positionSurrounding.school.value),
            ])),
      ),
      Stack(
        children: <Widget>[
          Container(
            margin: EdgeInsets.fromLTRB(0, 0, 0, 0),
            width: double.infinity,
            height: ScreenUtil().setHeight(612),
            child: AndroidView(viewType: "MapView"),
          ),
          Container(
            margin: EdgeInsets.fromLTRB(0, 0, 0, 0),
            width: double.infinity,
            height: ScreenUtil().setHeight(612),
            child: Align(
              child: Container(
                margin: EdgeInsets.fromLTRB(0, 0, 0, 0),
                height: ScreenUtil().setHeight(100),
                color: Colors.black26,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: <Widget>[
                    Text(
                      "公交",
                      style: TextStyle(
                          color: Colors.white,
                          fontSize: ScreenUtil().setSp(32)),
                    ),
                    Text(
                      "地铁",
                      style: TextStyle(
                          color: Colors.white,
                          fontSize: ScreenUtil().setSp(32)),
                    ),
                    Text(
                      "教育",
                      style: TextStyle(
                          color: Colors.white,
                          fontSize: ScreenUtil().setSp(32)),
                    ),
                    Text(
                      "医院",
                      style: TextStyle(
                          color: Colors.white,
                          fontSize: ScreenUtil().setSp(32)),
                    ),
                    Text(
                      "购物",
                      style: TextStyle(
                          color: Colors.white,
                          fontSize: ScreenUtil().setSp(32)),
                    ),
                    Text(
                      "休闲",
                      style: TextStyle(
                          color: Colors.white,
                          fontSize: ScreenUtil().setSp(32)),
                    ),
                  ],
                ),
              ),
              alignment: Alignment.bottomCenter,
            ),
          ),
        ],
      ),
    ],
  );
}

// 房源介绍
Widget houseIntr(HouseIntr houseIntr) {
  return Column(
    children: <Widget>[
      innerTitle(houseIntr.name),
      Container(
        margin: EdgeInsets.fromLTRB(
            ScreenUtil().setWidth(72),
            ScreenUtil().setWidth(27),
            ScreenUtil().setWidth(72),
            ScreenUtil().setWidth(39)),
        child: Text(
          houseIntr.content,
          style: TextStyle(
              color: Color.fromARGB(255, 34, 34, 34),
              fontSize: ScreenUtil().setSp(42)),
          maxLines: 5,
          overflow: TextOverflow.ellipsis,
        ),
      )
    ],
  );
}

// 房源动态
Widget houseNews(HouseNews houseNews) {
  List<Widget> houseNewsLists = [];
  houseNews.houseNewsLists.forEach((HouseNewsList onData) {
    houseNewsLists.add(Column(
      children: <Widget>[
        Text(onData.name,
            style: TextStyle(
                color: Color.fromARGB(255, 153, 153, 153),
                fontSize: ScreenUtil().setSp(33))),
        Divider(
          height: ScreenUtil().setHeight(15),
          color: Colors.transparent,
        ),
        Text(onData.value,
            style: TextStyle(
                color: Color.fromARGB(255, 34, 34, 34),
                fontSize: ScreenUtil().setSp(60))),
        Divider(
          height: ScreenUtil().setHeight(15),
          color: Colors.transparent,
        ),
      ],
      crossAxisAlignment: CrossAxisAlignment.start,
    ));
  });

  return new Column(
    children: <Widget>[
      innerTitle(houseNews.name),
      Container(
        margin: EdgeInsets.fromLTRB(ScreenUtil().setWidth(72), 0,
            ScreenUtil().setWidth(72), ScreenUtil().setWidth(39)),
        child: Row(
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: houseNewsLists,
        ),
      ),
      divider(),
      Container(
        margin: EdgeInsets.fromLTRB(
            ScreenUtil().setWidth(72), 0, ScreenUtil().setWidth(72), 0),
        height: ScreenUtil().setHeight(195),
        child: Align(
          alignment: Alignment.centerLeft,
          child: Row(
            children: <Widget>[
              Expanded(
                  child: Text(houseNews.allSeeRecord.name,
                      style: TextStyle(
                          color: Color.fromARGB(255, 34, 34, 34),
                          fontSize: ScreenUtil().setSp(42)))),
              Text("${houseNews.allSeeRecord.desc}      ",
                  style: TextStyle(
                      color: Color.fromARGB(255, 153, 153, 153),
                      fontSize: ScreenUtil().setSp(33))),
              Image.asset(
                "images/details_enter.png",
                width: ScreenUtil().setWidth(36),
                height: ScreenUtil().setWidth(36),
              )
            ],
          ),
        ),
      ),
      divider(),
      Container(
        margin: EdgeInsets.fromLTRB(
            ScreenUtil().setWidth(72), 0, ScreenUtil().setWidth(72), 0),
        height: ScreenUtil().setHeight(195),
        child: Align(
          alignment: Alignment.centerLeft,
          child: Row(
            children: <Widget>[
              Expanded(
                  child: Text(houseNews.houseNewsTimeline.name,
                      style: TextStyle(
                          color: Color.fromARGB(255, 34, 34, 34),
                          fontSize: ScreenUtil().setSp(42)))),
              Text("${houseNews.houseNewsTimeline.desc}      ",
                  style: TextStyle(
                      color: Color.fromARGB(255, 153, 153, 153),
                      fontSize: ScreenUtil().setSp(33))),
              Image.asset(
                "images/details_enter.png",
                width: ScreenUtil().setWidth(36),
                height: ScreenUtil().setWidth(36),
              )
            ],
          ),
        ),
      ),
      space()
    ],
  );
}

Widget communityCard(CommunityCard communityCard) {
  List<Widget> baseInfoLists = [];
  communityCard.basicInfo2.basicInfoLists.forEach((BasicInfoList onData) {
    baseInfoLists.add(Text("${onData.name}${onData.value}",
        style: TextStyle(
            color: Color.fromARGB(255, 153, 153, 153),
            fontSize: ScreenUtil().setSp(33))));
    baseInfoLists.add(Divider(
      height: ScreenUtil().setHeight(6),
      color: Colors.transparent,
    ));
  });

  baseInfoLists.insert(
      0,
      Divider(
        height: ScreenUtil().setHeight(18),
        color: Colors.transparent,
      ));

  baseInfoLists.insert(
      0,
      Text.rich(TextSpan(
          style: TextStyle(
              color: Color.fromARGB(255, 34, 34, 34),
              fontSize: ScreenUtil().setSp(42)),
          children: [
            TextSpan(text: communityCard.basicInfo2.unitPrice.name),
            TextSpan(
                style: TextStyle(
                    color: Color.fromARGB(255, 250, 87, 65),
                    fontSize: ScreenUtil().setSp(42)),
                text: communityCard.basicInfo2.unitPrice.value)
          ])));

  return Column(
    children: <Widget>[
      Container(
        margin: EdgeInsets.fromLTRB(
            ScreenUtil().setWidth(72), 0, ScreenUtil().setWidth(72), 0),
        height: ScreenUtil().setHeight(210),
        width: double.infinity,
        child: Align(
          alignment: Alignment.centerLeft,
          child: Row(
            children: <Widget>[
              Expanded(
                  child: Text(communityCard.name,
                      style: TextStyle(
                          color: Color.fromARGB(255, 34, 34, 34),
                          fontSize: ScreenUtil().setSp(48)))),
              Text("${communityCard.moreDesc}      ",
                  style: TextStyle(
                      color: Color.fromARGB(255, 153, 153, 153),
                      fontSize: ScreenUtil().setSp(33))),
              Image.asset(
                "images/details_enter.png",
                width: ScreenUtil().setWidth(36),
                height: ScreenUtil().setWidth(36),
              )
            ],
          ),
        ),
      ),
      Container(
        margin: EdgeInsets.fromLTRB(
            ScreenUtil().setWidth(72), 0, ScreenUtil().setWidth(72), 0),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Expanded(
                child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: baseInfoLists,
            )),
            Image.network(
              communityCard.basicInfo2.picture,
              width: ScreenUtil().setWidth(315),
              height: ScreenUtil().setHeight(240),
              fit: BoxFit.cover,
            )
          ],
        ),
      ),
      Divider(
        height: ScreenUtil().setHeight(78),
        color: Colors.transparent,
      )
    ],
  );
}

Widget sameCommunityDeal(SameCommunityDeal sameCommunityDeal) {
  List<Widget> widgets = [];
  for (int i = 0; i < sameCommunityDeal.sameCommunityDealLists.length; i++) {
    SameCommunityDealList onData = sameCommunityDeal.sameCommunityDealLists[i];

    widgets.add(Container(
      margin: EdgeInsets.fromLTRB(
          ScreenUtil().setWidth(72), 0, ScreenUtil().setWidth(72), 0),
      width: double.infinity,
      height: ScreenUtil().setHeight(171),
      child: Row(
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Column(
            children: <Widget>[
              CircleAvatar(
                backgroundColor: Color.fromARGB(255, 229, 229, 229),
                radius: ScreenUtil().setHeight(15),
              ),
              Divider(
                height: ScreenUtil().setHeight(15),
              ),
              Expanded(
                  child: VerticalDivider(
                width: ScreenUtil().setHeight(3),
                color: Color.fromARGB(255, 228, 230, 240),
              )),
              Divider(
                height: ScreenUtil().setHeight(
                    i == sameCommunityDeal.sameCommunityDealLists.length - 1
                        ? 60
                        : 15),
              ),
            ],
          ),
          Expanded(
            child: Container(
              margin: EdgeInsets.only(left: ScreenUtil().setWidth(24)),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Text(onData.title,
                      style: TextStyle(
                          color: Color.fromARGB(255, 34, 34, 34),
                          fontSize: ScreenUtil().setSp(42))),
                  Divider(
                    height: ScreenUtil().setHeight(15),
                    color: Colors.transparent,
                  ),
                  Text(onData.desc,
                      style: TextStyle(
                          color: Color.fromARGB(255, 153, 153, 153),
                          fontSize: ScreenUtil().setSp(33)))
                ],
              ),
            ),
          ),
          Text(onData.price,
              style: TextStyle(
                  color: Color.fromARGB(255, 250, 87, 65),
                  fontSize: ScreenUtil().setSp(42)))
        ],
      ),
    ));
  }

  widgets.add(Divider(
    height: ScreenUtil().setHeight(36),
    color: Colors.transparent,
  ));
  widgets.insert(
    0,
    Container(
      height: ScreenUtil().setHeight(210),
      margin: EdgeInsets.fromLTRB(
          ScreenUtil().setWidth(72), 0, ScreenUtil().setWidth(72), 0),
      width: double.infinity,
      child: Align(
        alignment: Alignment.centerLeft,
        child: Row(
          children: <Widget>[
            Expanded(
                child: Text(sameCommunityDeal.name,
                    style: TextStyle(
                        color: Color.fromARGB(255, 34, 34, 34),
                        fontSize: ScreenUtil().setSp(48)))),
            Image.asset(
              "images/details_enter.png",
              width: ScreenUtil().setWidth(36),
              height: ScreenUtil().setWidth(36),
            )
          ],
        ),
      ),
    ),
  );

  return Column(
    children: widgets,
  );
}

// 子模块标题
Widget innerTitle(String title) {
  return Container(
    margin: EdgeInsets.only(
        left: ScreenUtil().setWidth(72), right: ScreenUtil().setWidth(72)),
    height: ScreenUtil().setHeight(180),
    child: Align(
      alignment: Alignment.centerLeft,
      child: Text(
        title,
        style: TextStyle(
            color: Color.fromARGB(255, 34, 34, 34),
            fontSize: ScreenUtil().setSp(48)),
      ),
    ),
  );
}

// 分割线
Widget divider() {
  return Container(
    margin: EdgeInsets.symmetric(
      horizontal: ScreenUtil().setHeight(72),
    ),
    child: Divider(
      height: ScreenUtil().setHeight(3),
      color: Color.fromARGB(225, 228, 230, 240),
    ),
  );
}

// 粗分割线
Widget space() {
  return Container(
    alignment: Alignment.topLeft,
    height: ScreenUtil().setHeight(35),
    width: double.infinity,
    color: Color.fromARGB(255, 248, 248, 248),
    child: Divider(
      height: ScreenUtil().setHeight(3),
      color: Color.fromARGB(225, 228, 230, 240),
    ),
  );
}
