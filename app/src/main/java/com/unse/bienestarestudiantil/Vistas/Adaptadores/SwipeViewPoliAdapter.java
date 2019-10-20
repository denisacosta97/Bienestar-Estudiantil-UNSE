package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Modelos.SwipeViewPoliModel;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.SwipeViewPoliDetails;

import java.util.List;

public class SwipeViewPoliAdapter extends PagerAdapter {

    private List<SwipeViewPoliModel> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public SwipeViewPoliAdapter(List<SwipeViewPoliModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_swipe_view_poli, container, false);

        FontChangeUtil fontChanger = new FontChangeUtil(context.getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup) view);

        ImageView imageView;
        TextView title, desc, prize;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        prize = view.findViewById(R.id.txtprize);
        desc = view.findViewById(R.id.desc);

        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());
        prize.setText(models.get(position).getPrize());
        desc.setText(models.get(position).getDesc());
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, SwipeViewPoliDetails.class);
//                intent.putExtra("param", models.get(position).getTitle());
//                context.startActivity(intent);
//                // finish();
//            }
//        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
