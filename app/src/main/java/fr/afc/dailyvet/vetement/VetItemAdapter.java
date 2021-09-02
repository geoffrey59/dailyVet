package fr.afc.dailyvet.vetement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.afc.dailyvet.R;
import fr.afc.dailyvet.model.Vetement;

public class VetItemAdapter extends BaseAdapter {



        //
        private Context context;
        private List<Vetement> vetements;
        private LayoutInflater inflater;
        public VetItemAdapter (Context context, List<Vetement> vetements){
            this.context = context;
            this.vetements = vetements;
            this.inflater = LayoutInflater.from(context);
        }


        public int getCount() {
            return vetements.size();
        }


        public Vetement getItem(int position) {
            return vetements.get(position);
        }


        public long getItemId(int position) {
            return 0;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.affvetement, null );

            Vetement currentitem = getItem(position);
            String itemname = currentitem.getMarque() + " " + currentitem.getCouleur();



            TextView itemNameView = convertView.findViewById(R.id.catname);
            ImageView itemImage  = convertView.findViewById(R.id.imageVet);
            Picasso.get().load(currentitem.getUrl()).into(itemImage);
            itemNameView.setText(itemname);

            return convertView;
        }


}
