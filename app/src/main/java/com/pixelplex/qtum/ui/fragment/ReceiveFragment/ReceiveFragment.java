package com.pixelplex.qtum.ui.fragment.ReceiveFragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pixelplex.qtum.R;
import com.pixelplex.qtum.ui.FragmentFactory.Factory;
import com.pixelplex.qtum.ui.fragment.BaseFragment.BaseFragment;
import com.pixelplex.qtum.utils.FontManager;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class ReceiveFragment extends BaseFragment implements ReceiveFragmentView {

    private ReceiveFragmentPresenterImpl mReceiveFragmentPresenter;

    @BindView(R.id.iv_qr_code)
    ImageView mImageViewQrCode;
    @BindView(R.id.et_amount)
    TextInputEditText mTextInputEditTextAmount;
    @BindView(R.id.til_amount)
    TextInputLayout mTextInputLayoutAmount;
    @BindView(R.id.tv_single_string)
    TextView mTextViewAddress;
    @BindView(R.id.bt_copy_wallet_address)
    Button mButtonCopyWalletAddress;
    @BindView(R.id.bt_choose_another_address)
    Button mButtonChooseAnotherAddress;
    @BindView(R.id.ibt_back)
    ImageButton mImageButtonBack;
    @BindView(R.id.rl_receive)
    RelativeLayout mRelativeLayoutBase;
    @BindView(R.id.cl_receive)
    protected
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.qr_progress_bar)
    ProgressBar qrProgressBar;

    @OnClick({R.id.bt_copy_wallet_address,R.id.bt_choose_another_address,R.id.ibt_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_copy_wallet_address:
                getPresenter().onCopyWalletAddressClick();
                break;
            case R.id.bt_choose_another_address:
                getPresenter().onChooseAnotherAddressClick();
                break;
            case R.id.ibt_back:
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                getActivity().onBackPressed();
                break;
        }
    }

    public static BaseFragment newInstance(Context context) {
        Bundle args = new Bundle();
        BaseFragment fragment = Factory.instantiateFragment(context, ReceiveFragment.class);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void createPresenter() {
        mReceiveFragmentPresenter = new ReceiveFragmentPresenterImpl(this);
    }

    @Override
    protected ReceiveFragmentPresenterImpl getPresenter() {
        return mReceiveFragmentPresenter;
    }

    @Override
    public void initializeViews() {
        mTextInputEditTextAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE) {
                    getPresenter().changeAmount(mTextInputEditTextAmount.getText().toString());
                    mRelativeLayoutBase.requestFocus();
                    return false;
                }
                return false;
            }
        });

        mRelativeLayoutBase.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    getPresenter().changeAmount(mTextInputEditTextAmount.getText().toString());
                    hideKeyBoard();
                }
            }
        });

        mTextInputEditTextAmount.setTypeface(FontManager.getInstance().getFont(getString(R.string.simplonMonoRegular)));
        mTextInputLayoutAmount.setTypeface(FontManager.getInstance().getFont(getString(R.string.simplonMonoRegular)));
    }

    @Override
    public void openFragmentForResult(Fragment fragment) {
        int code_response = 201;
        fragment.setTargetFragment(this, code_response);
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.fragment_container, fragment, fragment.getClass().getCanonicalName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setSoftMode() {
        super.setSoftMode();
        getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public void setQrCode(Bitmap bitmap) {
        if(bitmap != null && mImageViewQrCode != null) {
            hideSpinner();
            mImageViewQrCode.setImageBitmap(bitmap);
        }
    }

    @Override
    public void showSpinner() {
        qrProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSpinner() {
        qrProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUpAddress(String s) {
        mTextViewAddress.setText(s);
    }

    @Override
    public void showToast() {
        Toast.makeText(getContext(),R.string.coped,Toast.LENGTH_SHORT).show();
    }

    public void onChangeAddress(){
        getPresenter().changeAddress();
    }

}