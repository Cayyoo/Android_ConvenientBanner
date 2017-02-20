package net.roocky.convenientbanner;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * 网络图片加载例子，可参考实现其他类型或方式的图片加载
 *
 * 图片轮播控件ConvenientBanner
 *
 */
public class NetworkImageHolderView implements Holder<BannerItem> {
    private View view;

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以用代码创建，不一定是Image，任何控件都可以进行翻页
        //ImageView imageView = new ImageView(context);
        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        view = LayoutInflater.from(context).inflate(R.layout.banner_item, null, false);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerItem data) {
        ((TextView)view.findViewById(R.id.tv_title)).setText(data.getTitle());
        ((SimpleDraweeView)view.findViewById(R.id.sdv_background)).setImageURI(Uri.parse(data.getImage()));
    }

}