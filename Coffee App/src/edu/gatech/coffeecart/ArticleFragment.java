/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.gatech.coffeecart;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.gatech.coffeecart.model.cart.CoffeeCartManager;
import edu.gatech.coffeecart.model.cart.PurchaseItem;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


/** 
 * The ArticleFragment class allows items to be added to activites. 
 * 
 */
//TODO Rename class to ReporListFragment
public class ArticleFragment extends ListFragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    List<Map<String, String>> purchaseList = new ArrayList<Map<String,String>>();
	SimpleAdapter simpleAdap;


	/**
	 * Run when the view is created.
	 * 
	 * @return The current view of the activity.
	 * 
	 * @param inflater The current LayoutInflater used in the activity.
	 * @param container The activities' ViewGroup container.
	 * 
	 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        return inflater.inflate(R.layout.article_view, container, false);
    }

    /**
     * Takes care of the essential actions when started
     * 
     */
    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {

            updateArticleView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {

            updateArticleView(mCurrentPosition);
        }
    }

    /**
     * Updates the article view
     * 
     * @param position the position selected by the user.
     */
    
    public void updateArticleView(int position) {  	
    	mCurrentPosition = position;
    	switch(mCurrentPosition){
    	case 0:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, CoffeeCartManager.getCoffeeCart().getDailyReportOfPurchasedItems()));
    		break;
    	case 1:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, CoffeeCartManager.getCoffeeCart().getDailyReportOfPreOrderedItems()));
    		break;
    	case 2:
    		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, CoffeeCartManager.getCoffeeCart().getDailyReportOfPreOrderedItems()));
    		break;
    	default:
    		break;
    		
    	}
    }

    /**
     * Keeps track of the Save Instance state
     * 
     * @param outState the state to be resumed from.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}