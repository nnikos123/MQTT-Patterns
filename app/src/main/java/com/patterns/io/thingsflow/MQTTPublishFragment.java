package com.patterns.io.thingsflow;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class MQTTPublishFragment extends Fragment {

    public String                       topicString;
    public String                       textToPublishString;

    public EditText                     textPublish;
    public EditText                     textPublishTopic;

    public FloatingActionButton         fabPublishTotopics;
    public FloatingActionButton         fabConnect;
    public FloatingActionButton         fabSubscribe;

    public PublishDataPassListener      mCallback;

    public MQTTPublishFragment() {
    }

    public interface PublishDataPassListener{
        void launchSubscribeFragment(String data);
        void launchConnectFragment(String data);
        void publishMQTTmessage(String messageParams[]);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity;

        if (context instanceof Activity){
            activity=(Activity) context;

            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (PublishDataPassListener) activity;

            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement PublishDataPassListener");
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.devicefragment, menu);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_publish, container, false);

        ///////////////  ALL THIS IS   I/O    MQTT STUFF //////////////////////////////////////////
        // Find the listView by its ID
        fabPublishTotopics  = (FloatingActionButton) rootView.findViewById(R.id.fabPublishToTopic);
        fabConnect          = (FloatingActionButton) rootView.findViewById(R.id.fabConnect);
        fabSubscribe        = (FloatingActionButton) rootView.findViewById(R.id.fabSubscribe);

        textPublishTopic    = (EditText)             rootView.findViewById(R.id.editTextPublishTopic);
        textPublish         = (EditText)             rootView.findViewById(R.id.editTextPublish);

        fabPublishTotopics  .setOnClickListener(onClickListenerMQTT);
        fabConnect          .setOnClickListener(onClickListenerMQTT);
        fabSubscribe        .setOnClickListener(onClickListenerMQTT);

        return rootView;
    }

    @Override
    public void onStart(){

        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            //TODO: add the data to be passed to this fragment
            textPublishTopic.setText(args.getString("topic"));
            textPublish     .setText(args.getString("text"));
        }
    }



    private OnClickListener onClickListenerMQTT = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch(v.getId()){
                case R.id.fabPublishToTopic:
                    //Handle Button click
                    String textToPublish= textPublish       .getText().toString();
                    String publishTopic = textPublishTopic  .getText().toString();

                    String connectParams[] = {"publish", textToPublish,publishTopic};
                    mCallback.publishMQTTmessage(connectParams);

                    break;

                case R.id.fabConnect:
                    //Handle Button click
                    mCallback.launchConnectFragment("");
                    break;

                case R.id.fabSubscribe:
                    //Handle Button click
                    mCallback.launchSubscribeFragment("");
                    break;
            }
        }
    };
}