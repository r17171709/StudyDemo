import 'package:flutter/material.dart';
import 'package:flutter_demo/animation_motion_widgets/animatedlist/Beans.dart';

void main() {
  runApp(new MyAnimatedListAppDemo());
}

class MyAnimatedListAppDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "MyAnimatedListAppDemo",
      theme: new ThemeData(primarySwatch: Colors.red),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text("AnimatedListDemo"),
        ),
        body: new AnimatedListDemo(),
      ),
    );
  }
}

class AnimatedListDemo extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _AnimatedListDemoState();
  }
}

class _AnimatedListDemoState extends State<AnimatedListDemo> {
  GlobalKey<AnimatedListState> _listKey = new GlobalKey<AnimatedListState>();

  List<Beans> _lists = <Beans>[
    new Beans(name: "A"),
    new Beans(name: "B"),
    new Beans(name: "C"),
    new Beans(name: "D"),
  ];

  ListModel _listModel;

  @override
  void initState() {
    super.initState();
    _listModel = new ListModel(_listKey, _lists);
  }

  @override
  Widget build(BuildContext context) {
    return new Column(
      children: <Widget>[
        new Row(
          children: <Widget>[
            new RaisedButton.icon(
                onPressed: () {
                  _listModel.insert(new Beans(name: "E"));
                },
                icon: new Icon(Icons.add),
                label: new Text("add")),
            new RaisedButton.icon(
                onPressed: () {
                  _listModel.remove();
                },
                icon: new Icon(Icons.delete),
                label: new Text("delete"))
          ],
          mainAxisAlignment: MainAxisAlignment.spaceAround,
        ),
        new Expanded(
            child: new AnimatedList(
          itemBuilder:
              (BuildContext context, int index, Animation<double> animation) {
            return new SizeTransition(
              sizeFactor: animation,
              axis: Axis.vertical,
              child: new Container(
                width: double.infinity,
                height: 80,
                alignment: AlignmentDirectional.centerStart,
                child: new Text(
                  "${_lists[index].name}",
                  style: new TextStyle(fontSize: 20),
                ),
              ),
            );
          },
          initialItemCount: _lists.length,
          key: _listKey,
        ))
      ],
    );
  }
}

class ListModel {
  final GlobalKey<AnimatedListState> _listKey;
  final List<Beans> _lists;

  ListModel(this._listKey, this._lists);

  AnimatedListState get _animatedListState => _listKey.currentState;

  void insert(Beans bean) {
    _lists.add(bean);
    _animatedListState.insertItem(_lists.length - 1);
  }

  void remove() {
    _lists.removeLast();
    _animatedListState.removeItem(_lists.length,
        (BuildContext context, Animation<double> animation) {
      return new Container();
    });
  }
}
