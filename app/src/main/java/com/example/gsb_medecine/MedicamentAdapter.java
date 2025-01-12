package com.example.gsb_medecine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MedicamentAdapter extends ArrayAdapter<Medicament> {
private TextView tvCodeCIS,tvDenomination,tvFormePharmaceutique,tvVoieAdmin,tvTitulaire,tvStatusAdmin,tvNbMolecule;
    public MedicamentAdapter(Context context, List<Medicament> medicaments) {
        super(context, 0, medicaments);
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Medicament medicament = getItem(position);
            tvCodeCIS = convertView.findViewById(R.id.tvCodeCIS);
            tvDenomination=convertView.findViewById(R.id.tvDenomination);
            tvFormePharmaceutique=convertView.findViewById(R.id.tvFormePharmaceutique);
            tvVoieAdmin=convertView.findViewById(R.id.tvVoieDadministration);
            tvTitulaire=convertView.findViewById(R.id.Titulaire);
            tvStatusAdmin=convertView.findViewById(R.id.SatutAdministratif);
            tvNbMolecule=convertView.findViewById(R.id.NombresMolecules);

            tvCodeCIS.setText(String.valueOf(medicament.getCodeCIS()));
            tvDenomination.setText(String.valueOf(medicament.getDenomination()));
            tvFormePharmaceutique.setText(String.valueOf(medicament.getFormePharmaceutique()));
            tvVoieAdmin.setText(String.valueOf(medicament.getVoiesAdmin()));
            tvTitulaire.setText(String.valueOf(medicament.getTitulaires()));
            tvStatusAdmin.setText(String.valueOf(medicament.getStatutAdministratif()));
            tvNbMolecule.setText(String.valueOf(medicament.getnbMolecules()));
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_medicament, parent, false);
            }
//cette class va heriter de medicament cette classe est en lien avec l objet medicament et va affecter a la vue les resultat
// un ta    }bleau qui va adapter un emseble de tableau une resultat de recherche

            //here le reste du codee
            return convertView;


        }
    }
















