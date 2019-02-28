import 'package:dio/dio.dart';
import 'package:flutter_demo/scroll/GridViewDemo.dart';
import 'package:flutter_demo/demo1/widget4/child/News.dart';
import 'package:flutter_demo/demo1/widget4/child/SecondHouseContent.dart';

void getNetworData(
    void getHouseData(List<IconsList> iconsList,
        SecondHouseBean secondHouseBean, NewsBean newsBean)) {
  final Dio dio = new Dio();
  dio
      .get("http://www.mocky.io/v2/5c2572d9300000780067f50b")
      .then((Response<dynamic> onValue) {
    if (onValue.statusCode == 200) {
      SecondHouseBean secondHouseBeanTemp = new SecondHouseBean();
      List<RecommendListBean> recommendListBeansTemp = new List();
      List<ContentListBean> contentListBeansTemp = new List();
      List<IconsList> iconsListsTemp = new List();
      NewsBean newsBeanTemp = new NewsBean();

      var data = onValue.data["data"];
      var moduleList = data["moduleList"];
      var icons = moduleList[0];
      var iconsList = icons["list"];
      for (var list in iconsList) {
        IconsList iconsList = new IconsList();
        iconsList.id = list["id"];
        iconsList.title = list["title"];
        iconsList.imgUrl = list["imgUrl"];
        iconsList.actionUrl = list["actionUrl"];
        iconsList.itemKey = list["itemKey"];
        iconsListsTemp.add(iconsList);
      }

      var secondHouse = moduleList[6];
      secondHouseBeanTemp.title = secondHouse["title"];
      var contentList = secondHouse["contentList"];
      for (var list in contentList) {
        ContentListBean contentListBean = new ContentListBean();
        contentListBean.title = list["title"];
        contentListBean.id = list["id"];
        contentListBean.subtitle = list["subtitle"];
        contentListBean.actionUrl = list["actionUrl"];
        contentListBean.imgUrl = list["imgUrl"];
        contentListBeansTemp.add(contentListBean);
      }
      secondHouseBeanTemp.contentListBeans = contentListBeansTemp;

      var recommendList = secondHouse["recommendList"];
      for (var list in recommendList) {
        RecommendListBean recommendListBean = new RecommendListBean();
        recommendListBean.houseCode = list["houseCode"];
        recommendListBean.title = list["title"];
        recommendListBean.communityName = list["communityName"];
        recommendListBean.priceStr = list["priceStr"];
        recommendListBean.priceUnit = list["priceUnit"];
        recommendListBean.coverPic = list["coverPic"];
        recommendListBean.areaStr = list["areaStr"];
        List<ColorTags> colorTagsBeansTemp = new List();
        var colorTags = list["colorTags"];
        for (var colorTag in colorTags) {
          ColorTags c = new ColorTags();
          c.desc = colorTag["desc"];
          c.color = colorTag["color"];
          c.boldFont = colorTag["boldFont"];
          colorTagsBeansTemp.add(c);
        }
        recommendListBean.colorTags = colorTagsBeansTemp;
        recommendListBeansTemp.add(recommendListBean);
        secondHouseBeanTemp.recommendListBeans = recommendListBeansTemp;

        var newsInfo = moduleList[4];
        List<NewsListBean> newsListBeansTemp = new List();
        var newsLists = newsInfo["list"];
        for (var newslist in newsLists) {
          NewsListBean newsListBean = new NewsListBean();
          newsListBean.title = newslist["title"];
          newsListBean.subtitle = newslist["subtitle"];
          newsListBean.imgUrl = newslist["imgUrl"];
          newsListBean.actionUrl = newslist["actionUrl"];
          newsListBeansTemp.add(newsListBean);
        }
        newsBeanTemp.newsListbeans = newsListBeansTemp;
        newsBeanTemp.title = newsInfo["title"];
      }
      getHouseData(iconsListsTemp, secondHouseBeanTemp, newsBeanTemp);
    }
  });
}
