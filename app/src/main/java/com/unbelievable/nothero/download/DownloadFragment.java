package com.unbelievable.nothero.download;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unbelievable.library.android.base.adapter.BaseFragment;
import com.unbelievable.library.android.download.DownloadManagerUtils;
import com.unbelievable.library.android.utils.ToastUtils;
import com.unbelievable.nothero.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DownloadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DownloadFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.btn_download)
    Button btnDownload;
    @BindView(R.id.btn_remove)
    Button btnRemove;
    @BindView(R.id.btn_query)
    Button btnQuery;
    Unbinder unbinder;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Handler handler;
    private String url = "http://www.jhlhlj.com/test.apk";
    private long downloadId;
    private OnFragmentInteractionListener mListener;

    public DownloadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DownloadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DownloadFragment newInstance(String param1, String param2) {
        DownloadFragment fragment = new DownloadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void init(){
        handler = new Handler();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_download;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_download, R.id.btn_remove, R.id.btn_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                ToastUtils.toastL(getContext(),"start download");
                download();
                break;
            case R.id.btn_remove:
                break;
            case R.id.btn_query:
                break;
        }
    }

    private void download(){
        downloadId = DownloadManagerUtils.request(getContext(), url, "nothero", "test.apk");
        //三秒定时刷新一次
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        Runnable command = new Runnable() {

            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateView();
                    }
                });
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(command, 0, 1000, TimeUnit.MILLISECONDS);
    }

    private void updateView(){
        int[] bytesAndStatus = DownloadManagerUtils.getBytesAndStatus(DownloadFragment.this.getContext(),downloadId);
        int currentSize = bytesAndStatus[0];//当前大小
        int totalSize = bytesAndStatus[1];//总大小
        int status = bytesAndStatus[2];//下载状态
        tvStatus.setText("downloaded："+currentSize+"\ntotal:"+totalSize+"\nstatus"+status);
    }
}
