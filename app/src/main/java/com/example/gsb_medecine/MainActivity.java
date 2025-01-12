package com.example.gsb_medecine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;


public class MainActivity extends AppCompatActivity { // le terme clé Extends permet de faire hériter la class MainActivity avec la class AppComptaActivity
    private EditText edit_text_Denomination_du_medicament, edit_text_Forme_pharmaceutique, edit_text_Titulaires, edit_text_Denomination_substance;
    private Spinner edit_text_Spinner;
    private ListView listView;
    private Button btn_rechercher, btn_deconnexion, btn_quitter_application;
    private DatabaseHelper dbHelper;
    private static final String PREF_NAME = "UserPrefs"; // attribut de la classe (c'est comme initialiser des variables)Main
    private static final String KEY_USER_STATUS = "UserStatus"; // on met le type( String) puis les noms

    @Override
    protected void onCreate(Bundle savedInstanceState) { // constructeur
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// La vue associé a cette classe Java est activity_main.xml

        // initialiser les composants de l'interface utilisateur (UserInterface)
        edit_text_Denomination_du_medicament = findViewById(R.id.edit_text_Denomination_du_medicament);
        edit_text_Forme_pharmaceutique = findViewById(R.id.edit_text_Forme_pharmaceutique);
        edit_text_Titulaires = findViewById(R.id.edit_text_Titulaires);
        edit_text_Denomination_substance = findViewById(R.id.edit_text_Denomination_substance);
        edit_text_Spinner = findViewById(R.id.edit_text_Spinner);
        btn_rechercher = findViewById(R.id.btn_rechercher);
        listView = findViewById(R.id.listView);
        btn_deconnexion = findViewById(R.id.btn_deconnexion);
        btn_quitter_application = findViewById(R.id.btn_quitter_application);

        // Initialiser la database helper
        dbHelper = new DatabaseHelper(this);


        // Set up the spinner with Voies_dadministration data; appel de la fonction qui permet de paramétrer le spinner
        setupVoiesAdminSpinner();

        // Set up the click listener for the search button
        btn_rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform the search and update the ListView
                performSearch();
                //cacherClavier();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Get the selected item
                Medicament selectedMedicament = (Medicament) adapterView.getItemAtPosition(position);
                // Show composition of the selected medicament
                //afficherCompositionMedicament(selectedMedicament);
            }
    });
}

    private void setupVoiesAdminSpinner() {
        // Fetch distinct Voies_dadministration data from the database and populate the spinner
        List<String> voiesAdminList = dbHelper.getDistinctVoiesAdmin();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, voiesAdminList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_text_Spinner.setAdapter(spinnerAdapter);
    }

    private void performSearch() { // fonction qui va recuperer les saisies des utilisateurs
        String denomination = edit_text_Denomination_du_medicament.getText().toString().trim();// toString permet de changer en chaine de caractere, get permet de recuperer/visualiser la saisie, trim permet de retirer les espaces
        String formePharmaceutique = edit_text_Forme_pharmaceutique.getText().toString().trim();
        String titulaires = edit_text_Denomination_substance.getText().toString().trim();
        String denominationSubstance = edit_text_Denomination_substance.getText().toString().trim();
        String voiesAdmin = edit_text_Spinner.getSelectedItem().toString().trim();

        List<Medicament> searchResults = dbHelper.searchMedicament(denomination, formePharmaceutique, titulaires, denominationSubstance, voiesAdmin);
        MedicamentAdapter adapter = new MedicamentAdapter(this, searchResults);
        listView.setAdapter(adapter);

    }

    private void cacherClavier() {
        // Obtenez le gestionnaire de fenetre
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // Obtenez la vue actuellement focalisé, qui devrait être la vue avec le clavier
        View vueCourante = getCurrentFocus();

        // Vérifiez si la vue est non nulle pour éviter les erreurs
        if (vueCourante != null) {
            // Masquez le clavier
            imm.hideSoftInputFromWindow(vueCourante.getWindowToken(), 0);
        }
    }



    private boolean isUserAuthenticated () {// Methode
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String UserStatuts = preferences.getString(KEY_USER_STATUS, "");
        return "Authentification= ok".equals(UserStatuts);
    }
}
