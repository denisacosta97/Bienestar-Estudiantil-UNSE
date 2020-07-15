package com.unse.bienestarestudiantil.Vistas.Activities.Becas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesSimpleAdapter;

import java.util.ArrayList;

public class SelectorReceptoresActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    OpcionesSimpleAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Opciones> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_receptores);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadData();

        loadListener();
    }

    private void loadData() {
        mList = new ArrayList<>();
        mList.add(new Opciones(LinearLayout.VERTICAL,1, "RECEPTOR 1", 0, 0));
        mList.add(new Opciones(LinearLayout.VERTICAL,2, "RECEPTOR 2", 0, 0));
        mList.add(new Opciones(LinearLayout.VERTICAL,3, "RECEPTOR 3", 0, 0));
        mList.add(new Opciones(LinearLayout.VERTICAL,4, "RECEPTOR 4", 0, 0));
        mList.add(new Opciones(LinearLayout.VERTICAL,5, "RECEPTOR 5", 0, 0));
        mList.add(new Opciones(LinearLayout.VERTICAL,6, "RECEPTOR 6", 0, 0));


        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        adapter = new OpcionesSimpleAdapter(mList, getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), ResumenTurnoActivity.class));
            }
        });
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
    }


}