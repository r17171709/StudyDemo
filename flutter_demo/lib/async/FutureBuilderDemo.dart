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
  Future<List<String>> _data;

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

  @override
  void initState() {
    super.initState();
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
                return new ListView.builder(
                  itemBuilder: (BuildContext context, int index) {
                    return new ListTile(
                      title: new Text(snapshot.data[index]),
                    );
                  },
                  itemCount: snapshot.data.length,
                );
              default:
                return new Container();
            }
          },
          future: _data,
        ),
        onRefresh: _getData);
  }
}
