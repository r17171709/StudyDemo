package com.renyu.dragrecyclerview.utils;

import android.net.Uri;

import com.blankj.utilcode.util.SizeUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by Administrator on 2020/6/18.
 */
public class OtherUtils {
    public static void loadFresco(String path, float width, float height, SimpleDraweeView simpleDraweeView) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(path))
                .setResizeOptions(new ResizeOptions(SizeUtils.dp2px(width), SizeUtils.dp2px(height))).build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request).setAutoPlayAnimations(true).build();
        simpleDraweeView.setController(draweeController);
        simpleDraweeView.setTag(path);
    }
}
