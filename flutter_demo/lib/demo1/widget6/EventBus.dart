class EventBus {
  Map<String, OperationCallback> _map = new Map();

  EventBus._singleInstance();

  static EventBus _instance = new EventBus._singleInstance();

  factory EventBus() {
    return _instance;
  }

  void add(String tag, OperationCallback callback) {
    _map[tag] = callback;
  }

  void post(String tag, String args) {
    if (_map.containsKey(tag)) {
      _map[tag](args);
    }
  }

  void remove(String tag) {
    _map.remove(tag);
  }
}

typedef void OperationCallback(String args);