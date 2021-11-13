package com.example.zwiggy.UI.OwnerUI.OwnerMenu;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zwiggy.Adapter.OwnerMenuAdapter;
import com.example.zwiggy.Data.MenuItem;
import com.example.zwiggy.Data.UserDetail;
import com.example.zwiggy.R;
import com.example.zwiggy.UI.OwnerDataActivity;
import com.example.zwiggy.databinding.FragmentOwnermenuBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.bson.Document;

import java.util.ArrayList;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class OwnerMenuFragment extends Fragment {

    private OwnerMenuViewModel ownerMenuViewModel;
    private FragmentOwnermenuBinding binding;
    RecyclerView rvOwnerMenu;
    ArrayList<MenuItem> menuItems;
    EditText minAmountEdit;
    ImageView minAmountButton;
    String Restaurant,Location;
    int MinAmnt;
    App app;
    User user;
    String appID = "hackit-qyzey",OwnerID;
    String LOG_TAG = OwnerMenuFragment.class.getSimpleName();
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> mongoCollectionOwner;
    Document res;
    TextView restrau_name,restrau_loc,restrau_minamnt;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ownerMenuViewModel =
                new ViewModelProvider(this).get(OwnerMenuViewModel.class);
        binding = FragmentOwnermenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        app = new App(new AppConfiguration.Builder(appID).build());
        user= UserDetail.getUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("zwiggy");
        mongoCollectionOwner = mongoDatabase.getCollection("owner");
        restrau_name= root.findViewById(R.id.restrau_name);
        restrau_loc=root.findViewById(R.id.restrau_loc);
        restrau_minamnt=root.findViewById(R.id.edit_min_amount);
        addRestaurantDetails();

        rvOwnerMenu = root.findViewById(R.id.rvOwnerMenu);
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Shahi Paneer", 210, "Restaurant Special"));
        menuItems.add(new MenuItem("Kadai Paneer", 230, "Spicy as Hell"));
        menuItems.add(new MenuItem("Malai Kofta", 225, "Sweet And Soft"));

        for(int i=0;i<3;i++){
            Log.i("info", menuItems.get(i).getDisc());
        }

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.add_menu_item_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddMenuItemActivity.class);
                startActivity(intent);
            }
        });


        OwnerMenuAdapter adapter=new OwnerMenuAdapter(getContext(), menuItems);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        rvOwnerMenu.setLayoutManager(layoutManager);
        rvOwnerMenu.setAdapter(adapter);

        minAmountEdit = root.findViewById(R.id.edit_min_amount);
        minAmountButton = root.findViewById(R.id.min_amount_button);
        minAmountEdit.setOnFocusChangeListener(editAmountFocus);
        minAmountButton.setVisibility(View.GONE);
        minAmountButton.setOnClickListener(minAmountButtonClick);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    void addRestaurantDetails()
    {
        Document queryFilter = new Document().append("userId", UserDetail.getUid());
        mongoCollectionOwner.findOne(queryFilter).getAsync(result->
        {
            if(result.isSuccess())
            {
                res=result.get();
                OwnerID=res.getObjectId("_id").toString();
                Restaurant=res.getString("Name");
                Location=res.getString("Loc");
                MinAmnt=res.getInteger("MinAmnt",0);
                restrau_name.setText(Restaurant);
                restrau_loc.setText(Location);
                restrau_minamnt.setText(MinAmnt+"");

                Log.v(LOG_TAG,"owner name found");
            }
            else
            {
                Log.v(LOG_TAG,"owner name not found");
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    View.OnFocusChangeListener editAmountFocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if(b){
                minAmountButton.setVisibility(View.VISIBLE);
            }else{
                minAmountButton.setVisibility(View.GONE);
            }
        }
    };
    View.OnClickListener minAmountButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            hideKeybaord(view);
            minAmountEdit.clearFocus();
        }
    };

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }



}