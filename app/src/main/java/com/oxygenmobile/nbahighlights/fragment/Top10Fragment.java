package com.oxygenmobile.nbahighlights.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.oxygenmobile.nbahighlights.R;
import com.oxygenmobile.nbahighlights.adapters.MyRecyclerViewAdapterPlayList;
import com.oxygenmobile.nbahighlights.adapters.MyRecyclerViewAdapterTopPlayList;
import com.oxygenmobile.nbahighlights.configuration.Config;
import com.oxygenmobile.nbahighlights.model.Game;
import com.oxygenmobile.nbahighlights.model.PlayListItem;
import com.oxygenmobile.nbahighlights.utils.GlobalVariables;
import com.oxygenmobile.nbahighlights.utils.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Top10Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Top10Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Top10Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String baseUrl = Config.TOP_PLAYS_API;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Game> gameList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public Top10Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Top10Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Top10Fragment newInstance(String param1, String param2) {
        Top10Fragment fragment = new Top10Fragment();
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
        View rootView = inflater.inflate(R.layout.fragment_top_10, container, false);


        new RetrieveTopPlayListAsyncTask(rootView).execute();
        
       /* RetrieveTopPlayListAsyncTask retrieveTopPlayListAsyncTask = new RetrieveTopPlayListAsyncTask();
        retrieveTopPlayListAsyncTask.execute();*/


        return rootView;

    }

    public class RetrieveTopPlayListAsyncTask extends AsyncTask<Void, Void, List<Game>> {

        private View rootView;
        private ProgressDialog progressDialog = new ProgressDialog(getActivity());


        public RetrieveTopPlayListAsyncTask(View rootView) {
            this.rootView = rootView;

        }

        @Override
        protected void onPreExecute() {

            progressDialog.setCancelable(false);
            progressDialog.setMessage("Lütfen Bekleyiniz.....");
            progressDialog.show();
        }

        @Override
        protected List<Game> doInBackground(Void... voids) {
            if (((GlobalVariables) getActivity().getApplicationContext()).getTopPlayListItem() != null) {
                return ((GlobalVariables) getActivity().getApplicationContext()).getTopPlayListItem();
            } else {

                String nextPageToken = null;

                try {
                    URL url = new URL(baseUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();

                    JSONObject object = (JSONObject) new JSONTokener(stringBuilder.toString()).nextValue();
                    JSONObject subObject = object.getJSONObject("pageInfo");
                    double totalResults = subObject.getDouble("totalResults");
                    double roundOfTotalResults = Math.ceil(totalResults / Config.RESULTS_PER_PAGE);
                    urlConnection.disconnect();
                    Log.e("Yuvarlanmis sonuc", String.valueOf((int) roundOfTotalResults));
                    int silinecekSayac = 0;
                    for (int j = 0; j < roundOfTotalResults; j++) {
                        URL url1 = new URL(baseUrl);
                        urlConnection = (HttpURLConnection) url1.openConnection();
                        BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder1 = new StringBuilder();
                        String line1;
                        while ((line1 = bufferedReader1.readLine()) != null) {
                            stringBuilder1.append(line1).append("\n");
                        }
                        bufferedReader.close();

                        JSONObject object1 = (JSONObject) new JSONTokener(stringBuilder1.toString()).nextValue();
                        if (object1.has("nextPageToken")) {
                            nextPageToken = object1.getString("nextPageToken");
                        }
                        JSONArray itemsArray = object1.getJSONArray("items");

                        for (int i = 0; i < itemsArray.length(); i++) {
                            JSONObject eachGame = itemsArray.getJSONObject(i);
                            JSONObject snippet = eachGame.getJSONObject("snippet");
                            String title = snippet.getString("title");
                            String description = snippet.getString("description");
                            String thumbnails = snippet.getJSONObject("thumbnails").getJSONObject("high").getString("url");
                            JSONObject resourceId = snippet.getJSONObject("resourceId");
                            String videoId = resourceId.getString("videoId");

                            gameList.add(new Game(title, description, thumbnails, videoId));
                        }

                        baseUrl = Config.TOP_PLAYS_API + "&pageToken=" + nextPageToken;
                    }
                    Log.e("Silinecek Sayac", String.valueOf(silinecekSayac));
                    urlConnection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return gameList;


                // JsonParser jsonParser = new JsonParser(baseUrl);
              //  return jsonParser.getJsonData(Config.TOP_PLAYS_API);
            }


        }

        @Override
        protected void onPostExecute(List<Game> playListItems) {
            ((GlobalVariables) getActivity().getApplicationContext()).setTopPlayListItem(playListItems);
            mRecyclerView = rootView.findViewById(R.id.my_recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new StaggeredGridLayoutManager(2,1);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyRecyclerViewAdapterTopPlayList(playListItems, getContext());
            mRecyclerView.setAdapter(mAdapter);
            progressDialog.dismiss();

        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
