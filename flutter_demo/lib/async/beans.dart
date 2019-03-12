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
  HouseIntr houseIntr;
  HouseNews houseNews;

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

  houseIntr = HouseIntr(
      data["houseIntr"]["name"], data["houseIntr"]["intr"]["content"]);

  List<HouseNewsList> houseNewsLists = [];
  data["houseNews"]["list"].forEach((dynamic onData) {
    houseNewsLists.add(HouseNewsList(onData["name"], onData["value"]));
  });
  HouseNewsAllSeeRecord allSeeRecord = HouseNewsAllSeeRecord(
      data["houseNews"]["allSeeRecord"]["name"],
      data["houseNews"]["allSeeRecord"]["desc"]);
  List<HouseNewsTimelineList> houseNewsTimelineLists = [];
  data["houseNews"]["timeline"]["list"].forEach((dynamic onData) {
    houseNewsTimelineLists
        .add(HouseNewsTimelineList(onData["desc"], onData["time"]));
  });
  HouseNewsTimeline houseNewsTimeline = HouseNewsTimeline(
      data["houseNews"]["timeline"]["name"],
      data["houseNews"]["timeline"]["desc"],
      houseNewsTimelineLists);
  houseNews = HouseNews(houseNewsLists, houseNewsTimeline, allSeeRecord,
      data["houseNews"]["name"]);

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
      positionSurrounding: positionSurrounding,
      houseIntr: houseIntr,
      houseNews: houseNews);
}

Beans parseData2(dynamic onData2, Beans bean) {
  var data = onData2["data"];

  UnitPrice unitPrice = UnitPrice(
      data["communityCard"]["basicInfo"]["unitPrice"]["name"],
      data["communityCard"]["basicInfo"]["unitPrice"]["value"]);
  List<BasicInfoList> baseInfoLists = [];
  data["communityCard"]["basicInfo"]["list"].forEach((dynamic onData) {
    baseInfoLists.add(BasicInfoList(onData["name"], onData["value"]));
  });
  BasicInfo2 basicInfo2 = BasicInfo2(
      data["communityCard"]["basicInfo"]["picture"], unitPrice, baseInfoLists);

  List<SameCommunityDealList> sameCommunityDealLists = [];
  data["communityCard"]["sameCommunityDeal"]["list"].forEach((dynamic onData) {
    sameCommunityDealLists.add(SameCommunityDealList(
        onData["houseCode"],
        onData["title"],
        onData["desc"],
        onData["realDesc"],
        onData["price"],
        onData["realPrice"],
        onData["requireLogin"]));
  });
  SameCommunityDeal sameCommunityDeal = SameCommunityDeal(
      data["communityCard"]["sameCommunityDeal"]["name"],
      sameCommunityDealLists);

  CommunityCard communityCard = CommunityCard(data["communityCard"]["name"],
      data["communityCard"]["moreDesc"], basicInfo2, sameCommunityDeal);

  bean.communityCard = communityCard;

  return bean;
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
  HouseIntr houseIntr;
  HouseNews houseNews;
  CommunityCard communityCard;

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
      this.positionSurrounding,
      this.houseIntr,
      this.houseNews,
      this.communityCard});
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

class HouseIntr {
  final String name;
  final String content;

  HouseIntr(this.name, this.content);
}

class HouseNews {
  final List<HouseNewsList> houseNewsLists;
  final HouseNewsTimeline houseNewsTimeline;
  final HouseNewsAllSeeRecord allSeeRecord;
  final String name;

  HouseNews(this.houseNewsLists, this.houseNewsTimeline, this.allSeeRecord,
      this.name);
}

class HouseNewsList {
  final String name;
  final String value;

  HouseNewsList(this.name, this.value);
}

class HouseNewsAllSeeRecord {
  final String name;
  final String desc;

  HouseNewsAllSeeRecord(this.name, this.desc);
}

class HouseNewsTimeline {
  final String name;
  final String desc;
  final List<HouseNewsTimelineList> houseNewsTimelineLists;

  HouseNewsTimeline(this.name, this.desc, this.houseNewsTimelineLists);
}

class HouseNewsTimelineList {
  final String desc;
  final int time;

  HouseNewsTimelineList(this.desc, this.time);
}

class CommunityCard {
  final String name;
  final String moreDesc;
  final BasicInfo2 basicInfo2;
  final SameCommunityDeal sameCommunityDeal;

  CommunityCard(
      this.name, this.moreDesc, this.basicInfo2, this.sameCommunityDeal);
}

class BasicInfo2 {
  final String picture;
  final UnitPrice unitPrice;
  final List<BasicInfoList> basicInfoLists;

  BasicInfo2(this.picture, this.unitPrice, this.basicInfoLists);
}

class UnitPrice {
  final String name;
  final String value;

  UnitPrice(this.name, this.value);
}

class BasicInfoList {
  final String name;
  final String value;

  BasicInfoList(this.name, this.value);
}

class SameCommunityDeal {
  final String name;
  final List<SameCommunityDealList> sameCommunityDealLists;

  SameCommunityDeal(this.name, this.sameCommunityDealLists);
}

class SameCommunityDealList {
  final String houseCode;
  final String title;
  final String desc;
  final String realDesc;
  final String price;
  final String realPrice;
  final int requireLogin;

  SameCommunityDealList(this.houseCode, this.title, this.desc, this.realDesc,
      this.price, this.realPrice, this.requireLogin);
}
