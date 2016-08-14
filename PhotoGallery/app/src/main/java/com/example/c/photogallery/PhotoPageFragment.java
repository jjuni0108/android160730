package com.example.c.photogallery;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by c on 2016-08-14.
 */
public class PhotoPageFragment extends Fragment {

    private static final String ARG_URI ="photo_page_uri";
    private Uri mUri;
    private WebView mWebView;
    private ProgressBar mProgressBar;

    public static PhotoPageFragment newInstance(Uri uri) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_URI,uri);
        PhotoPageFragment fragment = new PhotoPageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUri =getArguments().getParcelable(ARG_URI);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_page,container,false);
        mWebView = (WebView)v.findViewById(R.id.fragment_photo_page_web_view);
        mProgressBar =(ProgressBar)v.findViewById(R.id.fragment_progressbar);

//        mWebView.setWebViewClient(new WebViewClient(){
//            ProgressDialog dlg= new ProgressDialog(getActivity());
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                dlg.show();
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                dlg.dismiss();
//            }
//        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                 if (newProgress==100){
                    mProgressBar.setVisibility(View.GONE);
                }else{
                     mProgressBar.setVisibility(View.VISIBLE);
                     mProgressBar.setProgress(newProgress);
                 }
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mUri.toString());
//        mWebView.canGoBack();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keycode, KeyEvent keyEvent) {
                if(keyEvent.getAction()== keyEvent.ACTION_DOWN){
                    if(keycode==keyEvent.KEYCODE_BACK){
                        if(mWebView.canGoBack()){
                            mWebView.goBack();
                        }else{
                            getActivity().onBackPressed();
                        }
                    }
                }
                return false;
            }
        });
    }
}
