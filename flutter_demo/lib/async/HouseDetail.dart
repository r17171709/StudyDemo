import 'dart:async';
import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_demo/async/BlocProvider.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:rxdart/rxdart.dart';
import 'package:hex/hex.dart';

void main() {
  runApp(HouseDetailPage());
  if (Platform.isAndroid) {
    // 设置android状态栏为透明的沉浸
    SystemUiOverlayStyle systemUiOverlayStyle =
        SystemUiOverlayStyle(statusBarColor: Colors.transparent);
    SystemChrome.setSystemUIOverlayStyle(systemUiOverlayStyle);
  }
}

class HouseDetailPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "HouseDetailPage",
      theme: ThemeData(
          primarySwatch: Colors.red, scaffoldBackgroundColor: Colors.white),
      home: Scaffold(
        body: BlocProvider(
          bloc: DetailDataBloc(),
          child: HouseDetailPageDemo(),
        ),
      ),
    );
  }
}

class HouseDetailPageDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _HouseDetailPageDemo();
  }
}

class _HouseDetailPageDemo extends State<HouseDetailPageDemo> {
  DetailDataBloc _bloc;

  @override
  void initState() {
    super.initState();

    Observable<dynamic> observable1 = Observable.fromFuture(getDetailData1());
    Observable<dynamic> observable2 = Observable.fromFuture(getDetailData2());
    Observable.combineLatest2(observable1, observable2,
        (dynamic onData1, dynamic onData2) {
      Beans beans = parseData1(onData1);
      print("$onData1");
      print("$onData2");
      return beans;
    }).listen((Beans onData) {
      _bloc.dataReturn(onData);
    });
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.instance =
        ScreenUtil(width: 1080, height: 1920, allowFontScaling: true)
          ..init(context);

    _bloc = BlocProvider.of(context);
    return StreamBuilder<Beans>(
      builder: (BuildContext context, AsyncSnapshot<Beans> snapshot) {
        print(snapshot.data);
        if (snapshot.data == null) {
          return Container(
            width: 0,
            height: 0,
          );
        } else {
          return ListView(
            children: <Widget>[
              head(snapshot.data.imgUrlLists),
              houseTags(snapshot.data.colorTags),
              houseTitle(snapshot.data.basicInfo.title),
              basicList(snapshot.data.basicList),
              vrInfo(snapshot.data.liveVr),
              appHaoFang(snapshot.data.appHaofang),
              realHouseLogo(),
              info(snapshot.data.infoList),
              otherInfo(snapshot.data.infoJumpLists),
              downpayment(snapshot.data.downpayment),
              diveder(),
              agentsRecoment(snapshot.data.agents),
              diveder(),
              frameCell(snapshot.data.frameCell),
              diveder(),
              positionSurrounding(snapshot.data.positionSurrounding)
            ],
          );
        }
      },
      stream: _bloc._stream,
    );
  }
}

// 头部轮播图组件
Widget head(dynamic imgUrlLists) {
  List<String> heads = [];
  imgUrlLists.forEach((String key, String value) {
    heads.add(key);
  });

  return Container(
      width: ScreenUtil.screenWidth,
      height: ScreenUtil().setHeight(810),
      child: Stack(
        children: <Widget>[
          Positioned(
            child: PageView.custom(childrenDelegate:
                SliverChildBuilderDelegate((BuildContext context, int index) {
              return Image.network(
                heads[index],
                fit: BoxFit.cover,
                width: ScreenUtil.screenWidth,
                height: ScreenUtil().setHeight(810),
              );
            })),
          ),
          Positioned(
            child: IndicatorItem(),
          ),
          Positioned(
              child: Row(
            children: <Widget>[
              tabIcon("images/ic_bk_white_back.png", 44),
              tabIcon("images/ic_bk_white_heart.png", 595),
              tabIcon("images/ic_bk_white_message.png", 76),
              tabIcon("images/ic_bk_wshare.png", 76),
            ],
          ))
        ],
      ));
}

// 头部PageView切换组件
class IndicatorItem extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _StateIndicatorItem();
  }
}

class _StateIndicatorItem extends State<IndicatorItem> {
  int index = 0;

  void onTabChange(int clickPosition) {
    setState(() {
      index = clickPosition;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
        margin: EdgeInsets.only(top: ScreenUtil().setHeight(705)),
        alignment: Alignment.bottomCenter,
        height: ScreenUtil().setHeight(66),
        width: double.infinity,
        color: Colors.transparent,
        child: DecoratedBox(
          decoration: BoxDecoration(
              color: Color.fromARGB(66, 153, 153, 153),
              borderRadius: BorderRadius.all(Radius.circular(20))),
          child: Container(
            height: ScreenUtil().setHeight(66),
            width: ScreenUtil().setWidth(406),
            child: Flex(
              direction: Axis.horizontal,
              children: <Widget>[
                tabImage(index == 0, "VR", () {
                  onTabChange(0);
                }),
                tabImage(index == 1, "图片", () {
                  onTabChange(1);
                }),
                tabImage(index == 2, "户型", () {
                  onTabChange(2);
                }),
              ],
            ),
          ),
        ));
  }
}

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
          fontWeight: FontWeight.bold,
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
                  fontWeight: FontWeight.bold),
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
                  fontWeight: FontWeight.bold),
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
                        fontWeight: FontWeight.bold),
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
                fontSize: ScreenUtil().setSp(40)),
          ),
          Text(
            onData.value,
            style: TextStyle(
              color: Color.fromARGB(255, 34, 34, 34),
              fontSize: ScreenUtil().setSp(40),
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
            style: TextStyle(color: Color.fromARGB(255, 48, 114, 246)),
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
                    fontSize: ScreenUtil().setSp(39)),
                children: [
                  TextSpan(text: onData.substring(0, onData.indexOf("："))),
                  TextSpan(
                      style: TextStyle(
                          color: Color.fromARGB(255, 34, 34, 34),
                          fontSize: ScreenUtil().setSp(39)),
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
                  fontSize: ScreenUtil().setSp(39)),
              children: <TextSpan>[
                TextSpan(text: positionSurrounding.position.name),
                TextSpan(
                    style: TextStyle(
                        color: Color.fromARGB(255, 34, 34, 34),
                        fontSize: ScreenUtil().setSp(39)),
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
                fontSize: ScreenUtil().setSp(39)),
            children: <TextSpan>[
              TextSpan(text: positionSurrounding.school.name),
              TextSpan(
                  style: TextStyle(
                      color: Color.fromARGB(255, 48, 114, 246),
                      fontSize: ScreenUtil().setSp(39)),
                  text: positionSurrounding.school.value),
            ])),
      ),
      Container(
        width: ScreenUtil().setHeight(168),
        height: ScreenUtil().setHeight(168),
        child: AndroidView(viewType: "MapView"),
      )
    ],
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
Widget diveder() {
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

class DetailDataBloc extends BaseBloc {
  StreamController<Beans> _controller = StreamController();

  StreamSink get _sink => _controller.sink;

  Stream<Beans> get _stream => _controller.stream;

  void dataReturn(Beans onData) {
    _sink.add(onData);
  }

  @override
  void dispose() {
    _controller.close();
  }
}

Future<dynamic> getDetailData1() async {
  Dio dio = Dio();
  Response response =
      await dio.get("http://www.mocky.io/v2/5c8215d8310000a5211d1b8e");
  return response.data;
}

Future<dynamic> getDetailData2() async {
  Dio dio = Dio();
  Response response =
      await dio.get("http://www.mocky.io/v2/5c8215fd310000a7211d1b90");
  return response.data;
}

Beans parseData1(dynamic onData1) {
  Map<String, String> imgUrlLists = {};
  List<InfoList> infoList = [];
  List<ColorTags> colorTags = [];
  BasicInfo basicInfo;
  List<InfoList> basicList = [];
  LiveVr liveVr;
  AppHaofang appHaofang;
  List<InfoJumpList> infoJumpLists = [];
  Downpayment downpayment;
  List<Agent> agents = [];
  FrameCell frameCell;
  PositionSurrounding positionSurrounding;

  var data = onData1["data"];

  data["pictureList"].forEach((dynamic onData) {
    onData["imgUrlList"].forEach((dynamic imgUrl) {
      imgUrlLists[imgUrl] = onData["groupName"];
    });
  });

  data["infoList"].forEach((dynamic onData) {
    infoList.add(InfoList(name: onData["name"], value: onData["value"]));
  });

  data["colorTags"].forEach((dynamic onData) {
    colorTags.add(ColorTags(onData["desc"], onData["color"], onData["bgColor"],
        onData["boldFont"]));
  });

  basicInfo = BasicInfo(
      data["basicInfo"]["isFocus"],
      data["basicInfo"]["isOffSale"],
      data["basicInfo"]["title"],
      data["basicInfo"]["cityId"],
      data["basicInfo"]["houseCode"],
      data["basicInfo"]["communityId"],
      data["basicInfo"]["communityName"],
      data["basicInfo"]["price"],
      data["basicInfo"]["unitPrice"],
      data["basicInfo"]["floorState"],
      data["basicInfo"]["blueprintHallNum"],
      data["basicInfo"]["blueprintBedroomNum"],
      data["basicInfo"]["area"],
      data["basicInfo"]["orientation"],
      data["basicInfo"]["mUrl"],
      data["basicInfo"]["hasFramePoints"],
      data["basicInfo"]["houseSource"]);

  data["basicList"].forEach((dynamic onData) {
    basicList.add(InfoList(name: onData["name"], value: onData["value"]));
  });

  liveVr = LiveVr(
      data["vrInfo"]["liveVr"]["url"],
      data["vrInfo"]["liveVr"]["title"],
      data["vrInfo"]["liveVr"]["subTitle"],
      data["vrInfo"]["liveVr"]["buttontitle"]);

  appHaofang = AppHaofang(data["appHaofang"]["title"],
      data["appHaofang"]["icon"], data["appHaofang"]["mUrl"]);

  data["infoJumpList"].forEach((dynamic onData) {
    infoJumpLists.add(InfoJumpList(
        onData["id"], onData["name"], onData["value"], onData["actionUrl"]));
  });

  downpayment =
      Downpayment(data["downpayment"]["name"], data["downpayment"]["value"]);

  data["agents"].forEach((dynamic onData) {
    BrandInfo brandInfo = BrandInfo(onData["brandInfo"]["text"],
        onData["brandInfo"]["textColor"], onData["brandInfo"]["bgColor"]);
    TitleInfo titleInfo = onData["titleInfo"] != null
        ? TitleInfo(onData["titleInfo"]["text"],
            onData["titleInfo"]["textColor"], onData["titleInfo"]["bgColor"])
        : null;
    DescInfo descInfo = DescInfo(onData["descInfo"]["text"],
        onData["descInfo"]["textColor"], onData["descInfo"]["bgColor"]);
    Agent agent = Agent(onData["name"], onData["photoUrl"],
        onData["mobilePhone"], descInfo, titleInfo, brandInfo);
    agents.add(agent);
  });

  List<String> cellInfo = [];
  data["frameCell"]["cellInfo"].forEach((dynamic onData) {
    cellInfo.add(onData);
  });
  frameCell = new FrameCell(cellInfo, data["framePic"][0]["imgUrlList"][0],
      data["frameCell"]["name"]);

  Position _position = Position(data["positionSurrounding"]["position"]["name"],
      data["positionSurrounding"]["position"]["value"]);
  School _school = School(data["positionSurrounding"]["school"]["name"],
      data["positionSurrounding"]["school"]["value"]);
  positionSurrounding = new PositionSurrounding(
      data["positionSurrounding"]["title"], _position, _school);

  return Beans(
      imgUrlLists: imgUrlLists,
      infoList: infoList,
      colorTags: colorTags,
      basicInfo: basicInfo,
      basicList: basicList,
      liveVr: liveVr,
      appHaofang: appHaofang,
      infoJumpLists: infoJumpLists,
      downpayment: downpayment,
      agents: agents,
      frameCell: frameCell,
      positionSurrounding: positionSurrounding);
}

class Beans {
  Map<String, String> imgUrlLists;
  List<InfoList> infoList;
  List<ColorTags> colorTags;
  BasicInfo basicInfo;
  List<InfoList> basicList;
  LiveVr liveVr;
  AppHaofang appHaofang;
  List<InfoJumpList> infoJumpLists;
  Downpayment downpayment;
  List<Agent> agents;
  FrameCell frameCell;
  PositionSurrounding positionSurrounding;

  Beans(
      {this.imgUrlLists,
      this.infoList,
      this.colorTags,
      this.basicInfo,
      this.basicList,
      this.liveVr,
      this.appHaofang,
      this.infoJumpLists,
      this.downpayment,
      this.agents,
      this.frameCell,
      this.positionSurrounding});
}

class InfoList {
  final String name;
  final String value;

  InfoList({this.name, this.value});
}

class ColorTags {
  final String desc;
  final String color;
  final String bgColor;
  final int boldFont;

  ColorTags(this.desc, this.color, this.bgColor, this.boldFont);
}

class BasicInfo {
  final bool isFocus;
  final bool isOffSale;
  final String title;
  final String cityId;
  final String houseCode;
  final String communityId;
  final String communityName;
  final int price;
  final int unitPrice;
  final String floorState;
  final int blueprintHallNum;
  final int blueprintBedroomNum;
  final double area;
  final String orientation;
  final String mUrl;
  final int hasFramePoints;
  final String houseSource;

  BasicInfo(
    this.isFocus,
    this.isOffSale,
    this.title,
    this.cityId,
    this.houseCode,
    this.communityId,
    this.communityName,
    this.price,
    this.unitPrice,
    this.floorState,
    this.blueprintHallNum,
    this.blueprintBedroomNum,
    this.area,
    this.orientation,
    this.mUrl,
    this.hasFramePoints,
    this.houseSource,
  );
}

class LiveVr {
  final String url;
  final String title;
  final String subTitle;
  final String buttontitle;

  LiveVr(this.url, this.title, this.subTitle, this.buttontitle);
}

class AppHaofang {
  final String title;
  final String icon;
  final String mUrl;

  AppHaofang(this.title, this.icon, this.mUrl);
}

class InfoJumpList {
  final String id;
  final String name;
  final String value;
  final String actionUrl;

  InfoJumpList(this.id, this.name, this.value, this.actionUrl);
}

class Downpayment {
  final String name;
  final String value;

  Downpayment(this.name, this.value);
}

class BrandInfo {
  final String text;
  final String textColor;
  final String bgColor;

  BrandInfo(this.text, this.textColor, this.bgColor);
}

class TitleInfo {
  final String text;
  final String textColor;
  final String bgColor;

  TitleInfo(this.text, this.textColor, this.bgColor);
}

class DescInfo {
  final String text;
  final String textColor;
  final String bgColor;

  DescInfo(this.text, this.textColor, this.bgColor);
}

class Agent {
  final String name;
  final String photoUrl;
  final String mobilePhone;
  final DescInfo descInfo;
  final TitleInfo titleInfo;
  final BrandInfo brandInfo;

  Agent(this.name, this.photoUrl, this.mobilePhone, this.descInfo,
      this.titleInfo, this.brandInfo);
}

class FrameCell {
  final List<String> cellInfo;
  final String picture;
  final String name;

  FrameCell(this.cellInfo, this.picture, this.name);
}

class PositionSurrounding {
  final String title;
  final Position position;
  final School school;

  PositionSurrounding(this.title, this.position, this.school);
}

class Position {
  final String name;
  final String value;

  Position(this.name, this.value);
}

class School {
  final String name;
  final String value;

  School(this.name, this.value);
}
