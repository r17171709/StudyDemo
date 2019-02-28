import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(new MyFutureBuilderAppDemo());
}

class MyFutureBuilderAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "FutureBuilderDemo",
      theme: new ThemeData(primaryColor: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("FutureBuilderDemo"),
        ),
        body: new FutureBuilderDemo(),
      ),
    );
  }
}

class FutureBuilderDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _FutureBuilderDemoState();
  }
}

class _FutureBuilderDemoState extends State<FutureBuilderDemo> {
  // 适用于首次请求使用
  Future<List<String>> _data;

  // 真正的数据源
  List<String> _beans;

  Future<List<String>> _getData() async {
    Dio dio = new Dio();
    Response<dynamic> response =
        await dio.get("http://www.mocky.io/v2/5c2572d9300000780067f50b");
    var data = response.data["data"];
    var moduleList = data["moduleList"];
    var icons = moduleList[0];
    var iconsList = icons["list"];
    List<String> temp = [];
    for (var list in iconsList) {
      temp.add(list["title"]);
    }
    return temp;
  }

  Future<void> _onRefresh() async {
    List<String> onValue = await _getData();
    setState(() {
      _beans.clear();
      _beans.add("测试");
      _beans.addAll(onValue);
    });
  }

  @override
  void initState() {
    super.initState();
    _beans = [];
    _data = _getData();
  }

  @override
  Widget build(BuildContext context) {
    return new RefreshIndicator(
        child: new FutureBuilder(
          builder:
              (BuildContext context, AsyncSnapshot<List<String>> snapshot) {
            switch (snapshot.connectionState) {
              case ConnectionState.waiting:
                return new Container();
              case ConnectionState.done:
                if (_beans.length == 0) {
                  _beans.addAll(snapshot.data);
                }
                return new ListView.builder(
                  itemBuilder: (BuildContext context, int index) {
                    return new ListTile(
                      title: new Text(_beans[index]),
                    );
                  },
                  itemCount: _beans.length,
                );
              default:
                return new Container();
            }
          },
          future: _data,
        ),
        onRefresh: _onRefresh);
  }
}
