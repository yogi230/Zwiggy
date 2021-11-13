package com.example.zwiggy.UI.OwnerUI.OwnerMenu;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Service;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zwiggy.Adapter.OwnerMenuAdapter;
import com.example.zwiggy.R;
import com.example.zwiggy.databinding.FragmentOwnermenuBinding;

import java.util.ArrayList;

public class OwnerMenuFragment extends Fragment {

    private OwnerMenuViewModel ownerMenuViewModel;
    private FragmentOwnermenuBinding binding;
    RecyclerView rvOwnerMenu;
    ArrayList<String> menuItems;
    EditText minAmountEdit;
    ImageView minAmountButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ownerMenuViewModel =
                new ViewModelProvider(this).get(OwnerMenuViewModel.class);

        binding = FragmentOwnermenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rvOwnerMenu = root.findViewById(R.id.rvOwnerMenu);
        menuItems = new ArrayList<>();
        menuItems.add("Kadai Paneer");
        menuItems.add("SHahi Paneer");
        menuItems.add("Malai Kofta");

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