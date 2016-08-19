package com.renyu.ninegridview.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Created by Clevo on 2016/8/16.
 */
public class RVAdapter extends RecyclerView.Adapter {

    private static final int EMPTY_IMAGE=0;
    private static final int ONE_IMAGE=1;
    private static final int MORE_IMAGE=2;

    ArrayList<ArrayList<NineGridImageModel>> models;
    Context context;

    public RVAdapter(ArrayList<ArrayList<NineGridImageModel>> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==EMPTY_IMAGE) {
            View view=LayoutInflater.from(context).inflate(R.layout.adapter_empty, parent, false);
            return new RVEmptyViewHolder(view);
        }
        else if (viewType==ONE_IMAGE) {
            View view=LayoutInflater.from(context).inflate(R.layout.adapter_one, parent, false);
            return new RVOneViewHolder(view);
        }
        else if (viewType==MORE_IMAGE) {
            View view=LayoutInflater.from(context).inflate(R.layout.adapter_more, parent, false);
            return new RVMoreViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position)==EMPTY_IMAGE) {

        }
        else if (getItemViewType(position)==ONE_IMAGE) {
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(models.get(position).get(0).getImage()))
                    .setResizeOptions(new ResizeOptions(dp2px(context, 150), dp2px(context, 150))).build();
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request).setAutoPlayAnimations(true).build();
            ((RVOneViewHolder) holder).one_image.setController(draweeController);
        }
        else if (getItemViewType(position)==MORE_IMAGE) {
            ((RVMoreViewHolder) holder).adapter_ninelayout.setImageData(models.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (models.get(position).size()==0) {
            return EMPTY_IMAGE;
        }
        else if (models.get(position).size()==1) {
            return ONE_IMAGE;
        }
        else {
            return MORE_IMAGE;
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class RVEmptyViewHolder extends RecyclerView.ViewHolder {

        public RVEmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class RVOneViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView one_image;

        public RVOneViewHolder(View itemView) {
            super(itemView);

            one_image= (SimpleDraweeView) itemView.findViewById(R.id.one_image);
        }
    }

    public class RVMoreViewHolder extends RecyclerView.ViewHolder {

        NineGridLayout adapter_ninelayout;

        public RVMoreViewHolder(View itemView) {
            super(itemView);

            adapter_ninelayout= (NineGridLayout) itemView.findViewById(R.id.adapter_ninelayout);
        }
    }

    public int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
