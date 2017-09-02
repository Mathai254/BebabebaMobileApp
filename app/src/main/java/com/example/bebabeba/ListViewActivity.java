package com.example.bebabeba;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewActivity extends ListActivity {

    public String[] DRIVERS, DRIVERSNAMES, DRIVERSPHONENUMBERS, DRIVERSEMAILS, DRIVERSPRICES;
    public DatabaseHelper myDb = new DatabaseHelper(this);
    public String slocality, passengerEmail;

/*    static final String[] COUNTRIES = new String[] {

            "Afghanistan", "Albania", "Algeria", "American Samoa",
            "Andorra", "Angola", "Anguilla", "Antarctica",
            "Antigua and Barbuda", "Argentina", "Armenia", "Aruba",
            "Australia", "Austria", "Azerbaijan", "Bahrain",
            "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize",
            "Benin", "Bermuda", "Bhutan", "Bolivia",
            "Bosnia and Herzegovina", "Botswana", "Bouvet Island",
            "Brazil", "British Indian Ocean Territory"
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list_view);

        Intent intent = getIntent();

        slocality = intent.getStringExtra("locality");
        passengerEmail = intent.getStringExtra("passengerEmail");
        final String passengerEmail = intent.getStringExtra("passengerEmail");
        Bundle b = this.getIntent().getExtras();
        DRIVERS = b.getStringArray("drivers");
        DRIVERSNAMES = b.getStringArray("driversNames");
        DRIVERSPHONENUMBERS = b.getStringArray("driversPhoneNos");
        DRIVERSEMAILS = b.getStringArray("driversEmails");
        DRIVERSEMAILS = b.getStringArray("driversEmails");
        DRIVERSPRICES = b.getStringArray("driversPrices");

        setListAdapter(new ArrayAdapter< String >(this, android.R.layout.simple_list_item_1, DRIVERS));
        getListView().setTextFilterEnabled(true);

    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        new AlertDialog.Builder(this)
                .setTitle("Request for a ride")
                .setMessage("from " + getListView().getItemAtPosition(position))
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(ListViewActivity.this, PassengerBookingActivity.class);
                                intent.putExtra("price", DRIVERSPRICES[position]);
                                intent.putExtra("source", slocality);
                                intent.putExtra("passengerEmail", passengerEmail);
                                intent.putExtra("dName", DRIVERSNAMES[position]);
                                intent.putExtra("dEmail", DRIVERSEMAILS[position]);
                                intent.putExtra("dPhone", DRIVERSPHONENUMBERS[position]);
                                ListViewActivity.this.startActivity(intent);

                            }
                        })
                .show();

/*        Toast.makeText(ListViewActivity.this,
                "ListView: " + l.toString() + "\n" +
                        "View: " + v.toString() + "\n" +
                        "position: " + String.valueOf(position) + "\n" +
                        "id: " + String.valueOf(id),
                Toast.LENGTH_LONG).show();*/
    }
    public void showMessage (String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
