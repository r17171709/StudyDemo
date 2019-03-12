import 'dart:async';
import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_demo/async/BlocProvider.dart';
import 'package:flutter_demo/async/beans.dart';
import 'package:flutter_demo/async/widget.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:rxdart/rxdart.dart';

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
      beans = parseData2(onData2, beans);
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
              divider(),
              agentsRecoment(snapshot.data.agents),
              divider(),
              frameCell(snapshot.data.frameCell),
              divider(),
              positionSurrounding(snapshot.data.positionSurrounding),
              houseIntr(snapshot.data.houseIntr),
              divider(),
              space(),
              houseNews(snapshot.data.houseNews),
              communityCard(snapshot.data.communityCard),
              divider(),
              sameCommunityDeal(snapshot.data.communityCard.sameCommunityDeal),
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
