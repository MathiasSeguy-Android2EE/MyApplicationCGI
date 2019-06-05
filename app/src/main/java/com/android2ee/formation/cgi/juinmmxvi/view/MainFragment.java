/**
 * <ul>
 * <li>MainFragment</li>
 * <li>com.android2ee.formation.cgi.juinmmxvi.view</li>
 * <li>23/06/2016</li>
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

package com.android2ee.formation.cgi.juinmmxvi.view;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android2ee.formation.cgi.juinmmxvi.R;
import com.android2ee.formation.cgi.juinmmxvi.model.Human;
import com.android2ee.formation.cgi.juinmmxvi.view.adapter.HumanRecycleViewAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 23/06/2016.
 */
public class MainFragment extends Fragment implements RecyclerViewItemClicked{
    private static final String TAG = "MainFragment";
    public static final String RESULT = "RESULT";
    public static final String MESSAGE = "MESSAGE";
    public static final String DELETE_DIALOG_TAG = "DeleteDialogTag";
    /***********************************************************
     *  Attributes
     **********************************************************/

    /**
     * The EditText for the message
     */
    private EditText edtMessage;
    /**
     * The Button to add the message in the result area
     */
    private Button btnAdd;
    /**
     * The result area
     */
    private RecyclerView rcvResult;
    /**
     * ListView dataset
     */
    private ArrayList<Human> messages;
    /**
     * The arrayadapter of the listView
     */
    private HumanRecycleViewAdapter recycleViewAdapter;
    /**
     * Tween animation when pushing the button add
     */
    private Animation btnAddPushedAnim;
    private AnimatorSet btnAddPushedAnimator;
    /***********************************************************
     *  TempAttributes
     **********************************************************/
    /**
     * Temp String
     */
    String messageTemp;
    /***********************************************************
     *  Managing LifeCycle
     **********************************************************/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        //instanciate the graphical components
        edtMessage= (EditText) view.findViewById(R.id.edtMessage);
        btnAdd= (Button) view.findViewById(R.id.btnAdd);
        rcvResult = (RecyclerView) view.findViewById(R.id.rcvResult);
        rcvResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        messages=new ArrayList<>();
//        for(int i=0;i<100;i++){
//            messages.add(new Human("Hello "+i,i));
//        }
        recycleViewAdapter =new HumanRecycleViewAdapter(getActivity(),this,messages);
        rcvResult.setAdapter(recycleViewAdapter);
        //add listeners
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMessageToResult();
            }
        });
        if(getResources().getBoolean(R.bool.ics)){
            btnAddPushedAnimator= (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(),R.animator.btnadd_pushed_anim);
            btnAddPushedAnimator.setTarget(btnAdd);
        }else{
            btnAddPushedAnim= AnimationUtils.loadAnimation(getActivity(),R.anim.btn_add_pushed);
        }
        if(savedInstanceState!=null) {
            messages.clear();
            for (Parcelable p : savedInstanceState.getParcelableArrayList(RESULT)) {
                messages.add((Human) p);
            }
            recycleViewAdapter.notifyItemChanged(0, messages.size());
            edtMessage.setText(savedInstanceState.getString(MESSAGE));
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onActivityCreated called and savedI="+savedInstanceState);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(RESULT,messages);
        outState.putString(MESSAGE,edtMessage.getText().toString());
        super.onSaveInstanceState(outState);
    }

    /***********************************************************
     *  Managing Menu
     **********************************************************/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mitDeleteAll){
            int messagesSize=messages.size();
            messages.clear();
            recycleViewAdapter.notifyItemRangeRemoved(0,messagesSize);
        }
        return super.onOptionsItemSelected(item);
    }

    /***********************************************************
     *  Business Methods
     **********************************************************/
    /**
     * Add the string contained in the edt to the result area
     */
    @SuppressLint("NewApi")
    private void addMessageToResult() {
        messageTemp = edtMessage.getText().toString();
        //first way
//        messages.add(message);
//        arrayAdapter.notifyDataSetChanged();
        //second way
        Log.e(TAG, "addMessageToResult");
        messages.add(0, new Human(messageTemp, messages.size()));
        recycleViewAdapter.notifyItemInserted(0);
        edtMessage.setText("");

        if (getResources().getBoolean(R.bool.ics)) {
            btnAddPushedAnimator.start();
        } else {
            btnAdd.startAnimation(btnAddPushedAnim);
        }
    }
    /**
     * Ask the user if he wants to copy the string message in the edt
     * @param message
     */
    public void copyMessageInEdt(String message){
        messageTemp=message;
        FragmentManager fm=getActivity().getSupportFragmentManager();
        DeleteDialog deleteDialog= (DeleteDialog) fm.findFragmentByTag(DELETE_DIALOG_TAG);
        if(deleteDialog==null){
            deleteDialog=new DeleteDialog(this);
        }
        deleteDialog.show(fm, DELETE_DIALOG_TAG);
    }

    private void okCopyInEdt(){
        //copy
        edtMessage.setText(messageTemp);
        //Toast
        Toast.makeText(getActivity(),R.string.toast_mess,Toast.LENGTH_LONG).show();
    }

    /***********************************************************
     * AlertDialog
     **********************************************************/
    @SuppressLint("ValidFragment")
    private static class DeleteDialog extends DialogFragment{
        WeakReference<MainFragment> mainFragmentWeak;
        public DeleteDialog(MainFragment frag){
            mainFragmentWeak=new WeakReference<MainFragment>(frag);
        }
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder alertBulder=new AlertDialog.Builder(getActivity());
            if(mainFragmentWeak!=null&&mainFragmentWeak.get()!=null){
                alertBulder.setMessage(getString(R.string.alertdialog_mes,mainFragmentWeak.get().messageTemp));
            }else{
                alertBulder.setMessage(getString(R.string.alertdialog_mes,":("));
            }
            alertBulder.setPositiveButton(R.string.alertdialog_oui, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(mainFragmentWeak!=null&&mainFragmentWeak.get()!=null){
                        mainFragmentWeak.get().okCopyInEdt();
                    }
                }
            });
            alertBulder.setNegativeButton(R.string.alertdialog_non, null);
            return alertBulder.create();
        }

        @Override
        public void onResume() {
            super.onResume();
            if(mainFragmentWeak!=null&&mainFragmentWeak.get()!=null){
                ((AlertDialog)getDialog()).setMessage(getString(R.string.alertdialog_mes,mainFragmentWeak.get().messageTemp));
            }
        }
    }
}


