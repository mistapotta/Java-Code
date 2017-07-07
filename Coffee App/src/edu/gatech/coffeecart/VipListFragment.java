package edu.gatech.coffeecart;

import edu.gatech.coffeecart.model.cart.CoffeeCartManager;
import android.app.Activity;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VipListFragment extends ListFragment {
	int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
            android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
    OnHeadlineSelectedListener mCallback;
    //TODO delete
    static String[] Reports = {
        "VIP 1",
        "VIP 2"
    };

    public interface OnHeadlineSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onArticleSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
        //        android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
      //TODO DB Implement
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, CoffeeCartManager.getCoffeeCart().getVips()));
      //end TODO
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getFragmentManager().findFragmentById(R.id.article_fragment2) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, CoffeeCartManager.getCoffeeCart().getVips()));
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        mCallback.onArticleSelected(position);

        getListView().setItemChecked(position, true);
    }
}