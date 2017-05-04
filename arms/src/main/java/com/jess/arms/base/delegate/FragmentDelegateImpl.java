package com.jess.arms.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.jess.arms.base.App;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jess on 29/04/2017 16:12
 * Contact with jess.yan.effort@gmail.com
 */

public class FragmentDelegateImpl implements FragmentDelegate {
    private android.support.v4.app.FragmentManager mFragmentManager;
    private android.support.v4.app.Fragment mFragment;
    private IFragment iFragment;
    private Unbinder mUnbinder;


    public FragmentDelegateImpl(android.support.v4.app.FragmentManager fragmentManager, android.support.v4.app.Fragment fragment) {
        this.mFragmentManager = fragmentManager;
        this.mFragment = fragment;
        this.iFragment = (IFragment) fragment;
    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onCreateView(View view, Bundle savedInstanceState) {
        //绑定到butterknife
        if (view != null)
            mUnbinder = ButterKnife.bind(mFragment, view);
    }

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        if (iFragment.useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(mFragment);//注册到事件主线
        iFragment.setupFragmentComponent(((App) mFragment.getActivity().getApplication()).getAppComponent());
        iFragment.initData();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null)
            try {
                mUnbinder.unbind();
            } catch (IllegalStateException e) {
                //fix Bindings already cleared
                e.printStackTrace();
            }
    }

    @Override
    public void onDestroy() {
        if (iFragment.useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(mFragment);//注册到事件主线
        this.mUnbinder = null;
    }

    @Override
    public void onDetach() {

    }

    /**
     * Created by xiaobailong24 on 2017/5/3 16:44
     * fix java.io.NotSerializableException
     */
    public static final Parcelable.Creator<FragmentDelegateImpl> CREATOR
            = new Parcelable.Creator<FragmentDelegateImpl>() {

        @Override
        public FragmentDelegateImpl createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public FragmentDelegateImpl[] newArray(int size) {
            return new FragmentDelegateImpl[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
