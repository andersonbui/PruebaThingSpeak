package com.andersonbuitron.mipruebathingspeakweb.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.andersonbuitron.mipruebathingspeakweb.R;
import com.andersonbuitron.mipruebathingspeakweb.activities.ListDisponiblesActivity;
import com.andersonbuitron.mipruebathingspeakweb.adaptadores.DispositivoAdapter;
import com.andersonbuitron.mipruebathingspeakweb.gestores.GestorDispositivos;
import com.andersonbuitron.mipruebathingspeakweb.modelos.Dispositivo;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DispositivosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DispositivosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DispositivosFragment extends Fragment {

    ListView lvDisposRegistradosList;
    public static ArrayAdapter disposRegistradosAdapter;
    List<Dispositivo> list_dispos_registrados;
    private boolean mTwoPane;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DispositivosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DispositivosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DispositivosFragment newInstance(String param1, String param2) {
        DispositivosFragment fragment = new DispositivosFragment();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Informe","entro en onCreateView");
        View root = inflater.inflate(R.layout.fragment_dispositivos, container, false);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //rev
                GestorDispositivos objGD = new GestorDispositivos();
                objGD.AGREGAR = true;
                //revend

                Intent intent = new Intent(getActivity(), ListDisponiblesActivity.class);
                startActivity(intent);

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        //instancia del listView
        lvDisposRegistradosList = (ListView) root.findViewById(R.id.dispos_registrados_list);

        list_dispos_registrados = new ArrayList<>();
        //inicializa el adaptador con la fuente de datos
        disposRegistradosAdapter = new DispositivoAdapter(getActivity(), list_dispos_registrados);

        GestorDispositivos.getInstance(getActivity()).recuperarDispositivosBaseDatos(disposRegistradosAdapter);

        //relacionando la lista con el adaptador
        lvDisposRegistradosList.setAdapter(disposRegistradosAdapter);
        //setear una escucha a las clicks de los item
/*

        lvDisposRegistradosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Dispositivo dispositivoClickeado = (Dispositivo) disposRegistradosAdapter.getItem(position);
                Toast.makeText(getActivity(), "Agregando socket:\n " + dispositivoClickeado.getNombre(), Toast.LENGTH_SHORT).show();
                //view.setSelected(true);
                Intent intent = new Intent(getActivity(), AddDispositivoActivity.class);
                intent.putExtra("canal_clickeado", dispositivoClickeado);
                startActivity(intent);
            }

        });*/

        // Inflate the layout for this fragment
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
