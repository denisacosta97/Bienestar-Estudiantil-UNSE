package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.TreeView.TreeNode;
import com.unse.bienestarestudiantil.R;

public class TreeAdapter extends TreeNode.BaseNodeViewHolder<TreeAdapter.IconTreeItem> {

    public static final int PARENT = 1;
    public static final int CHILD = 2;

    private ImageView img;
    private TextView tvValue;
    private CheckBox mCheckBox;

    private Context mContext;
    private boolean isOpen;
    private int tipoNodo;
    private int tabMargin;
    private int level;
    private int rotation = 0;

    public TreeAdapter(Context context, boolean isOpen, int tipoNodo, int tabMargin, int level) {
        super(context);
        this.isOpen = isOpen;
        this.tipoNodo = tipoNodo;
        this.tabMargin = tabMargin;
        this.level = level;
        this.mContext = context;
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        if (tipoNodo == PARENT) {
            view = inflater.inflate(R.layout.roles_padre, null, false);
        } else if (tipoNodo == CHILD) {
            view = inflater.inflate(R.layout.roles_hijo, null, false);
        }

        if (tabMargin == PARENT) {
            tabMargin = getDimens(10);
        }
        int dimen = 10;
        view.setPadding(tabMargin, getDimens(dimen), getDimens(dimen),
                getDimens(dimen));

        img = view.findViewById(R.id.image);
        tvValue = view.findViewById(R.id.text);

        img.setImageResource(value.icon);
        tvValue.setText(value.text);

        mCheckBox = view.findViewById(R.id.checkbox);
        mCheckBox.setChecked(isOpen);
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    isOpen = false;
                    mCheckBox.setChecked(false);
                } else {
                    isOpen = true;
                    mCheckBox.setChecked(true);
                }
            }
        });

        switch (level) {
            case 1:
                tvValue.setTextColor(mContext.getResources().getColor(R.color.colorLevel1));
                break;
            case 2:
                tvValue.setTextColor(mContext.getResources().getColor(R.color.colorLevel2));
                break;
            case 3:
                tvValue.setTextColor(mContext.getResources().getColor(R.color.colorLevel3));
                break;
            default:
                tvValue.setTextColor(mContext.getResources().getColor(R.color.colorLevel4));
                break;
        }

        return view;
    }


    public void toggle(boolean active, TreeNode nod) {
       /* if (nod.getChildren().size() == 0)
            img.setImageDrawable(null);
        else*/
        ObjectAnimator animator = ObjectAnimator.ofFloat(img, "rotation",
                active ? 0 : 90, active ? 90 : 0);
        animator.setDuration(200);
        animator.start();
        //img.setImageResource(!active ? R.drawable.ic_keyboard_arrow : R.drawable.ic_keyboard_arrow_down);
    }

    public boolean getChecked() {
        return isOpen;
    }

    public void setChecked(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public static class IconTreeItem {
        private int icon;
        private String text;

        public IconTreeItem(int icon, String text) {
            this.icon = icon;
            this.text = text;
        }
    }

    private int getDimens(int resId) {
        return (int) (resId /
                context.getResources().getDisplayMetrics().density);
    }
}
