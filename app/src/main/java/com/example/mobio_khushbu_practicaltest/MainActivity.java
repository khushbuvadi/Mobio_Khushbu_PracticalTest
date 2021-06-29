package com.example.mobio_khushbu_practicaltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobio_khushbu_practicaltest.adapter.ColorAdapter;
import com.example.mobio_khushbu_practicaltest.model.Brand;
import com.example.mobio_khushbu_practicaltest.model.Color;
import com.example.mobio_khushbu_practicaltest.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.listView) ListView listView;

    ArrayList<Product> main = new ArrayList<Product>();
    ArrayList<Product> addedProduct = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LoadJsonFile();
    }

    private void LoadJsonFile() {
        String jsonFileString = Utils.getJsonFromAssets(getApplicationContext(), "Products.json");
        Log.e("data","==> " + jsonFileString);
        String picture="";
        try {
            JSONArray obj = new JSONArray(jsonFileString);
            for (int i=0;i<obj.length();i++){
                JSONObject jsonObject = obj.getJSONObject(i);
                picture = jsonObject.getString("picture");
                JSONArray colors = jsonObject.getJSONArray("colors");
                ArrayList<String> colorList = new ArrayList<String>();
                for (int c=0;c<colors.length();c++){
                    colorList.add(colors.getString(c));
                }
                ArrayList<Brand> brandList = new ArrayList<Brand>();
                JSONArray brands = jsonObject.getJSONArray("brands");
                brandList.add(new Brand("0","Select Brand"));
                for (int b=0;b<brands.length();b++){
                    JSONObject bO = brands.getJSONObject(b);
                    brandList.add(new Brand(String.valueOf(bO.getInt("id")),bO.getString("name")));
                }
                main.add(new Product(jsonObject.getString("_id"),jsonObject.getString("price"),picture,
                        jsonObject.getString("productName"),colorList,brandList,"","",""));
            }
            ListAdapter customAdapter = new ListAdapter(this, R.layout.item_product, main);
            listView .setAdapter(customAdapter);
            listView.setDivider(null);
            addedProduct = (ArrayList<Product>) main.clone();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnSubmitProduct)
    public void setBtnSubmitProduct(View view){
        ArrayList<Product> FinalList = new ArrayList<Product>();
        for (int i=0;i<addedProduct.size();i++){
            if (addedProduct.get(i).getSelectedColor().length()!=0 &&
                    !addedProduct.get(i).getSelectedBrand().equals("Select Brand") && addedProduct.get(i).getQty().length()!=0){
                    FinalList.add(addedProduct.get(i));
            }
        }
        if (FinalList.size()!=0){
            Intent intent = new Intent(MainActivity.this,OrderSummary.class);
            Bundle bundle  = new Bundle();
            bundle.putSerializable("list",FinalList);
            intent.putExtra("BUNDLE",bundle);
            startActivity(intent);
        }
        Log.e("final","==> "+ FinalList);
    }

    public class ListAdapter extends ArrayAdapter<Product> {

        private int resourceLayout;
        private Context mContext;
        String SelectedColor="";
        HashMap<Integer,Integer> selectedItems = new HashMap<Integer, Integer>();

        public ListAdapter(Context context, int resource, ArrayList<Product> items) {
            super(context, resource, items);
            this.resourceLayout = resource;
            this.mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(mContext);
                v = vi.inflate(resourceLayout, null);
            }

            Product product = getItem(position);

            if (product != null) {
                CircleImageView ivImage = (CircleImageView) v.findViewById(R.id.ivImage);
                Spinner spBrand = (Spinner) v.findViewById(R.id.spBrand);
                EditText txtQty = (EditText) v.findViewById(R.id.txtQty);
                TextView tvPName = (TextView) v.findViewById(R.id.tvPName);
                TextView tvPrice = (TextView) v.findViewById(R.id.tvPrice);
                RadioGroup radiogroup = (RadioGroup) v.findViewById(R.id.radiogroup);
                RadioButton btnRed = (RadioButton) v.findViewById(R.id.btnRed);
                RadioButton btnBlue = (RadioButton) v.findViewById(R.id.btnBlue);
                RadioButton btnGreen = (RadioButton) v.findViewById(R.id.btnGreen);

                Glide.with(mContext).load(product.getImage()).thumbnail(Glide.with(mContext).load(R.drawable.place)).into(ivImage);
                tvPName.setText(product.getProductName());
                tvPrice.setText(product.getPrice());
                ArrayList<String> list = new ArrayList<String>();
                if (product.getBrandList().size()!=0){
                    for (int i=0;i<product.getBrandList().size();i++){
                        list.add(product.getBrandList().get(i).getBrandName());
                    }
                }
                ArrayAdapter aa = new ArrayAdapter(mContext,android.R.layout.simple_spinner_item,list);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spBrand.setAdapter(aa);

                if ( selectedItems.get( position ) != null ) {
                    ((Spinner) v.findViewById( R.id.spBrand )).setSelection( selectedItems.get( position ) );
                }

                spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        selectedItems.put( position, pos );
                        product.setSelectedBrand(parent.getSelectedItem().toString());
                        addedProduct.set(position,new Product(product.getId(),product.getPrice(),product.getImage(),product.getProductName(),
                                product.getColorList(),product.getBrandList(),txtQty.getText().toString(),SelectedColor,
                                parent.getSelectedItem().toString()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                        SelectedColor = radioButton.getText().toString();
                        product.setSelectedColor(SelectedColor);
                        addedProduct.set(position,new Product(product.getId(),product.getPrice(),product.getImage(),product.getProductName(),
                                product.getColorList(),product.getBrandList(),txtQty.getText().toString(),SelectedColor,
                                spBrand.getSelectedItem().toString()));
                    }
                });

                ArrayList<Color>  colorList = new ArrayList<Color>();
                if (product.getColorList().size()!=0){
                    for (int i=0;i<product.getColorList().size();i++){
                        colorList.add(new Color(product.getColorList().get(i),false));
                        if (product.getColorList().get(i).equals("Red"))
                            btnRed.setVisibility(View.VISIBLE);
                        if (product.getColorList().get(i).equals("Blue"))
                            btnBlue.setVisibility(View.VISIBLE);
                        if (product.getColorList().get(i).equals("Green"))
                            btnGreen.setVisibility(View.VISIBLE);
                    }
                    ColorAdapter coloradapter = new ColorAdapter(mContext,colorList);
                    //holder.rvColorsList.setAdapter(coloradapter);
                }

                txtQty.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        product.setQty(s.toString());
                        addedProduct.set(position,new Product(product.getId(),product.getPrice(),product.getImage(),product.getProductName(),
                                product.getColorList(),product.getBrandList(),txtQty.getText().toString(),SelectedColor,
                                spBrand.getSelectedItem().toString()));
                    }
                });
            }

            return v;
        }
        @Override
        public int getViewTypeCount() {

            return getCount();
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }
    }
}