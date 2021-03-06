package com.scand.commerce.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scand.commerce.Animations;
import com.scand.commerce.R;
import com.scand.commerce.order.OrderDialogFragment;
import com.scand.commerce.widgets.TintedProgressBar;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity implements ProductView, Button.OnClickListener, OrderDialogFragment.OrderDialogStateListener {

    public static boolean isActive = false;

    private ProductPresenter productPresenter;

    private View v;
    private ImageView ivProductImage;
    private LinearLayout llProductBuy;
    private TextView tvPrdTitle, tvPrdPrice, tvPrdDescription;
    private NestedScrollView nsvProduct;
    private TintedProgressBar pbProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        v = getWindow().getDecorView().getRootView();

        ivProductImage = findViewById(R.id.ivProductImage);
        llProductBuy = findViewById(R.id.llProductBuy);
        llProductBuy.setOnClickListener(this);
        tvPrdTitle = findViewById(R.id.tvPrdTitle);
        tvPrdPrice = findViewById(R.id.tvPrdPrice);
        tvPrdDescription = findViewById(R.id.tvPrdDescription);
        nsvProduct = findViewById(R.id.nsvProduct);
        pbProduct = findViewById(R.id.pbProduct);

        productPresenter = new ProductPresenter(this,
                getIntent().getStringExtra("objectId"));
        isFromOrders(getIntent().getBooleanExtra("fromOrders", false));
        productPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        productPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        isActive = false;
        if (productPresenter != null) {
            productPresenter.onStop();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    public void onProductLoaded(ProductModel productModel) {
        Picasso.get()
                .load("https://commerce-7c5d.restdb.io/media/" + productModel.getImage())
                .fit()
                .centerInside()
                .into(ivProductImage);
        tvPrdTitle.setText(productModel.getTitle());
        tvPrdPrice.setText(getString(R.string.product_price, productModel.getPrice()));
        tvPrdDescription.setText(productModel.getDescription());
        Animations.hideElementFade(pbProduct, 400);
        Animations.showElementFade(nsvProduct, 500);
    }

    @Override
    public void onErrorMessage(String error) {
        Snackbar.make(v, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (llProductBuy == v) {
            OrderDialogFragment dialogFragment = OrderDialogFragment
                    .newInstance(productPresenter.getObjectId());
            dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
        }
    }


    public void onFinishDialog(String msg) {
        onErrorMessage(msg);
    }

    private void isFromOrders(boolean flag) {
        if (flag)
            llProductBuy.setVisibility(View.GONE);
    }
}
