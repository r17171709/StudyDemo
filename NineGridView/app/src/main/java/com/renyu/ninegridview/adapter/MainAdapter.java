package com.renyu.ninegridview.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.renyu.ninegridview.R;
import com.renyu.ninegridview.model.NineGridImageModel;
import com.renyu.ninegridview.myview.NineGridLayout;

import java.util.ArrayList;

/**
 * Created by Pan_ on 2015/2/3.
 */
public class MainAdapter extends BaseAdapter {

    private final static int EMPTY_IMAGE=0;
    private final static int ONE_IMAGE=1;
    private final static int MORE_IMAGE=2;

    private Context context;
    private ArrayList<ArrayList<NineGridImageModel>> models;

    public MainAdapter(Context context, ArrayList<ArrayList<NineGridImageModel>> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position)==EMPTY_IMAGE) {
            ViewHolder viewHolder;
            if (convertView==null) {
                viewHolder=new ViewHolder();
                convertView=LayoutInflater.from(context).inflate(R.layout.adapter_empty, parent, false);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
        }
        else if (getItemViewType(position)==ONE_IMAGE) {
            ViewHolder viewHolder;
            if (convertView==null) {
                viewHolder=new ViewHolder();
                convertView=LayoutInflater.from(context).inflate(R.layout.adapter_one, parent, false);
                viewHolder.one_image= (SimpleDraweeView) convertView.findViewById(R.id.one_image);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(models.get(position).get(0).getImage()))
                    .setResizeOptions(new ResizeOptions(Dp2Px(context, 150), Dp2Px(context, 150))).build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request).setAutoPlayAnimations(true).build();
            viewHolder.one_image.setController(draweeController);
        }
        else if (getItemViewType(position)==MORE_IMAGE) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_more, parent, false);
                viewHolder.adapter_ninelayout = (NineGridLayout) convertView.findViewById(R.id.adapter_ninelayout);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.adapter_ninelayout.setImageData(models.get(position));
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (models.get(position).size()==0) {
            return EMPTY_IMAGE;
        }
        else if (models.get(position).size()==1) {
            return ONE_IMAGE;
        }
        else if (models.get(position).size()>1) {
            return MORE_IMAGE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    class ViewHolder {
        NineGridLayout adapter_ninelayout;

        SimpleDraweeView one_image;
    }

    public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
