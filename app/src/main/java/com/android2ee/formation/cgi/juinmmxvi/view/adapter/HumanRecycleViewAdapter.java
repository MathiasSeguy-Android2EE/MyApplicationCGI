/**
 * <ul>
 * <li>HumanRecycleViewAdapter</li>
 * <li>com.android2ee.formation.cgi.juinmmxvi.view.adapter</li>
 * <li>21/06/2016</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.formation.cgi.juinmmxvi.view.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.cgi.juinmmxvi.R;
import com.android2ee.formation.cgi.juinmmxvi.model.Human;
import com.android2ee.formation.cgi.juinmmxvi.view.RecyclerViewItemClicked;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 21/06/2016.
 */
public class HumanRecycleViewAdapter extends RecyclerView.Adapter<HumanRecycleViewAdapter.ViewHolder> {
    ArrayList<Human> dataset;
    LayoutInflater inflater;
    RecyclerViewItemClicked container;

    public HumanRecycleViewAdapter(Context ctx,RecyclerViewItemClicked container,ArrayList<Human> dataset){
        this.dataset=dataset;
        inflater=LayoutInflater.from(ctx);
        this.container=container;
    }

    /***********************************************************
     *  Temp Attributes
     **********************************************************/

    Human humanTemp;
    View rowViewTemp;
    ViewHolder viewHolderTemp;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            rowViewTemp=inflater.inflate(R.layout.human_even,parent,false);
        }else{
            rowViewTemp=inflater.inflate(R.layout.human_odd,parent,false);
        }
        viewHolderTemp=new ViewHolder(rowViewTemp,this);
        return viewHolderTemp;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getTxvName().setText(humanTemp.getName());
        holder.getTxvMessage().setText(humanTemp.getMessage());
        holder.getImvPicture().setBackgroundResource(humanTemp.getPictureId());
    }
    @Override
    public int getItemCount() {
        return dataset.size();
    }
    /**
     * Delete the item at the position position
     * @param viewHolder
     */
    public void deleteItem(RecyclerView.ViewHolder viewHolder){
        int position=viewHolder.getAdapterPosition();
        dataset.remove(position);
        notifyItemRemoved(position);
    }

    public void itemClicked(String messageToCopy){
        container.copyMessageInEdt(messageToCopy);
    }

    /***********************************************************
     * Managing odd/even row
     ***********************-***********************************/

    @Override
    public int getItemViewType(int position) {
        //depending on the position return in wich ViewPool pick the convertview
        humanTemp=dataset.get(position);
        return humanTemp.getName().equals("toto")?0:1;
    }


    /***********************************************************
     * ViewHolder pattern
     **********************************************************/

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private static final String TAG = "ViewHolder";
        TextView txvName;
        TextView txvMessage;
        ImageView imvPicture;
        ImageView imvDelete;

        View view;
        HumanRecycleViewAdapter adapter;

        public ViewHolder(View itemView,HumanRecycleViewAdapter adapter) {
            super(itemView);
            this.adapter=adapter;
            this.view = itemView;
            this.view.setOnClickListener(this);
            txvName= (TextView) view.findViewById(R.id.txvName);
            txvMessage=(TextView) view.findViewById(R.id.txvMessage);
            imvPicture= (ImageView) view.findViewById(R.id.imvPicture);
            imvDelete= (ImageView) view.findViewById(R.id.imvDelete);
            imvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteThis();
                }
            });

        }

        public void deleteThis(){
            adapter.deleteItem(this);
        }
        public ImageView getImvPicture() {
            return imvPicture;
        }

        public TextView getTxvMessage() {
            return txvMessage;
        }

        public TextView getTxvName() {
            return txvName;
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            adapter.itemClicked(txvMessage.getText().toString());
        }
    }

}
