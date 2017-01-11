package com.example.alex.cherrysports;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import android.view.View.OnClickListener;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PageTwo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PageTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageTwo extends Fragment implements OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatesDataSource datasource;
    public ListView list;
    public String myDate = null;
    public GifImageView myGif;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PageTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PageTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static PageTwo newInstance(String param1, String param2) {
        PageTwo fragment = new PageTwo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page_two, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button addButton = (Button) getView().findViewById(R.id.buttonAdd);
        Button deleteButton = (Button) getView().findViewById(R.id.buttonDelete);
        CalendarView myCalendarView = (CalendarView) getView().findViewById(R.id.calendarView);
        myGif = (GifImageView) getView().findViewById(R.id.piggyGif);

        addButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        myCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView myCalendarView, int year, int month, int dayOfMonth)
            {
                //Snackbar.make(myCalendarView, "CA MARCHE", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                myDate = dayOfMonth + "/" + month+1 + "/" + year;
            }
        });

        datasource = new DatesDataSource(this.getContext());
        datasource.open();
        list = (ListView) getView().findViewById(R.id.listView);
        List<Date> values = datasource.getAllDates();

        // utilisez SimpleCursorAdapter pour afficher les
        // éléments dans une ListView
        ArrayAdapter<Date> adapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_list_item_1, values);
        list.setAdapter(adapter);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Date> adapter = (ArrayAdapter<Date>) list.getAdapter();
        Date date;
        switch (view.getId()) {
            case R.id.buttonAdd:
                //int nextInt = new Random().nextInt(3);
                // enregistrer le nouveau commentaire dans la base de données
                if (myDate != null) {
                    date = datasource.createDate(myDate);
                    adapter.add(date);
                    if (myGif != null){
                        myGif.setVisibility(View.VISIBLE);
                        myGif.postDelayed(new Runnable(){
                            public void run(){ myGif.setVisibility(View.GONE);}
                        },2000);
                    }
                }
                break;
            case R.id.buttonDelete:
                if (list.getAdapter().getCount() > 0) {
                    date = (Date) list.getAdapter().getItem(0);
                    datasource.deleteDate(date);
                    adapter.remove(date);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name


        void onFragmentInteraction(Uri uri);
    }
}
