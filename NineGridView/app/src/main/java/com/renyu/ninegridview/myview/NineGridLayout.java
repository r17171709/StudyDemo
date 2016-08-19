package com.renyu.ninegridview.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.renyu.ninegridview.R;
import com.renyu.ninegridview.model.NineGridImageModel;

import java.util.ArrayList;

/**
 * Created by Clevo on 2016/8/16.
 */
public class NineGridLayout extends ViewGroup {

    Context context;

    //layout的宽度
    int width=0;
    //每张图片的间隙
    int space=0;
    //每排有多少张图片
    int column_image=0;
    //单张图片最大宽度
    int oneimage_maxwidth=0;
    //单张图片最大高度
    int oneimage_maxheight=0;

    //上一次复用的的图片数量
    int oldNum=0;

    //计算每一个图片的宽
    int itemW;
    //计算每一个图片的高
    int itemH;

    ArrayList<NineGridImageModel> models;

    public NineGridLayout(Context context) {
        this(context, null);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context=context;

        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.NineGridlayoutStyle);
        space=array.getDimensionPixelSize(R.styleable.NineGridlayoutStyle_space, 10);
        column_image=array.getInteger(R.styleable.NineGridlayoutStyle_column_image, 3);
        width=array.getDimensionPixelSize(R.styleable.NineGridlayoutStyle_total_width, 600);
        oneimage_maxheight=array.getDimensionPixelSize(R.styleable.NineGridlayoutStyle_oneimage_maxheight, 360);
        oneimage_maxwidth=array.getDimensionPixelSize(R.styleable.NineGridlayoutStyle_oneimage_maxwidth, 360);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //没有的时候宽高均为0
        if (models.size()==0) {
            itemW=0;
            itemH=0;
        }
        //只有一个的时候重设置宽高
        else if (models.size()==1) {
            //处理图片大小不能超过边界
            float scaleWidth=(float) models.get(0).getWidth()/oneimage_maxwidth;
            float scaleHeight=(float) models.get(0).getHeight()/oneimage_maxheight;
            if (scaleHeight<1 && scaleWidth<1) {
                itemW=models.get(0).getWidth()-space*2;
                itemH=models.get(0).getHeight()-space*2;
            }
            else {
                itemW= (int) (models.get(0).getWidth()/(scaleHeight>scaleWidth?scaleHeight:scaleWidth))-space*2;
                itemH= (int) (models.get(0).getHeight()/(scaleHeight>scaleWidth?scaleHeight:scaleWidth))-space*2;
            }
        }
        else {
            itemW=(width-(column_image+1)*space)/column_image;
            itemH=(width-(column_image+1)*space)/column_image;
        }

        if (models.size()==0) {
            setMeasuredDimension(0, 0);
        }
        else if (models.size()==1) {
            setMeasuredDimension(itemW, itemH);
        }
        else {
            //总行数
            int row=(models.size()-1)/column_image+1;
            //最大列数
            int column=0;
            if (column_image>models.size()) {
                column=models.size();
            }
            else {
                column=column_image;
            }
            //通过每个item的宽高计算出layout整体宽高
            setMeasuredDimension(space*(column+1)+column*itemW, space*(row+1)+row*itemH);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            if (models.get(i).getImage()==null) {
                continue;
            }
            SimpleDraweeView imageView= (SimpleDraweeView) getChildAt(i);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(models.get(i).getImage()))
                    .setResizeOptions(new ResizeOptions(itemW, itemH)).build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request).setAutoPlayAnimations(true).build();
            imageView.setController(draweeController);

            int row=i/column_image+1;
            int column=i%column_image+1;

            int left=space*column + itemW*(column-1);
            int top=space*row + itemH*(row-1);
            int right=left+itemW;
            int bottom=top+itemH;

            imageView.layout(left, top, right, bottom);
        }
    }

    public void setImageData(ArrayList<NineGridImageModel> models) {
        this.models=models;

        if (models.size()!=0) {
            //从来没有创建过
            if (oldNum==0) {
                for (NineGridImageModel model : models) {
                    SimpleDraweeView imageView=getSimpleDraweeView();
                    addView(imageView);
                }
            }
            else {
                //新创建的比之前的要少，则减去多余的部分
                if (oldNum>models.size()) {
                    removeViews(models.size()-1, oldNum - models.size());
                }
                //新创建的比之前的要少，则添加缺少的部分
                else if (oldNum<models.size()) {
                    for (int i = 0; i < models.size() - oldNum; i++) {
                        SimpleDraweeView imageView=getSimpleDraweeView();
                        addView(imageView);
                    }
                }
            }
            oldNum=models.size();
        }
        else {
            removeAllViews();
            oldNum=0;
        }
    }

    private SimpleDraweeView getSimpleDraweeView() {
        GenericDraweeHierarchyBuilder builder=new GenericDraweeHierarchyBuilder(getResources());
//        RoundingParams params=new RoundingParams();
//        params.setBorder(Color.WHITE, 3);
//        params.setRoundAsCircle(true);
        GenericDraweeHierarchy hierarchy=builder
                .setPlaceholderImage(R.mipmap.ic_launcher)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setFailureImage(R.mipmap.ic_launcher)
                .setFailureImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setRetryImage(R.mipmap.ic_launcher)
                .setRetryImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
//                .setRoundingParams(params)
                .build();
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        simpleDraweeView.setHierarchy(hierarchy);
        simpleDraweeView.setBackgroundColor(Color.BLACK);
        return simpleDraweeView;
    }
}
