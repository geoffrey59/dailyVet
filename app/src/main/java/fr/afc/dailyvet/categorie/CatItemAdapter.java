package fr.afc.dailyvet.categorie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.afc.dailyvet.R;
import fr.afc.dailyvet.menu.PrincipalActivity;
import fr.afc.dailyvet.model.Categorie;
import fr.afc.dailyvet.vetement.VetementActivity;

public class CatItemAdapter extends BaseAdapter {

    //
    private Context context;
    private List<Categorie> categories;
    private LayoutInflater inflater;
    public CatItemAdapter (Context context, List<Categorie> categories){
        this.context = context;
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (categories == null){
            return 0;
        }
        return categories.size();
    }

    @Override
    public Categorie getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.btncat, null );

        Categorie currentitem = getItem(position);
        String itemname = currentitem.getName();

        TextView itemNameView = convertView.findViewById(R.id.catname);
        itemNameView.setText(itemname);


        return convertView;
    }
}
