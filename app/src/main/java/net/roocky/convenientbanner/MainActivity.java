package net.roocky.convenientbanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 顶部图片循环播放开源库Android-ConvenientBanner的使用
 */
public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener, OnItemClickListener {
    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private List<BannerItem> bannerItemList = new ArrayList<>();
    private String[] images = {
            "http://pic3.zhimg.com/da1fcaf6a02d1223d130d5b106e828b9.jpg",
            "http://p1.zhimg.com/dd/f1/ddf10a04227ea50fd59746dbcd13c728.jpg",
            "http://p3.zhimg.com/64/5c/645cde143c9a371005f3f749366cffad.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

    private ListView mListView;
    private ArrayList<String> transformerList = new ArrayList<>();
    private ArrayAdapter transformerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fresco.initialize(this);//加载图片

        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);

        //生成所需的数据
        for (int i = 0; i < images.length; i++) {
            bannerItemList.add(new BannerItem("第" + i + "张", images[i]));
        }

        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, bannerItemList)//设置需要切换的View
                .startTurning(2000)//设置自动切换（同时设置了切换时间间隔）
                .setPointViewVisible(true)//设置指示器是否可见
                .setOnItemClickListener(this)//设置点击监听事件
                .setPageIndicator(new int[]{R.drawable.dot_unselected, R.drawable.dot_selected})//设置指示器圆点
                .setManualPageable(true)//设置手动影响（false则无法手动切换）
        //.stopTurning()//关闭自动切换
                /*.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_LEFT)//设置指示器位置（左）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//设置指示器位置（中）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)//设置指示器位置（右）*/
        ;

        loadTestDatas();
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        if (convenientBanner.isTurning()) {
            convenientBanner.stopTurning();
        }
    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        if (!convenientBanner.isTurning()) {
            convenientBanner.startTurning(2000);
        }
    }

    /**
     * import com.bigkoo.convenientbanner.listener.OnItemClickListener;
     *
     * Banner轮播点击事件
     */
    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();

        switch (position) {
            case 0:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com/")));//打开百度
                break;
            case 1:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:10086")));//拨号界面
                break;
            case 2:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:110.2354,120.6548")));//打开地图上某个位置
                break;
            default:
                break;
        }

    }


    /*private ArrayList<Integer> localImages = new ArrayList<>();*/

    /**
     * ListView数据
     */
    public void loadTestDatas(){
        mListView= (ListView) this.findViewById(R.id.listview);
        mListView.setOnItemClickListener(this);

        //本地图片集合
        /*for (int position = 0; position < 7; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));*/

        //各种翻页效果
        transformerList.add(DefaultTransformer.class.getSimpleName());
        transformerList.add(AccordionTransformer.class.getSimpleName());
        transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
        transformerList.add(CubeInTransformer.class.getSimpleName());
        transformerList.add(CubeOutTransformer.class.getSimpleName());
        transformerList.add(DepthPageTransformer.class.getSimpleName());
        transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
        transformerList.add(FlipVerticalTransformer.class.getSimpleName());
        transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
        transformerList.add(RotateDownTransformer.class.getSimpleName());
        transformerList.add(RotateUpTransformer.class.getSimpleName());
        transformerList.add(StackTransformer.class.getSimpleName());
        transformerList.add(ZoomInTransformer.class.getSimpleName());
        transformerList.add(ZoomOutTranformer.class.getSimpleName());

        //transformerArrayAdapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,transformerList);
        //transformerArrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_activated_1,transformerList);
        //transformerArrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,transformerList);
        //transformerArrayAdapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,transformerList);
        //transformerArrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,transformerList);
        //transformerArrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_multiple_choice,transformerList);
        transformerArrayAdapter=new ArrayAdapter(this,android.R.layout.simple_selectable_list_item,transformerList);
        mListView.setAdapter(transformerArrayAdapter);

        transformerArrayAdapter.notifyDataSetChanged();
    }

    /**
     * ListView.OnItemClickListener的方法
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String transforemerName = transformerList.get(position);

        try {
            Class mClass = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            ABaseTransformer transforemer= (ABaseTransformer)mClass.newInstance();
            convenientBanner.getViewPager().setPageTransformer(true,transforemer);

            //部分3D特效需要调整滑动速度
            if(transforemerName.equals("StackTransformer")){
                convenientBanner.setScrollDuration(1200);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
