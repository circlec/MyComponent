package zc.commonlib.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import zc.commonlib.R;
import zc.commonlib.databinding.CommonlibFragmentBottomDialogBinding;

public class BottomDialogFragment extends DialogFragment {

    public static final String TITLE = "title";
    public static final String CONFIRM_STR = "confirmStr";
    public static final String REFUSE_STR = "refuseStr";

    private OnFragmentInteractionListener mListener;
    private String title;
    private String confirmStr = "确定";
    private String refuseStr = "取消";

    private CommonlibFragmentBottomDialogBinding binding;

    public BottomDialogFragment() {
    }

    public static BottomDialogFragment newInstance(String title) {
        BottomDialogFragment fragment = new BottomDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static BottomDialogFragment newInstance(String title, String confirmStr, String cancelStr) {
        BottomDialogFragment fragment = new BottomDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(CONFIRM_STR, confirmStr);
        bundle.putString(REFUSE_STR, cancelStr);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            if (!TextUtils.isEmpty(getArguments().getString(CONFIRM_STR)))
                confirmStr = getArguments().getString(CONFIRM_STR);
            if (!TextUtils.isEmpty(getArguments().getString(REFUSE_STR)))
                refuseStr = getArguments().getString(REFUSE_STR);
        }
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.commonlib_not_fullscreen_dialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = CommonlibFragmentBottomDialogBinding.inflate(inflater, container, false);
        binding.tvTitle.setText(title);
        binding.tvConfirm.setText(confirmStr);
        binding.tvRefuse.setText(refuseStr);
        binding.tvConfirm.setOnClickListener(view -> comfirm());
        binding.tvRefuse.setOnClickListener(view -> refuse());
//        binding.clEmpty.setOnClickListener(view -> dismiss());
        return binding.getRoot();
    }

    private void comfirm() {
        if (mListener != null)
            mListener.onBottomDialogConfirm();
        dismiss();
    }

    private void refuse() {
        if (mListener != null)
            mListener.onBottomDialogRefuse();
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        assert window != null;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public interface OnFragmentInteractionListener {
        void onBottomDialogConfirm();

        void onBottomDialogRefuse();
    }
}
