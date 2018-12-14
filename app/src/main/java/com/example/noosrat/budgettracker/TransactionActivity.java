package com.example.noosrat.budgettracker;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noosrat.budgettracker.Singleton.SpentSingleton;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.PlaceDetectionClient;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class TransactionActivity extends AppCompatActivity {

    private TextView tvDescription;
    private TextView tvAmount;
    private TextView tvDate;
    private ImageView imgMerchant;
    private ImageView imgMap;
    private TextView txtHistory;
    private TextView txtVisits;
    private TextView txtAverage;



    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;
    private TextView tvPrice;
    private TextView tvAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_transaction);

        tvDescription = (TextView) findViewById(R.id.recipient);
        tvAmount = (TextView) findViewById(R.id.amount);
        tvDate = (TextView) findViewById(R.id.date);
        imgMerchant = (ImageView) findViewById(R.id.imageView);
        imgMap = (ImageView) findViewById(R.id.mapView);
        tvPrice = (TextView) findViewById(R.id.priceLevel);
        tvAddress = (TextView) findViewById(R.id.address);
        txtHistory = (TextView) findViewById(R.id.historyLabel);
        txtVisits = (TextView) findViewById(R.id.visits);
        txtAverage = (TextView) findViewById(R.id.average);


        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);

        // TODO: Start using the Places API.

        mGeoDataClient.getPlaceById("ChIJQ69nSudCzB0RLHK1j9vJN90").addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                if (task.isSuccessful()) {

                    PlaceBufferResponse places = task.getResult();
                    Place myPlace = places.get(0);

                    String address = (String) myPlace.getAddress();
                    String priceLevel = priceLevel(myPlace.getPriceLevel());
                    getPhotos("ChIJQ69nSudCzB0RLHK1j9vJN90");

                    //String imageURL = (String) getIntent().getExtras().get("imageURL");
                    String amount = (String) getIntent().getExtras().get("amount");
                    String date = (String) getIntent().getExtras().get("date");
                    String recipient = (String) getIntent().getExtras().get("recipient");



                    tvAddress.setText(address);
                    tvPrice.setText(priceLevel);
                    tvDescription.setText(recipient);
                    txtHistory.setText("Your "+recipient+" History");
                    tvAmount.setText(amount);
                    tvDate.setText(date);


                    int count = 0;
                    float totalExpense = 0;

                    for (int i = 0; i < SpentSingleton.transactionList.size() ; i++){
                        if (SpentSingleton.transactionList.get(i).getMerchant().getName().equals(recipient)){
                            count++;
                            totalExpense = totalExpense + SpentSingleton.transactionList.get(i).getNumberAmount();
                        }
                    }

                    txtVisits.setText(count+"");
                    txtAverage.setText(SpentSingleton.currencyFormat.format( totalExpense/count));


                    String mapURL = "";
                    Log.i("blah blah", "Place found: " + myPlace.getName());
                    places.release();
                } else {
                    Log.e("blah blah", "Place not found.");
                }
            }
        });

//        String mapURL = "https://maps.googleapis.com/maps/api/place/photo?" +
//                "maxwidth=1600" +
//                "&photoreference=CmRaAAAA0wzIlB2Ti659pTkG_YLasemyMOAKpdQmtrnOpucUDY2jXBFAG65lf0CgKmv3PzZqQgkgyM77U4VYOq7XtMeewsjc5D8eAzJoVP4Hoksr-xDTvcMCtS4XRlsd1EL_2lmrEhB_Vq8_m4aFszw194Kv7okxGhQqEtfhlbq1MY_2ga2b5z7WOpw69w" +
//                "&key=AIzaSyAa6iFn0bZ83vs5ppVq9Nx2HhoX6horzwA";
//
        String imageURL = (String) getIntent().getExtras().get("imageURL");
//        String amount = (String) getIntent().getExtras().get("amount");
//        String date = (String) getIntent().getExtras().get("date");
//        String recipient = (String) getIntent().getExtras().get("recipient");



        Glide.with(this)
                .load(imageURL)
                .apply(RequestOptions.circleCropTransform())
                .into(imgMerchant);
//
//        Glide.with(this)
//                .load(mapURL)
//                .into(imgMap);

//        tvDescription.setText(recipient);
//        tvAmount.setText(amount);
//        tvDate.setText(date);



    }

    // Request photos and metadata for the specified place.
    private void getPhotos(String placeId) {
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                // Get the first photo in the list.
                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                // Get the attribution text.
                CharSequence attribution = photoMetadata.getAttributions();
                // Get a full-size bitmap for the photo.
                Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                        PlacePhotoResponse photo = task.getResult();
                        Bitmap bitmap = photo.getBitmap();
                        imgMap.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    String priceLevel(int priceLevel){
        switch (priceLevel){
            case 1: return "R";
            case 2: return "RR";
            case 3: return "RRR";
            case 4: return "RRRR";
            case 5: return "RRRRR";
        }
        return "";
    }
}
