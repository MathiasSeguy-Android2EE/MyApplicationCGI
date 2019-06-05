/**
 * <ul>
 * <li>HumanArrayAdapter</li>
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.cgi.juinmmxvi.R;
import com.android2ee.formation.cgi.juinmmxvi.model.Human;

import java.util.List;

/**
 * Created by Mathias Seguy - Android2EE on 21/06/2016.
 */
public class HumanArrayAdapter extends ArrayAdapter<Human>{
    LayoutInflater inflater;

    public HumanArrayAdapter(Context ctx, List<Human> dataset){
        super(ctx,0,dataset);
        inflater=LayoutInflater.from(ctx);
    }

    /***********************************************************
    *  Temp Attributes
    **********************************************************/

    Human humanTemp;
    View rowViewTemp;
    ViewHolder viewHolderTemp;

    /**
     * {@inheritDoc}
     *
     * @param position
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        humanTemp=getItem(position);
        rowViewTemp=convertView;
        if(rowViewTemp==null){
            if(getItemViewType(position)==0){
                rowViewTemp=inflater.inflate(R.layout.human_even,parent,false);
            }else{
                rowViewTemp=inflater.inflate(R.layout.human_odd,parent,false);
            }

            viewHolderTemp=new ViewHolder(rowViewTemp,this);
            rowViewTemp.setTag(viewHolderTemp);
        }
        viewHolderTemp= (ViewHolder) rowViewTemp.getTag();
        viewHolderTemp.getTxvName().setText(humanTemp.getName());
        viewHolderTemp.getTxvMessage().setText(humanTemp.getMessage());
        viewHolderTemp.getImvPicture().setBackgroundResource(humanTemp.getPictureId());
        viewHolderTemp.setCurrentPosition(position);
        //Do your stuff
        return rowViewTemp;
    }

    /**
     * Delete the item at the position position
     * @param position
     */
    public void deleteItem(int position){
        remove(getItem(position));
    }

    /***********************************************************
     * Managing odd/even row
     ***********************-***********************************/

    @Override
    public int getItemViewType(int position) {
        //depending on the position return in wich ViewPool pick the convertview
        humanTemp=getItem(position);

        return humanTemp.getName().equals("toto")?0:1;
    }

    @Override
    public int getViewTypeCount() {
        //how many pool of convertview do I have?
        return 2;
    }

    /***********************************************************
     * ViewHolder Pattern
     **********************************************************/

    private class ViewHolder{
        private static final String TAG = "ViewHolder";
        TextView txvName;
        TextView txvMessage;
        ImageView imvPicture;
        ImageView imvDelete;
        int currentPosition;

        View view;
        HumanArrayAdapter humanArrayAdapter;

        public ViewHolder(View view, HumanArrayAdapter humanArrayAdapter) {
            this.view = view;
            this.humanArrayAdapter=humanArrayAdapter;
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
            Log.e(TAG,"deleteThis "+currentPosition);
            humanArrayAdapter.deleteItem(currentPosition);
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

        public void setCurrentPosition(int currentPosition) {
            this.currentPosition = currentPosition;
        }
    }
}
