package com.example.staydream.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.example.staydream.Data.DataManager;
import com.example.staydream.Fragments.HotelListFragment;
import com.example.staydream.Fragments.SpecialOfferFragment;
import com.example.staydream.Interface.SpecialOfferDetailsCallback;
import com.example.staydream.Models.Hotel;
import com.example.staydream.Models.HotelList;
import com.example.staydream.R;
import com.example.staydream.Utilities.SignalManager;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText to_date_edit_text;
    private TextInputEditText from_date_edit_text;
    private AutoCompleteTextView autoComplete_guest;
    private AppCompatEditText search_bar_main;
    private String fromDate;
    private String toDate;
    private ExtendedFloatingActionButton main_SIGN_OUT;
    private ExtendedFloatingActionButton main_user_reservation;
    private ExtendedFloatingActionButton main_user_favorites;
    private FrameLayout main_special_offer_FRAME;
    private SpecialOfferFragment specialOfferListFragment;
    private int selectedGuestNum = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();


        // HotelList hotelList = DataManager.getInstance().loadHotelsFromJson(this,"hotels.json");
        // DataManager.getInstance().saveHotelListDB(hotelList);
    }

    private void initViews() {
        search_bar_main.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = search_bar_main.getText() != null ? search_bar_main.getText().toString() : "";
                performSearch(query,null);
                return true;
            }
            return false;
        });

        main_SIGN_OUT.setOnClickListener(v -> signOut());

        from_date_edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(from_date_edit_text);
            }
        });

        main_user_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactToUserReservation();
            }
        });

        main_user_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FavoriteHotelActivity.class);
                intent.putExtra(FavoriteHotelActivity.KEY_FROM_DATE,fromDate);
                intent.putExtra(FavoriteHotelActivity.KEY_TO_DATE,toDate);
                intent.putExtra(FavoriteHotelActivity.KEY_GUESTS,selectedGuestNum);
                startActivity(intent);
                finish();
            }
        });

        to_date_edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(to_date_edit_text);
            }
        });

        //sets guests options and adapter
        Integer[] guestOption = new Integer[]{1,2,3,4,5,6};
        ArrayAdapter<Integer> guestAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, guestOption);
        autoComplete_guest.setAdapter(guestAdapter);
        autoComplete_guest.setDropDownBackgroundResource(R.color.white);
        autoComplete_guest.setOnItemClickListener((parent, view, position, id) -> {
            selectedGuestNum = (int) parent.getItemAtPosition(position);
        });

        //callback in order to transact to about hotel with the current dates.
        specialOfferListFragment = new SpecialOfferFragment(new SpecialOfferDetailsCallback() {
            @Override
            public void checkValidDetails(Hotel hotel) {
                performSearch(null,hotel);
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.main_special_offer_FRAME,specialOfferListFragment).commit();



    }

    private void transactToUserReservation() {
        Intent intent = new Intent(this,UserReservationActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDatePicker(TextInputEditText editText) {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();

        //Set the start date to today
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        constraintsBuilder.setStart(today);

        //Create a DateValidator that only allows dates from today onwards
        CalendarConstraints.DateValidator dateValidator = DateValidatorPointForward.now();
        constraintsBuilder.setValidator(dateValidator);

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(today)
                .setCalendarConstraints(constraintsBuilder.build())
                .setTheme(R.style.CustomMaterialCalendarTheme)
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = sdf.format(new Date(selection));
            editText.setText(formattedDate);
            if (editText.getId() == R.id.from_date_edit_text)
                fromDate = formattedDate;
            else if (editText.getId() == R.id.to_date_edit_text)
                toDate = formattedDate;
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }


    private void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }


    //regular search/transaction directly to about hotel
    private void performSearch(String query, Hotel hotel) {

        if(fromDate == null  || toDate == null)
            SignalManager.getInstance().toast("Choose Your Vacation Dates");
        else if (!validDatesCheck(fromDate,toDate))
            SignalManager.getInstance().toast("Return Date Isn't after Arrival Date");
        else if (selectedGuestNum == -1)
            SignalManager.getInstance().toast("Choose number of guests");
         else if(hotel == null){
            Intent intent = new Intent(this,HotelViewActivity.class);
            intent.putExtra(HotelViewActivity.KEY_SEARCH,query);
            intent.putExtra(HotelViewActivity.KEY_FROM_DATE,fromDate);
            intent.putExtra(HotelViewActivity.KEY_TO_DATE,toDate);
            intent.putExtra(HotelViewActivity.KEY_GUESTS,selectedGuestNum);
            startActivity(intent);
            finish();
        } else{
            Intent intent = new Intent(this,AboutHotelActivity.class);
            intent.putExtra(AboutHotelActivity.KEY_FROM_DATE,fromDate);
            intent.putExtra(AboutHotelActivity.KEY_TO_DATE,toDate);
            intent.putExtra(AboutHotelActivity.KEY_GUESTS,selectedGuestNum);
            intent.putExtra(AboutHotelActivity.KEY_HOTEL,hotel);
            startActivity(intent);
            finish();
        }
    }


    private boolean validDatesCheck(String fromDate, String toDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date dateFrom = sdf.parse(fromDate);
            Date dateTo = sdf.parse(toDate);

            if (dateFrom == null || dateTo == null) {
                return false;
            }
            return dateTo.after(dateFrom);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void findViews() {
        main_SIGN_OUT = findViewById(R.id.main_SIGN_OUT);
        from_date_edit_text = findViewById(R.id.from_date_edit_text);
        to_date_edit_text = findViewById(R.id.to_date_edit_text);
        main_user_reservation = findViewById(R.id.main_user_reservation);
        autoComplete_guest = findViewById(R.id.autoComplete_guest);
        main_special_offer_FRAME = findViewById(R.id.main_special_offer_FRAME);
        search_bar_main = findViewById(R.id.search_bar_main);
        main_user_favorites = findViewById(R.id.main_user_favorites);
    }


    //focus out of text box when press elsewhere
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}